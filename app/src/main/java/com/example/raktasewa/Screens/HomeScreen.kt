package com.example.raktasewa.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Particle animations
    val particle1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val particle2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Premium gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B),
                            Color(0xFFFF5252),
                            Color(0xFFE53935)
                        )
                    )
                )
        )

        // Animated mesh gradient overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 3

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x40FFFFFF),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = size.width * 0.6f
                ),
                center = Offset(centerX, centerY),
                radius = size.width * 0.6f
            )

            // Floating particles
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x30FFFFFF),
                        Color.Transparent
                    ),
                    radius = 60.dp.toPx()
                ),
                center = Offset(size.width * 0.15f, size.height * 0.2f + particle1Y),
                radius = 60.dp.toPx()
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x30FFFFFF),
                        Color.Transparent
                    ),
                    radius = 50.dp.toPx()
                ),
                center = Offset(size.width * 0.85f, size.height * 0.25f + particle2Y),
                radius = 50.dp.toPx()
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
                // Logo
                Surface(
                    modifier = Modifier.size(90.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 16.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🩸",
                            fontSize = 45.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // RaktaSewa branding
                Text(
                    text = "RaktaSewa",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White,
                    letterSpacing = (-1).sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your Life-Saving Partner",
                    fontSize = 14.sp,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White.copy(alpha = 0.95f),
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Location badge with premium design
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.offset(y = floatOffset.dp / 4)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
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

        // Premium Bottom Sheet
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(340.dp))

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
                            elevation = 32.dp,
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                            spotColor = Color(0xFF000000).copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = Color(0xFFFAFAFA)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp, start = 28.dp, end = 28.dp, bottom = 24.dp)
                    ) {
                        // Drag handle
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFD0D0D0))
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Blood Group Grid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(0.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(data) { index, bloodGroup ->
                                var itemVisible by remember { mutableStateOf(false) }

                                LaunchedEffect(bloodTypesVisible) {
                                    if (bloodTypesVisible) {
                                        delay(index * 60L)
                                        itemVisible = true
                                    }
                                }

                                AnimatedVisibility(
                                    visible = itemVisible,
                                    enter = fadeIn(tween(400)) +
                                            scaleIn(
                                                initialScale = 0.7f,
                                                animationSpec = spring(
                                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                                    stiffness = Spring.StiffnessMedium
                                                )
                                            ) +
                                            slideInVertically(
                                                initialOffsetY = { it / 4 },
                                                animationSpec = spring(
                                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                                    stiffness = Spring.StiffnessMedium
                                                )
                                            )
                                ) {
                                    ModernBloodTypeCard(
                                        bloodGroup = bloodGroup,
                                        isSelected = viewModel.selectedBloodGroup == bloodGroup,
                                        onClick = { viewModel.selectBloodGroup(bloodGroup) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Premium Search Button
                        AnimatedVisibility(
                            visible = viewModel.selectedBloodGroup != null,
                            enter = fadeIn(tween(300)) +
                                    scaleIn(
                                        initialScale = 0.7f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioLowBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    ) +
                                    slideInVertically(
                                        initialOffsetY = { it / 3 },
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioLowBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
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
                                                "तपाईंको स्थान प्राप्त गर्दै..."
                                            else
                                                "Getting your location...",
                                            bloodType = viewModel.selectedBloodGroup ?: "",
                                            language = language
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModernBloodTypeCard(
    bloodGroup: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    Surface(
        modifier = Modifier
            .scale(scale)
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) Color.White else Color.White,
        shadowElevation = elevation,
        tonalElevation = if (isSelected) 8.dp else 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isSelected) {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF6B6B).copy(alpha = 0.1f),
                                Color(0xFFFF5252).copy(alpha = 0.05f)
                            )
                        )
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color(0xFFFAFAFA)
                            )
                        )
                    }
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Blood type icon/badge
                Surface(
                    modifier = Modifier.size(70.dp),
                    shape = CircleShape,
                    color = if (isSelected) {
                        Color(0xFFFF6B6B)
                    } else {
                        Color(0xFFF5F5F5)
                    },
                    shadowElevation = if (isSelected) 8.dp else 0.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = bloodGroup,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = Fonts.ManropeFamily,
                            color = if (isSelected) Color.White else Color(0xFF2C3E50)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isSelected) "Selected" else "Select",
                    fontSize = 12.sp,
                    fontFamily = Fonts.ManropeFamily,
                    color = if (isSelected) Color(0xFFFF6B6B) else Color(0xFF999999),
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
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
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
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
            .scale(scale)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color(0xFFFF6B6B).copy(alpha = 0.5f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B),
                            Color(0xFFFF5252)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
