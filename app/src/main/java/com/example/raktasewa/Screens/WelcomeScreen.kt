package com.example.raktasewa.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
        delay(200)
        contentVisible = true
    }

    // Infinite animations for floating elements
    val infiniteTransition = rememberInfiniteTransition(label = "background_animation")

    // Floating orb 1 animation
    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )

    // Floating orb 2 animation
    val orb2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2"
    )

    // Subtle breathing animation for logo
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_breathing"
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
        // Floating decorative orbs (background elements)
        Box(
            modifier = Modifier
                .offset(x = 60.dp, y = (120 + orb1Offset).dp)
                .size(180.dp)
                .blur(80.dp)
                .background(
                    Color(0xFFFFB8B0).copy(alpha = 0.3f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp, y = (orb2Offset - 100).dp)
                .size(220.dp)
                .blur(90.dp)
                .background(
                    Color(0xFFFF8A7E).copy(alpha = 0.25f),
                    CircleShape
                )
        )

        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(animationSpec = tween(800, easing = EaseOutCubic)),
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

                    // Main logo container with glassmorphism
                    Box(
                        modifier = Modifier
                            .scale(logoScale)
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
                            fontSize = 72.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Modern heading with gradient text effect
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "रक्तसेवा",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF1A1A1A),
                        letterSpacing = (-1.5).sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Blood Service",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF666666),
                        letterSpacing = 2.sp
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
                            text = "Choose Your Language",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF888888),
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.2.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "भाषा छान्नुहोस्",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF1A1A1A),
                            textAlign = TextAlign.Center,
                            letterSpacing = (-0.5).sp
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        // Nepali Language Option
                        UltraModernLanguageButton(
                            primaryText = "नेपाली",
                            secondaryText = "Nepali",
                            flag = "🇳🇵",
                            accentColor = Color(0xFFE74C3C),
                            isSelected = false
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Nep"))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // English Language Option
                        UltraModernLanguageButton(
                            primaryText = "English",
                            secondaryText = "अंग्रेजी",
                            flag = "🇺🇸",
                            accentColor = Color(0xFF3498DB),
                            isSelected = false
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Eng"))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
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
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "button_scale"
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.3f else 0.6f,
        animationSpec = tween(200),
        label = "border_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .height(76.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.95f),
                        Color.White.copy(alpha = 0.85f)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        accentColor.copy(alpha = borderAlpha),
                        accentColor.copy(alpha = borderAlpha * 0.5f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flag with subtle background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = flag,
                        fontSize = 28.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = primaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF1A1A1A),
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = secondaryText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF888888),
                        letterSpacing = 0.2.sp
                    )
                }
            }

            // Arrow indicator
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "→",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }
        }
    }
}
