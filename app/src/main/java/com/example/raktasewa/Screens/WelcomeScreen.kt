package com.example.raktasewa.Screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.Utils.LanguagePreference
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(backStack: SnapshotStateList<AllScreens>) {
    val context = LocalContext.current
    val languagePreference = LanguagePreference(context)

    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        contentVisible = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "ambient")

    // Dynamic orb movements
    val orb1X by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1x"
    )

    val orb1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1y"
    )

    val orb2X by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2x"
    )

    val orb2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(6500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2y"
    )

    val orb3X by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3x"
    )

    val orb3Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(7500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3y"
    )

    val orb4X by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb4x"
    )

    val orb4Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(8500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb4y"
    )

    // Dynamic logo animations - more lively!
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    val logoGlow by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_glow"
    )

    val logoRotate by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_rotate"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFAF8),
                        Color(0xFFFFFFFF),
                        Color(0xFFFFF8F6)
                    )
                )
            )
    ) {
        // Lively floating circles in the background
        // Circle 1 - Top Left
        Box(
            modifier = Modifier
                .offset(x = (orb1X - 80).dp, y = (100 + orb1Y).dp)
                .size(280.dp)
                .clip(CircleShape)
                .blur(100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF6B6B).copy(alpha = 0.35f),
                            Color(0xFFFFB3B3).copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        // Circle 2 - Top Right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (orb2X + 60).dp, y = (150 + orb2Y).dp)
                .size(220.dp)
                .clip(CircleShape)
                .blur(90.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDC3545).copy(alpha = 0.4f),
                            Color(0xFFFF9999).copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        // Circle 3 - Bottom Left
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (40 + orb3X).dp, y = (-120 + orb3Y).dp)
                .size(240.dp)
                .clip(CircleShape)
                .blur(95.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF4444).copy(alpha = 0.3f),
                            Color(0xFFFFCCCC).copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        // Circle 4 - Center Right
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (orb4X + 20).dp, y = (orb4Y - 50).dp)
                .size(200.dp)
                .clip(CircleShape)
                .blur(85.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF8080).copy(alpha = 0.35f),
                            Color(0xFFFFDDDD).copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        // Circle 5 - Bottom Right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-orb1X - 40).dp, y = (-200 - orb1Y).dp)
                .size(180.dp)
                .clip(CircleShape)
                .blur(80.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDC3545).copy(alpha = 0.3f),
                            Color(0xFFFFB3B3).copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
        )

        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(tween(700, easing = FastOutSlowInEasing)) +
                    scaleIn(
                        initialScale = 0.88f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(90.dp))

                // Eye-catching animated logo with lovely red halo
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    // Outer red halo - softer and larger - perfectly circular
                    Box(
                        modifier = Modifier
                            .size(260.dp)
                            .clip(CircleShape)
                            .blur(90.dp)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFF6B6B).copy(alpha = logoGlow * 0.5f),
                                        Color(0xFFFF4444).copy(alpha = logoGlow * 0.3f),
                                        Color.Transparent
                                    )
                                ),
                                CircleShape
                            )
                    )

                    // Inner red glow - more intense - perfectly circular
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .blur(70.dp)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFDC3545).copy(alpha = logoGlow),
                                        Color(0xFFFF6B6B).copy(alpha = logoGlow * 0.6f),
                                        Color.Transparent
                                    )
                                ),
                                CircleShape
                            )
                    )

                    // Outer ring with subtle pulsing
                    Box(
                        modifier = Modifier
                            .rotate(logoRotate)
                            .scale(logoScale)
                            .size(180.dp)
                            .border(
                                width = 3.dp,
                                color = Color(0xFFFFD6D6).copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                    )

                    // Main logo with rotation and scale
                    Box(
                        modifier = Modifier
                            .rotate(logoRotate)
                            .scale(logoScale)
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(
                                width = 4.dp,
                                color = Color(0xFFFFE5E5).copy(alpha = 0.6f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🩸",
                            fontSize = 72.sp,
                            modifier = Modifier.scale(1.05f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Text section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "RaktaSewa",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFFDC3545),
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your Life-Saving Partner",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF666666),
                        letterSpacing = 0.3.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Modern card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp, vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "स्वागतम्",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF1A1A1A),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "भाषा चयन गर्नुहोस्",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF888888),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        VibrantLanguageButton(
                            text = "🇳🇵 नेपाली",
                            backgroundColor = Color(0xFFDC3545)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Nep"))
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        VibrantLanguageButton(
                            text = "🇺🇸 English",
                            backgroundColor = Color(0xFF1E293B)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Eng"))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Connecting Lives, Saving Lives",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun VibrantLanguageButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "elevation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .height(58.dp),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = elevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        isPressed = true
                        onClick()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.2.sp
            )
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}