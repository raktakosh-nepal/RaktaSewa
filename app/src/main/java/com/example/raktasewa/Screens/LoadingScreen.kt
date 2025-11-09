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
import androidx.compose.ui.draw.scale
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
                    colors = listOf(RedBackground, Color.White)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Animated blood drops
            BloodDropsAnimation()

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Fonts.ManropeFamily,
                color = DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Progress indicator
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(6.dp),
                color = BloodRed,
                trackColor = CardRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (language == "Nep")
                    "कृपया प्रतीक्षा गर्नुहोस्..."
                else
                    "Please wait...",
                fontSize = 14.sp,
                fontFamily = Fonts.ManropeFamily,
                color = DarkRed,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun BloodDropsAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Three blood drops with different animations
    val drop1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val drop2Y by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val drop3Y by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val centerX = size.width / 2
            val radius = 60f

            // Draw three rotating circles with blood drops
            for (i in 0..2) {
                val angle = (rotation + i * 120) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * radius
                val y = centerX + sin(angle).toFloat() * radius

                drawCircle(
                    color = when(i) {
                        0 -> BloodRed
                        1 -> DarkRed
                        else -> SoftRed
                    },
                    radius = 15f,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        // Center blood drop with pulse animation
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )

        Text(
            text = "🩸",
            fontSize = 60.sp,
            modifier = Modifier.scale(scale)
        )
    }
}