import android.Manifest
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Path
import kotlin.random.Random
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.raktasewa.API.RetrofitInstance
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.ui.theme.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.cos
import kotlin.math.sin

// Blood drop data class for the loading animation
data class BloodDrop(
    var x: Float,
    var y: Float,
    var velocityY: Float,
    var alpha: Float,
    var size: Float,
    var phase: Float = 0f
)

@Composable
fun LoadingScreen(backStack: SnapshotStateList<AllScreens>, text: String, bloodType: String, language: String) {
    val context = LocalContext.current
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.lastLocation?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude
                    fusedLocationClient.removeLocationUpdates(this)

                    // Make API call to fetch blood banks
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = RetrofitInstance.api.getBloodBanks(
                                latitude = latitude,
                                longitude = longitude,
                                type = bloodType
                            )

                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful && response.body() != null) {
                                    val bloodBanks = response.body()!!
                                    // Navigate to results screen
                                    backStack.add(AllScreens.BloodBanksResultScreen(bloodBanks, latitude, longitude, language))
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Failed to fetch blood banks: ${response.code()}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Error: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val locationRequest = LocationRequest.Builder(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            val locationRequest = LocationRequest.Builder(1000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    // Custom loading animations
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF5F5),
                        Color(0xFFFFFFFF),
                        Color(0xFFFFF5F5)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Animated blood drop with orbiting circles
            BloodDropWithOrbits(bloodType = bloodType)

            Spacer(modifier = Modifier.weight(1f))

            // Bottom card with progress
            androidx.compose.material3.Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 80.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress dots indicator
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xFFDC3545))
                        )
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xFFE0E0E0))
                        )
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xFFE0E0E0))
                        )
                    }

                    Text(
                        text = if (language == "Nep")
                            "तपाईंको स्थान प्राप्त गर्दै..."
                        else
                            "Getting your location...",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF2C3E50),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (language == "Nep")
                            "रक्त प्रकार: $bloodType"
                        else
                            "Blood Type: $bloodType",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFFDC3545),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Progress bar
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(2.dp)),
                        color = Color(0xFFDC3545),
                        trackColor = Color(0xFFE0E0E0)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (language == "Nep")
                                "चरण १ को ३"
                            else
                                "Step 1 of 3",
                            fontSize = 13.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF888888),
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "0%",
                            fontSize = 13.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFFDC3545),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BloodDropWithOrbits(bloodType: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Blood drops state
    val bloodDrops = remember { mutableStateListOf<BloodDrop>() }
    var frameCount by remember { mutableIntStateOf(0) }

    // Heartbeat pulse animation (like an ECG)
    val heartbeatScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                1.0f at 0
                1.15f at 100 using EaseInOut
                1.0f at 200
                1.08f at 300 using EaseInOut
                1.0f at 400
            },
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Pulse wave that expands outward (like heartbeat ripple)
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 70f,
        targetValue = 140f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseOut),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Rotation for flowing effect
    val flowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Update blood drops - spawn and animate
    LaunchedEffect(Unit) {
        while (true) {
            frameCount++

            // Spawn new blood drops periodically
            if (frameCount % 15 == 0) {
                bloodDrops.add(
                    BloodDrop(
                        x = Random.nextFloat() * 280f + 10f,
                        y = -20f,
                        velocityY = Random.nextFloat() * 2f + 2f,
                        alpha = Random.nextFloat() * 0.3f + 0.6f,
                        size = Random.nextFloat() * 6f + 4f,
                        phase = Random.nextFloat()
                    )
                )
            }

            // Update existing drops
            val iterator = bloodDrops.iterator()
            while (iterator.hasNext()) {
                val drop = iterator.next()
                drop.y += drop.velocityY
                drop.velocityY += 0.1f // Gravity
                drop.phase += 0.1f

                // Remove drops that fall off screen
                if (drop.y > 320f) {
                    iterator.remove()
                }
            }

            // Limit drop count
            while (bloodDrops.size > 25) {
                bloodDrops.removeAt(0)
            }

            kotlinx.coroutines.delay(16) // ~60 FPS
        }
    }

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // Main canvas
        Canvas(modifier = Modifier.size(300.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Heartbeat pulse rings
            val pulseAlpha = ((140f - pulseRadius) / 70f).coerceIn(0f, 1f)
            if (pulseAlpha > 0.1f) {
                drawCircle(
                    color = Color(0xFFDC3545).copy(alpha = pulseAlpha * 0.3f),
                    radius = pulseRadius,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 2f)
                )
                drawCircle(
                    color = Color(0xFFDC3545).copy(alpha = pulseAlpha * 0.15f),
                    radius = pulseRadius + 10f,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 1f)
                )
            }

            // Flowing blood cells (circular motion)
            for (i in 0..7) {
                val angle = (flowRotation + i * 45) * Math.PI / 180
                val radius = 90f
                val x = centerX + cos(angle).toFloat() * radius
                val y = centerY + sin(angle).toFloat() * radius

                val cellSize = if (i % 2 == 0) 8f else 6f

                // Blood cell with subtle glow
                drawCircle(
                    color = Color(0xFFE85A50).copy(alpha = 0.6f),
                    radius = cellSize * 1.5f,
                    center = Offset(x, y)
                )
                drawCircle(
                    color = Color(0xFFDC3545),
                    radius = cellSize,
                    center = Offset(x, y)
                )
            }

            // Falling blood drops
            bloodDrops.forEach { drop ->
                // Drop shadow/glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDC3545).copy(alpha = drop.alpha * 0.4f),
                            Color.Transparent
                        ),
                        radius = drop.size * 2f
                    ),
                    center = Offset(drop.x, drop.y),
                    radius = drop.size * 2f
                )

                // Main drop
                drawCircle(
                    color = Color(0xFFDC3545).copy(alpha = drop.alpha),
                    radius = drop.size,
                    center = Offset(drop.x, drop.y)
                )

                // Highlight on drop
                drawCircle(
                    color = Color(0xFFFF8A80).copy(alpha = drop.alpha * 0.7f),
                    radius = drop.size * 0.4f,
                    center = Offset(drop.x - drop.size * 0.2f, drop.y - drop.size * 0.2f)
                )
            }

            // Medical cross pattern (subtle)
            val crossAlpha = 0.08f
            val crossSize = 50f
            drawLine(
                color = Color(0xFFDC3545).copy(alpha = crossAlpha),
                start = Offset(centerX - crossSize, centerY),
                end = Offset(centerX + crossSize, centerY),
                strokeWidth = 20f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFFDC3545).copy(alpha = crossAlpha),
                start = Offset(centerX, centerY - crossSize),
                end = Offset(centerX, centerY + crossSize),
                strokeWidth = 20f,
                cap = StrokeCap.Round
            )
        }

        // Center blood type badge with heartbeat
        Box(
            modifier = Modifier
                .size(130.dp)
                .scale(heartbeatScale),
            contentAlignment = Alignment.Center
        ) {
            // Outer subtle glow
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFDC3545).copy(alpha = 0.2f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            // Main badge
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .shadow(16.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFDC3545),
                                Color(0xFFC82333)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = bloodType,
                    fontSize = 46.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White,
                    letterSpacing = (-1).sp
                )
            }
        }
    }
}