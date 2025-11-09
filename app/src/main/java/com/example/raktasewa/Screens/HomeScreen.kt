package com.example.raktasewa.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.ViewModels.SearchBloodGroupViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Composable
fun HomeScreen(
    backStack: SnapshotStateList<AllScreens>,
    language: String,
    viewModel: SearchBloodGroupViewModel = viewModel()
) {
    val data = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    // Animation states
    var topSectionVisible by remember { mutableStateOf(false) }
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var bloodTypesVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50)
        topSectionVisible = true
        delay(250)
        bottomSheetVisible = true
        delay(350)
        bloodTypesVisible = true
    }

    // Oscillating animations for background circles
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val circle1X by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle1X"
    )

    val circle1Y by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle1Y"
    )

    val circle2X by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle2X"
    )

    val circle2Y by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle2Y"
    )

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Section - Red Gradient Background
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Animated gradient background - Darker shades
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB71C1C),
                                Color(0xFFC62828),
                                Color(0xFFD32F2F),
                                Color(0xFFB71C1C)
                            )
                        )
                    )
            )

            // Oscillating decorative circles
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.12f)
            ) {
                drawCircle(
                    color = Color.White,
                    radius = 150.dp.toPx(),
                    center = Offset(size.width * circle1X, size.height * circle1Y)
                )
                drawCircle(
                    color = Color.White,
                    radius = 100.dp.toPx(),
                    center = Offset(size.width * circle2X, size.height * circle2Y)
                )
            }

            AnimatedVisibility(
                visible = topSectionVisible,
                enter = fadeIn(tween(500)) +
                        slideInVertically(
                            initialOffsetY = { -150 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMediumLow
                            )
                        ) +
                        scaleIn(
                            initialScale = 0.9f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Animated Logo
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier.size(80.dp),
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 12.dp
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🩸",
                                    fontSize = 40.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // RaktaSewa branding
                    Text(
                        text = "RaktaSewa",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Your Life-Saving Partner",
                        fontSize = 14.sp,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color.White.copy(alpha = 0.95f),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Location badge with animation
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White.copy(alpha = 0.25f),
                        modifier = Modifier.offset(y = floatOffset.dp / 4)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == "Nep") "ललितपुर, नेपाल" else "Lalitpur, Nepal",
                                fontSize = 14.sp,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Bottom Sheet with LazyColumn (Properly Scrollable)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(320.dp))

            AnimatedVisibility(
                visible = bottomSheetVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ) +
                        fadeIn(tween(500)) +
                        scaleIn(
                            initialScale = 0.95f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                            spotColor = Color(0xFF000000).copy(alpha = 0.2f)
                        ),
                    shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                    color = Color(0xFFFAFAFA)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp)
                    ) {
                        // Drag handle
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(5.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(Color(0xFFDDDDDD))
                                )
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        // Title
                        item {
                            Text(
                                text = if (language == "Nep")
                                    "रक्त समूह चयन गर्नुहोस्"
                                else
                                    "Find Blood Banks Near You",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF1A1A1A),
                                letterSpacing = (-0.5).sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        item { Spacer(modifier = Modifier.height(8.dp)) }

                        // Subtitle
                        item {
                            Text(
                                text = if (language == "Nep")
                                    "आवश्यक रक्त समूह चयन गर्नुहोस्"
                                else
                                    "Select your required blood group to get started",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF888888),
                                letterSpacing = 0.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }

                        item { Spacer(modifier = Modifier.height(32.dp)) }

                        // Blood Group Grid with smooth, lively animation
                        items(data.chunked(2)) { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                rowItems.forEach { bloodGroup ->
                                    val index = data.indexOf(bloodGroup)
                                    var shouldAnimate by remember { mutableStateOf(false) }

                                    LaunchedEffect(bloodTypesVisible) {
                                        if (bloodTypesVisible) {
                                            delay(index * 50L) // Faster stagger
                                            shouldAnimate = true
                                        }
                                    }

                                    val scale by animateFloatAsState(
                                        targetValue = if (shouldAnimate) 1f else 0.8f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioLowBouncy,
                                            stiffness = Spring.StiffnessLow
                                        ),
                                        label = "card_scale_$index"
                                    )

                                    val alpha by animateFloatAsState(
                                        targetValue = if (shouldAnimate) 1f else 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMediumLow
                                        ),
                                        label = "card_alpha_$index"
                                    )

                                    val offsetY by animateDpAsState(
                                        targetValue = if (shouldAnimate) 0.dp else 40.dp,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioLowBouncy,
                                            stiffness = Spring.StiffnessLow
                                        ),
                                        label = "card_offset_$index"
                                    )

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .graphicsLayer(
                                                scaleX = scale,
                                                scaleY = scale,
                                                alpha = alpha,
                                                translationY = offsetY.toPx()
                                            )
                                    ) {
                                        EnhancedBloodTypeCard(
                                            bloodGroup = bloodGroup,
                                            isSelected = viewModel.selectedBloodGroup == bloodGroup,
                                            onClick = { viewModel.selectBloodGroup(bloodGroup) }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }

                        // Search Button
                        item {
                            AnimatedVisibility(
                                visible = viewModel.selectedBloodGroup != null,
                                enter = fadeIn(tween(300)) +
                                        scaleIn(
                                            initialScale = 0.7f,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioLowBouncy,
                                                stiffness = Spring.StiffnessMedium
                                            )
                                        )
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    PremiumSearchButton(
                                        text = if (language == "Nep")
                                            "रक्त बैंकहरू खोज्नुहोस्"
                                        else
                                            "Search Blood Banks",
                                        onClick = {
                                            backStack.add(
                                                AllScreens.LoadinScreen(
                                                    message = if (language == "Nep")
                                                        "रक्त बैंकहरूबाट डाटा ल्याउँदै"
                                                    else
                                                        "Fetching blood banks...",
                                                    bloodType = viewModel.selectedBloodGroup!!,
                                                    language = language
                                                )
                                            )
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Emergency Search Link
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                backStack.add(
                                                    AllScreens.LoadinScreen(
                                                        message = if (language == "Nep")
                                                            "आपतकालीन खोज"
                                                        else
                                                            "Emergency search...",
                                                        bloodType = viewModel.selectedBloodGroup!!,
                                                        language = language
                                                    )
                                                )
                                            },
                                        shape = RoundedCornerShape(16.dp),
                                        color = Color.Transparent,
                                        border = BorderStroke(2.dp, Color(0xFFDC3545))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(vertical = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "🚨",
                                                fontSize = 20.sp
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = if (language == "Nep")
                                                    "आपतकालीन खोज"
                                                else
                                                    "Emergency Search",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = Fonts.ManropeFamily,
                                                color = Color(0xFFDC3545),
                                                letterSpacing = 0.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EnhancedBloodTypeCard(
    bloodGroup: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.03f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    Card(
        modifier = Modifier
            .scale(scale)
            .fillMaxWidth()
            .height(140.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF5F4) else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        border = BorderStroke(
            width = if (isSelected) 3.dp else 1.dp,
            color = if (isSelected) Color(0xFFE85A50) else Color(0xFFE0E0E0)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = bloodGroup,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Fonts.ManropeFamily,
                    color = if (isSelected) Color(0xFFE85A50) else Color(0xFF2C3E50),
                    letterSpacing = (-0.8).sp
                )

                if (isSelected) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(
                                Color(0xFFE85A50),
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun PremiumSearchButton(
    text: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = ""
    )

    Button(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE85A50),
                            Color(0xFFDC4F45)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.2.sp
            )
        }
    }
}