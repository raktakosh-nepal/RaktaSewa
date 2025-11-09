package com.example.raktasewa.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Models.BloodBank
import com.example.raktasewa.Nav.AllScreens
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodBankDetailScreen(
    backStack: SnapshotStateList<AllScreens>,
    bloodBank: BloodBank,
    userLatitude: Double,
    userLongitude: Double,
    language: String
) {
    val context = LocalContext.current

    // Calculate distance
    val distance = remember {
        calculateDistance(userLatitude, userLongitude, bloodBank.latitude, bloodBank.longitude)
    }

    // Estimate travel time (assuming average speed of 30 km/h in city)
    val estimatedTime = remember { (distance / 30.0 * 60).roundToInt() }

    // Animation states
    var headerVisible by remember { mutableStateOf(false) }
    var mapVisible by remember { mutableStateOf(false) }
    var detailsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(200)
        mapVisible = true
        delay(300)
        detailsVisible = true
    }

    // Map camera position
    val userLocation = LatLng(userLatitude, userLongitude)
    val bloodBankLocation = LatLng(bloodBank.latitude, bloodBank.longitude)

    // Center map between user and blood bank
    val centerLat = (userLatitude + bloodBank.latitude) / 2
    val centerLng = (userLongitude + bloodBank.longitude) / 2
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(centerLat, centerLng), 13f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with back button
            AnimatedVisibility(
                visible = headerVisible,
                enter = fadeIn(tween(400)) + slideInVertically(initialOffsetY = { -it })
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { backStack.removeLast() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF1E293B)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == "Nep") "विवरण" else "Blood Bank Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF1E293B)
                        )
                    }
                }
            }

            // Map Section
            AnimatedVisibility(
                visible = mapVisible,
                enter = fadeIn(tween(500)) + scaleIn(
                    initialScale = 0.95f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(isMyLocationEnabled = false),
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = false,
                            compassEnabled = true
                        )
                    ) {
                        // User marker
                        Marker(
                            state = MarkerState(position = userLocation),
                            title = if (language == "Nep") "तपाईको स्थान" else "Your Location",
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                        )

                        // Blood bank marker
                        Marker(
                            state = MarkerState(position = bloodBankLocation),
                            title = bloodBank.name,
                            snippet = bloodBank.address,
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                        )

                        // Draw polyline (route)
                        Polyline(
                            points = listOf(userLocation, bloodBankLocation),
                            color = Color(0xFFDC3545),
                            width = 10f
                        )
                    }
                }
            }

            // Distance and Time Card
            AnimatedVisibility(
                visible = detailsVisible,
                enter = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { it / 4 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Distance
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = Color(0xFFDC3545),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "%.1f km".format(distance),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = if (language == "Nep") "दूरी" else "Distance",
                                fontSize = 12.sp,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF64748B)
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .height(80.dp)
                                .width(1.dp),
                            color = Color(0xFFE2E8F0)
                        )

                        // Time
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$estimatedTime min",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = if (language == "Nep") "अनुमानित समय" else "Est. Time",
                                fontSize = 12.sp,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            }

            // Blood Bank Details Card
            AnimatedVisibility(
                visible = detailsVisible,
                enter = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        // Blood Bank Image and Name
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Image
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFFFF0F0))
                            ) {
                                if (bloodBank.imageUrl.isNotEmpty() && bloodBank.imageUrl != "null") {
                                    AsyncImage(
                                        model = bloodBank.imageUrl,
                                        contentDescription = bloodBank.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "🏥",
                                            fontSize = 40.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = bloodBank.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Fonts.ManropeFamily,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = bloodBank.address,
                                        fontSize = 13.sp,
                                        fontFamily = Fonts.ManropeFamily,
                                        color = Color(0xFF64748B),
                                        maxLines = 2
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Blood Type and Quantity
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFF5F5)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (language == "Nep") "रक्त प्रकार" else "Blood Type",
                                        fontSize = 12.sp,
                                        fontFamily = Fonts.ManropeFamily,
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFFDC3545)
                                    ) {
                                        Text(
                                            text = bloodBank.type,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = Fonts.ManropeFamily,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = if (language == "Nep") "उपलब्ध" else "Available",
                                        fontSize = 12.sp,
                                        fontFamily = Fonts.ManropeFamily,
                                        color = Color(0xFF64748B),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${bloodBank.quantity.roundToInt()}",
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Fonts.ManropeFamily,
                                        color = Color(0xFFDC3545),
                                        letterSpacing = (-1).sp
                                    )
                                    Text(
                                        text = if (language == "Nep") "एकाइहरू" else "units",
                                        fontSize = 11.sp,
                                        fontFamily = Fonts.ManropeFamily,
                                        color = Color(0xFF64748B)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Contact Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = bloodBank.contact,
                                fontSize = 16.sp,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            AnimatedVisibility(
                visible = detailsVisible,
                enter = fadeIn(tween(700)) + slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Get Directions Button
                    Button(
                        onClick = {
                            val uri = "google.navigation:q=${bloodBank.latitude},${bloodBank.longitude}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                            intent.setPackage("com.google.android.apps.maps")
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFDC3545)
                        ),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (language == "Nep") "दिशा प्राप्त गर्नुहोस्" else "Get Directions",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Call Button
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${bloodBank.contact}")
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFDC3545)
                        ),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color(0xFFDC3545),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (language == "Nep") "सम्पर्क गर्नुहोस्" else "Call Blood Bank",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFFDC3545)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// Helper function to calculate distance between two coordinates (Haversine formula)
private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Radius of Earth in kilometers
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}
