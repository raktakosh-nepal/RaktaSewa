package com.example.raktasewa.Screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raktasewa.Composables.CustomButton
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.Utils.LanguagePreference
import kotlin.math.*

@Composable
fun WelcomeScreen(backStack: SnapshotStateList<AllScreens>) {
    val context = LocalContext.current
    val languagePreference = LanguagePreference(context)

    val infiniteTransition = rememberInfiniteTransition(label = "animations")

    val scaleAnimation by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "card_scale"
    )

    val orb1Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1_y"
    )

    val orb2Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2_y"
    )

    val orb3Y by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3_y"
    )

    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 60f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_radius"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A), // Dark slate
                        Color(0xFF1E293B), // Slate 800
                        Color(0xFF334155)  // Slate 700
                    )
                )
            )
    ) {
        // Floating background orbs
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Orb 1 - Top Left
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x40DC2626),
                        Color(0x10DC2626),
                        Color.Transparent
                    ),
                    radius = 120.dp.toPx()
                ),
                center = Offset(
                    x = size.width * 0.15f,
                    y = size.height * 0.2f + orb1Y
                ),
                radius = 120.dp.toPx()
            )

            // Orb 2 - Top Right
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x40059669),
                        Color(0x10059669),
                        Color.Transparent
                    ),
                    radius = 80.dp.toPx()
                ),
                center = Offset(
                    x = size.width * 0.85f,
                    y = size.height * 0.15f + orb2Y
                ),
                radius = 80.dp.toPx()
            )

            // Orb 3 - Bottom Right
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x402563EB),
                        Color(0x102563EB),
                        Color.Transparent
                    ),
                    radius = 100.dp.toPx()
                ),
                center = Offset(
                    x = size.width * 0.9f,
                    y = size.height * 0.8f + orb3Y
                ),
                radius = 100.dp.toPx()
            )
        }

        // Subtle grid pattern overlay
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.05f)
        ) {
            val gridSize = 40.dp.toPx()
            for (x in 0..(size.width / gridSize).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(x * gridSize, 0f),
                    end = Offset(x * gridSize, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            for (y in 0..(size.height / gridSize).toInt()) {
                drawLine(
                    color = Color.White,
                    start = Offset(0f, y * gridSize),
                    end = Offset(size.width, y * gridSize),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Content Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scaleAnimation)
                    .clip(RoundedCornerShape(32.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 24.dp
                ),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color(0xFFFAFAFA)
                                )
                            )
                        )
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Animated logo container
                    Box(
                        modifier = Modifier
                            .size(240.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFEF2F2),
                                        Color(0xFFF1F5F9)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Pulsing ring effect
                        Canvas(
                            modifier = Modifier.size(200.dp)
                        ) {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0x20DC2626),
                                        Color.Transparent
                                    )
                                ),
                                radius = pulseRadius,
                                center = center
                            )
                        }

                        // Blood drop icon
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFDC2626)),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(
                                modifier = Modifier.size(40.dp)
                            ) {
                                val path = Path().apply {
                                    val centerX = size.width / 2
                                    val centerY = size.height / 2
                                    val radius = size.width / 4

                                    // Teardrop shape
                                    moveTo(centerX, centerY - radius * 1.5f)

                                    // Left curve
                                    cubicTo(
                                        centerX - radius * 1.0f, centerY - radius * 0.5f,
                                        centerX - radius * 1.0f, centerY + radius * 0.5f,
                                        centerX, centerY + radius
                                    )

                                    // Right curve
                                    cubicTo(
                                        centerX + radius * 1.0f, centerY + radius * 0.5f,
                                        centerX + radius * 1.0f, centerY - radius * 0.5f,
                                        centerX, centerY - radius * 1.5f
                                    )

                                    close()
                                }

                                drawPath(
                                    path = path,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Welcome text
                    Text(
                        text = "स्वागतम्",
                        fontFamily = Fonts.ManropeFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 42.sp,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "भाषा चयन गर्नुहोस्",
                        fontFamily = Fonts.ManropeFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    // Language selection with gradient borders
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Nepali Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFDC2626),
                                            Color(0xFFEF4444)
                                        )
                                    )
                                )
                                .padding(2.dp)
                        ) {
                            CustomButton(
                                color = Color.Transparent,
                                text = "🇳🇵  नेपाली"
                            ) {
                                languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                                backStack.removeLastOrNull()
                                backStack.add(AllScreens.HomeScreen("Nep"))
                            }
                        }

                        // English Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF1E293B),
                                            Color(0xFF334155)
                                        )
                                    )
                                )
                                .padding(2.dp)
                        ) {
                            CustomButton(
                                color = Color.Transparent,
                                text = "🇺🇸  English"
                            ) {
                                languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                                backStack.removeLastOrNull()
                                backStack.add(AllScreens.HomeScreen("Eng"))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "🩸 रक्तसेवा • Blood Service",
                    fontFamily = Fonts.ManropeFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}
