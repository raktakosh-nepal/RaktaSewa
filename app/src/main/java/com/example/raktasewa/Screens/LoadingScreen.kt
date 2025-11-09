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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
                                    backStack.add(AllScreens.BloodBanksResultScreen(bloodBanks, language))
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

    // Main rotation for orbiting circles
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Pulsing effect for center badge
    val badgeScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Pulsing opacity for circles
    val circleOpacity by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Inner ring rotation (opposite direction)
    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer orbiting circles (4 circles)
        Canvas(modifier = Modifier.size(260.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = 100f

            for (i in 0..3) {
                val angle = (rotation + i * 90) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * radius
                val y = centerY + sin(angle).toFloat() * radius

                // Varying sizes for more dynamic effect
                val circleSize = if (i % 2 == 0) 16f else 14f

                drawCircle(
                    color = Color(0xFFFFB8B8).copy(alpha = circleOpacity),
                    radius = circleSize,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        // Inner orbiting circles (3 circles, smaller and faster)
        Canvas(modifier = Modifier.size(200.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = 70f

            for (i in 0..2) {
                val angle = (innerRotation + i * 120) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * radius
                val y = centerY + sin(angle).toFloat() * radius

                drawCircle(
                    color = Color(0xFFFFD0D0).copy(alpha = circleOpacity * 0.7f),
                    radius = 10f,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        // Center blood group badge with pulsing animation
        Box(
            modifier = Modifier
                .size(140.dp)
                .scale(badgeScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDC3545),
                            Color(0xFFC82333)
                        )
                    )
                )
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bloodType,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = (-1).sp
            )
        }
    }
}