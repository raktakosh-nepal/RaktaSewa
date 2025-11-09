package com.example.raktasewa.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.Utils.LanguagePreference
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.math.cos

@Composable
fun WelcomeScreen(backStack: SnapshotStateList<AllScreens>) {
    val context = LocalContext.current
    val languagePreference = LanguagePreference(context)

    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        contentVisible = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "bg_animation")

    // Multiple floating particles
    val particle1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle1"
    )

    val particle2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle2"
    )

    val particle3Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle3"
    )

    val particle4Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle4"
    )

    // Rotating gradient
    val gradientRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradient_rotation"
    )

    // Logo animations
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    val logoRotation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
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
                        Color(0xFF0A0E27),
                        Color(0xFF1A1F3A),
                        Color(0xFF2A1E35)
                    )
                )
            )
    ) {
        // Animated mesh gradient background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Rotating gradient circles
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x50FF4E50),
                        Color(0x30FC913A),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = size.width * 0.8f
                ),
                center = Offset(centerX, centerY),
                radius = size.width * 0.8f
            )
        }

        // Floating particles
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
        ) {
            // Particle 1
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x60FF6B6B),
                        Color.Transparent
                    ),
                    radius = 100.dp.toPx()
                ),
                center = Offset(size.width * 0.2f, size.height * 0.3f + particle1Y),
                radius = 100.dp.toPx()
            )

            // Particle 2
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x60FFD93D),
                        Color.Transparent
                    ),
                    radius = 80.dp.toPx()
                ),
                center = Offset(size.width * 0.8f, size.height * 0.2f + particle2Y),
                radius = 80.dp.toPx()
            )

            // Particle 3
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x604ECDC4),
                        Color.Transparent
                    ),
                    radius = 120.dp.toPx()
                ),
                center = Offset(size.width * 0.15f, size.height * 0.7f + particle3Y),
                radius = 120.dp.toPx()
            )

            // Particle 4
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x60C471ED),
                        Color.Transparent
                    ),
                    radius = 90.dp.toPx()
                ),
                center = Offset(size.width * 0.85f, size.height * 0.75f + particle4Y),
                radius = 90.dp.toPx()
            )
        }

        // Grid pattern overlay
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.03f)
        ) {
            val gridSize = 30.dp.toPx()
            for (x in 0..(size.width / gridSize).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(x * gridSize, 0f),
                    end = Offset(x * gridSize, size.height),
                    strokeWidth = 1f
                )
            }
            for (y in 0..(size.height / gridSize).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(0f, y * gridSize),
                    end = Offset(size.width, y * gridSize),
                    strokeWidth = 1f
                )
            }
        }

        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(tween(800)) + scaleIn(
                initialScale = 0.9f,
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
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Premium logo design
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 40.dp)
                ) {
                    // Outer glow rings
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size((220 + index * 40).dp)
                                .alpha(0.1f - index * 0.03f)
                                .blur(30.dp)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFF4E50),
                                            Color.Transparent
                                        )
                                    ),
                                    CircleShape
                                )
                        )
                    }

                    // Main logo container
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .scale(logoScale)
                            .rotate(logoRotation)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF6B6B),
                                        Color(0xFFFF4757)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Inner circle with glassmorphism
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🩸",
                                fontSize = 80.sp,
                                modifier = Modifier.rotate(-logoRotation)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Modern title
                Text(
                    text = "RaktaSewa",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White,
                    letterSpacing = (-1.5).sp,
                    style = MaterialTheme.typography.displayLarge.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White,
                                Color(0xFFFFB8B8)
                            )
                        )
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your Life-Saving Partner",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color.White.copy(alpha = 0.7f),
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(80.dp))

                // Premium glassmorphic card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = Color.White.copy(alpha = 0.08f),
                    shadowElevation = 0.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.12f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .padding(36.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "स्वागतम्",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "भाषा चयन गर्नुहोस्",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Modern buttons
                            ModernLanguageButton(
                                text = "🇳🇵  नेपाली",
                                gradient = listOf(Color(0xFFFF6B6B), Color(0xFFFF4757))
                            ) {
                                languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                                backStack.removeLastOrNull()
                                backStack.add(AllScreens.HomeScreen("Nep"))
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            ModernLanguageButton(
                                text = "🇺🇸  English",
                                gradient = listOf(Color(0xFF4ECDC4), Color(0xFF44A3A0))
                            ) {
                                languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                                backStack.removeLastOrNull()
                                backStack.add(AllScreens.HomeScreen("Eng"))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Footer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFFF6B6B), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Connecting Lives, Saving Lives",
                        fontSize = 12.sp,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color.White.copy(alpha = 0.6f),
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ModernLanguageButton(
    text: String,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "button_scale"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(gradient)
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
            Text(
                text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.5.sp
            )
        }
    }
}
