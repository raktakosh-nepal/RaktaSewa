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

// Particle data class for the sci-fi particle system
data class Particle(
    var x: Float,
    var y: Float,
    var velocityX: Float,
    var velocityY: Float,
    var alpha: Float,
    var size: Float,
    var life: Float,
    var maxLife: Float,
    var color: Color,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f
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

    // Particle system state
    val particles = remember { mutableStateListOf<Particle>() }
    var frameCount by remember { mutableIntStateOf(0) }

    // Main rotation for orbiting circles
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Center badge pulsing
    val badgeScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Circle opacity pulsing
    val circleOpacity by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Inner ring rotation (opposite direction)
    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Circle scale pulsing
    val circleScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Outer ring rotation
    val outerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Energy wave expansion
    val waveRadius by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseOut),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Secondary energy wave (offset)
    val waveRadius2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseOut),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(1000)
        ),
        label = ""
    )

    // Cosmic glow pulse
    val glowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Update particles and spawn new ones
    LaunchedEffect(rotation, innerRotation, outerRotation) {
        frameCount++

        // Spawn particles from orbiting circles
        if (frameCount % 2 == 0) { // Spawn every 2 frames
            val centerX = 140f
            val centerY = 140f

            // Spawn from outer ring
            for (i in 0..5) {
                if (Random.nextFloat() < 0.4f) {
                    val angle = (outerRotation + i * 60) * Math.PI / 180
                    val x = centerX + cos(angle).toFloat() * 120f
                    val y = centerY + sin(angle).toFloat() * 120f

                    val particleAngle = angle + Random.nextFloat() * 0.5 - 0.25
                    val speed = Random.nextFloat() * 2f + 1.5f

                    particles.add(
                        Particle(
                            x = x,
                            y = y,
                            velocityX = cos(particleAngle).toFloat() * speed,
                            velocityY = sin(particleAngle).toFloat() * speed,
                            alpha = 1f,
                            size = Random.nextFloat() * 4f + 3f,
                            life = 1f,
                            maxLife = Random.nextFloat() * 0.5f + 0.8f,
                            color = when (Random.nextInt(4)) {
                                0 -> Color(0xFFFF6B6B)
                                1 -> Color(0xFFFF8787)
                                2 -> Color(0xFFFFB3B3)
                                else -> Color(0xFFFFD4D4)
                            },
                            rotation = Random.nextFloat() * 360f,
                            rotationSpeed = Random.nextFloat() * 4f - 2f
                        )
                    )
                }
            }

            // Spawn from middle ring
            for (i in 0..3) {
                if (Random.nextFloat() < 0.5f) {
                    val angle = (rotation + i * 90) * Math.PI / 180
                    val x = centerX + cos(angle).toFloat() * 100f
                    val y = centerY + sin(angle).toFloat() * 100f

                    val particleAngle = angle + Random.nextFloat() * 0.6 - 0.3
                    val speed = Random.nextFloat() * 2.5f + 2f

                    particles.add(
                        Particle(
                            x = x,
                            y = y,
                            velocityX = cos(particleAngle).toFloat() * speed,
                            velocityY = sin(particleAngle).toFloat() * speed,
                            alpha = 1f,
                            size = Random.nextFloat() * 5f + 4f,
                            life = 1f,
                            maxLife = Random.nextFloat() * 0.6f + 1f,
                            color = when (Random.nextInt(3)) {
                                0 -> Color(0xFFFF4757)
                                1 -> Color(0xFFFF6B81)
                                else -> Color(0xFFFF9AA2)
                            },
                            rotation = Random.nextFloat() * 360f,
                            rotationSpeed = Random.nextFloat() * 5f - 2.5f
                        )
                    )
                }
            }

            // Spawn from inner ring
            for (i in 0..2) {
                if (Random.nextFloat() < 0.6f) {
                    val angle = (innerRotation + i * 120) * Math.PI / 180
                    val x = centerX + cos(angle).toFloat() * 70f
                    val y = centerY + sin(angle).toFloat() * 70f

                    val particleAngle = angle + Random.nextFloat() * 0.8 - 0.4
                    val speed = Random.nextFloat() * 3f + 2.5f

                    particles.add(
                        Particle(
                            x = x,
                            y = y,
                            velocityX = cos(particleAngle).toFloat() * speed,
                            velocityY = sin(particleAngle).toFloat() * speed,
                            alpha = 1f,
                            size = Random.nextFloat() * 6f + 3f,
                            life = 1f,
                            maxLife = Random.nextFloat() * 0.7f + 1.2f,
                            color = when (Random.nextInt(3)) {
                                0 -> Color(0xFFDC3545)
                                1 -> Color(0xFFFF5370)
                                else -> Color(0xFFFFB8C5)
                            },
                            rotation = Random.nextFloat() * 360f,
                            rotationSpeed = Random.nextFloat() * 6f - 3f
                        )
                    )
                }
            }
        }

        // Update existing particles
        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            val particle = iterator.next()
            particle.x += particle.velocityX
            particle.y += particle.velocityY
            particle.life -= 0.02f
            particle.alpha = (particle.life / particle.maxLife).coerceIn(0f, 1f)
            particle.rotation += particle.rotationSpeed

            // Apply slight gravity/curl effect
            particle.velocityY += 0.05f

            // Remove dead particles
            if (particle.life <= 0 || particle.alpha <= 0) {
                iterator.remove()
            }
        }

        // Limit particle count for performance
        while (particles.size > 200) {
            particles.removeAt(0)
        }
    }

    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // Main canvas with all effects
        Canvas(modifier = Modifier.size(300.dp)) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Cosmic background nebula effect
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFE5E8).copy(alpha = glowIntensity * 0.3f),
                        Color(0xFFFFD4D8).copy(alpha = glowIntensity * 0.2f),
                        Color.Transparent
                    ),
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    radius = 200f
                ),
                center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                radius = 200f
            )

            // Energy waves expanding from center
            val waveAlpha1 = (1f - waveRadius / 150f) * 0.6f
            if (waveAlpha1 > 0) {
                drawCircle(
                    color = Color(0xFFFF6B6B).copy(alpha = waveAlpha1),
                    radius = waveRadius,
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    style = Stroke(width = 3f)
                )

                // Inner glow of energy wave
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B).copy(alpha = waveAlpha1 * 0.3f),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                        radius = waveRadius
                    ),
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    radius = waveRadius
                )
            }

            // Secondary energy wave
            val waveAlpha2 = (1f - waveRadius2 / 150f) * 0.5f
            if (waveAlpha2 > 0) {
                drawCircle(
                    color = Color(0xFFFF8787).copy(alpha = waveAlpha2),
                    radius = waveRadius2,
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    style = Stroke(width = 2f)
                )
            }

            // Draw particles with glow effects
            particles.forEach { particle ->
                // Ensure particle size is always valid (> 0)
                val safeSize = particle.size.coerceAtLeast(0.1f)

                // Outer glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            particle.color.copy(alpha = particle.alpha * 0.4f),
                            Color.Transparent
                        ),
                        radius = safeSize * 3f
                    ),
                    center = androidx.compose.ui.geometry.Offset(particle.x, particle.y),
                    radius = safeSize * 3f
                )

                // Main particle
                drawCircle(
                    color = particle.color.copy(alpha = particle.alpha),
                    center = androidx.compose.ui.geometry.Offset(particle.x, particle.y),
                    radius = safeSize
                )

                // Inner bright core
                drawCircle(
                    color = Color.White.copy(alpha = particle.alpha * 0.7f),
                    center = androidx.compose.ui.geometry.Offset(particle.x, particle.y),
                    radius = safeSize * 0.4f
                )
            }

            // Outermost orbiting circles with enhanced glow
            val outerRadius = 120f
            for (i in 0..5) {
                val angle = (outerRotation + i * 60) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * outerRadius
                val y = centerY + sin(angle).toFloat() * outerRadius

                // Ensure safe radius values
                val safeCircleScale = circleScale.coerceAtLeast(0.1f)

                // Glow halo
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFA0A0).copy(alpha = circleOpacity * 0.6f),
                            Color(0xFFFFA0A0).copy(alpha = circleOpacity * 0.3f),
                            Color.Transparent
                        ),
                        radius = 25f * safeCircleScale
                    ),
                    center = androidx.compose.ui.geometry.Offset(x, y),
                    radius = 25f * safeCircleScale
                )

                // Main circle
                drawCircle(
                    color = Color(0xFFFFA0A0).copy(alpha = circleOpacity * 0.8f),
                    radius = 12f * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )

                // Inner bright core
                drawCircle(
                    color = Color.White.copy(alpha = circleOpacity * 0.6f),
                    radius = 6f * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }

            // Middle orbiting circles with glow
            val middleRadius = 100f
            for (i in 0..3) {
                val angle = (rotation + i * 90) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * middleRadius
                val y = centerY + sin(angle).toFloat() * middleRadius
                val circleSize = if (i % 2 == 0) 18f else 16f

                // Ensure safe radius values
                val safeCircleScale = circleScale.coerceAtLeast(0.1f)

                // Chromatic glow (multi-color halo)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B).copy(alpha = circleOpacity * 0.7f),
                            Color(0xFFFFB8B8).copy(alpha = circleOpacity * 0.4f),
                            Color.Transparent
                        ),
                        radius = 30f * safeCircleScale
                    ),
                    center = androidx.compose.ui.geometry.Offset(x, y),
                    radius = 30f * safeCircleScale
                )

                // Main circle
                drawCircle(
                    color = Color(0xFFFFB8B8).copy(alpha = circleOpacity),
                    radius = circleSize * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )

                // Inner core
                drawCircle(
                    color = Color.White.copy(alpha = circleOpacity * 0.8f),
                    radius = (circleSize * 0.5f) * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }

            // Inner orbiting circles with intense glow
            val innerRadius = 70f
            for (i in 0..2) {
                val angle = (innerRotation + i * 120) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * innerRadius
                val y = centerY + sin(angle).toFloat() * innerRadius

                // Ensure safe radius values
                val safeCircleScale = circleScale.coerceAtLeast(0.1f)

                // Intense energy glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDC3545).copy(alpha = circleOpacity * 0.8f),
                            Color(0xFFFFD0D0).copy(alpha = circleOpacity * 0.5f),
                            Color.Transparent
                        ),
                        radius = 35f * safeCircleScale
                    ),
                    center = androidx.compose.ui.geometry.Offset(x, y),
                    radius = 35f * safeCircleScale
                )

                // Main circle
                drawCircle(
                    color = Color(0xFFFFD0D0).copy(alpha = circleOpacity * 0.9f),
                    radius = 12f * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )

                // Bright white core
                drawCircle(
                    color = Color.White.copy(alpha = circleOpacity),
                    radius = 7f * safeCircleScale,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        // Center blood group badge with enhanced effects
        Box(
            modifier = Modifier
                .size(140.dp)
                .scale(badgeScale),
            contentAlignment = Alignment.Center
        ) {
            // Outer glow layer
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFF6B6B).copy(alpha = glowIntensity * 0.4f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            // Main badge
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFF4757),
                                Color(0xFFDC3545),
                                Color(0xFFC82333)
                            )
                        )
                    )
                    .shadow(
                        elevation = 20.dp,
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
}