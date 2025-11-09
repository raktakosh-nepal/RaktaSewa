package com.example.raktasewa.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.Surface
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.alpha
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

    // Animation states with staggered timing
    var contentVisible by remember { mutableStateOf(false) }

    // Trigger animations in sequence
    LaunchedEffect(Unit) {
        delay(100) // Faster start
        contentVisible = true
    }

    // Infinite animations for floating elements
    val infiniteTransition = rememberInfiniteTransition(label = "background_animation")

    // Floating orb 1 animation - more dynamic movement
    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 50f, // Increased range
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine), // Faster
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )

    // Floating orb 2 animation - more dynamic movement
    val orb2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -60f, // Increased range
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine), // Faster
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2"
    )

    // Add rotation to orbs for more dynamism
    val orb1Rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb1_rotation"
    )

    val orb2Rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb2_rotation"
    )

    // More dynamic breathing animation for logo with rotation
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f, // More pronounced
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic), // Faster
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_breathing"
    )

    val logoRotation by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF5F3),
                        Color(0xFFFFFFFF),
                        Color(0xFFFFF9F7)
                    )
                )
            )
    ) {
        // Floating decorative orbs (background elements) with rotation
        Box(
            modifier = Modifier
                .offset(x = 60.dp, y = (120 + orb1Offset).dp)
                .size(180.dp)
                .rotate(orb1Rotation)
                .blur(80.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFB8B0).copy(alpha = 0.4f),
                            Color(0xFFFF8A7E).copy(alpha = 0.2f)
                        )
                    ),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp, y = (orb2Offset - 100).dp)
                .size(220.dp)
                .rotate(orb2Rotation)
                .blur(90.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF8A7E).copy(alpha = 0.35f),
                            Color(0xFFFF6B5F).copy(alpha = 0.15f)
                        )
                    ),
                    CircleShape
                )
        )

        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(animationSpec = tween(600, easing = EaseOutCubic)) +
                    scaleIn(
                        initialScale = 0.9f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) +
                    slideInVertically(
                        initialOffsetY = { it / 10 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                // Modern Logo with glassmorphism effect
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 24.dp)
                ) {
                    // Soft glow background
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .blur(50.dp)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFF8A7E).copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                CircleShape
                            )
                    )

                    // Main logo container with glassmorphism and dynamic animations
                    Box(
                        modifier = Modifier
                            .scale(logoScale)
                            .rotate(logoRotation)
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF6B5F),
                                        Color(0xFFE74C3C)
                                    )
                                )
                            )
                            .border(
                                width = 3.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.5f),
                                        Color.White.copy(alpha = 0.1f)
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🩸",
                            fontSize = 72.sp,
                            modifier = Modifier.rotate(-logoRotation) // Counter-rotate emoji to keep it upright
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Modern heading with gradient text effect
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "RaktaSewa",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFFDC3545),
                        letterSpacing = (-1).sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Your Life-Saving Partner",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF888888),
                        letterSpacing = 0.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Glassmorphic Language Selection Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.9f),
                                    Color.White.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .border(
                            width = 1.5.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.8f),
                                    Color.White.copy(alpha = 0.3f)
                                )
                            ),
                            shape = RoundedCornerShape(32.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp, vertical = 44.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "स्वागतम्",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF1A1A1A),
                            textAlign = TextAlign.Center,
                            letterSpacing = (-0.3).sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "भाषा चयन गर्नुहोस्",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF888888),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Nepali Language Option
                        UltraModernLanguageButton(
                            primaryText = "🇳🇵 नेपाली",
                            secondaryText = "",
                            flag = "",
                            accentColor = Color(0xFFDC3545),
                            isSelected = false,
                            backgroundColor = Color(0xFFDC3545)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Nep"))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // English Language Option
                        UltraModernLanguageButton(
                            primaryText = "🇺🇸 English",
                            secondaryText = "",
                            flag = "",
                            accentColor = Color(0xFF1E293B),
                            isSelected = false,
                            backgroundColor = Color(0xFF1E293B)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Eng"))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom tagline
                Text(
                    text = "Connecting Lives, Saving Lives",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun UltraModernLanguageButton(
    primaryText: String,
    secondaryText: String,
    flag: String,
    accentColor: Color,
    isSelected: Boolean,
    backgroundColor: Color = accentColor,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f, // More pronounced scale
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy, // More bouncy!
            stiffness = Spring.StiffnessHigh
        ),
        label = "button_scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 8.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "button_elevation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .height(64.dp),
        shape = RoundedCornerShape(14.dp),
        shadowElevation = elevation,
        color = backgroundColor
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
                text = primaryText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.sp
            )
        }
    }
}
