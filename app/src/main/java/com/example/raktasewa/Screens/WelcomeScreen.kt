package com.example.raktasewa.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
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
    var logoVisible by remember { mutableStateOf(false) }
    var namasteyVisible by remember { mutableStateOf(false) }
    var cardVisible by remember { mutableStateOf(false) }
    var brandingVisible by remember { mutableStateOf(false) }

    // Trigger animations in sequence
    LaunchedEffect(Unit) {
        delay(150)
        logoVisible = true
        delay(600)
        namasteyVisible = true
        delay(400)
        cardVisible = true
        delay(300)
        brandingVisible = true
    }

    // Infinite pulsing animation for logo
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // Glowing effect
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFFFF9F7),
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Animated Logo Section
            AnimatedVisibility(
                visible = logoVisible,
                enter = fadeIn(animationSpec = tween(1000, easing = EaseOutCubic)) +
                        slideInVertically(
                            initialOffsetY = { -150 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    // Outer glow effect
                    Surface(
                        modifier = Modifier
                            .size(190.dp)
                            .alpha(glowAlpha),
                        shape = CircleShape,
                        color = Color(0xFFFFD4CD)
                    ) {}

                    // Main logo with shadow
                    Surface(
                        modifier = Modifier
                            .scale(logoScale)
                            .size(160.dp)
                            .shadow(
                                elevation = 20.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFFFF8A7E).copy(alpha = 0.5f)
                            ),
                        shape = CircleShape,
                        color = Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFF9B8E),
                                            Color(0xFFE85A50),
                                            Color(0xFFD84639)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🩸",
                                fontSize = 80.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Animated "Namaste" text
            AnimatedVisibility(
                visible = namasteyVisible,
                enter = fadeIn(animationSpec = tween(800)) +
                        slideInVertically(
                            initialOffsetY = { 80 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
            ) {
                Text(
                    text = "नमस्ते",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color(0xFFE85A50),
                    letterSpacing = (-0.5).sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Animated Language Selection Card
            AnimatedVisibility(
                visible = cardVisible,
                enter = fadeIn(animationSpec = tween(1000)) +
                        slideInVertically(
                            initialOffsetY = { 150 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp),
                            spotColor = Color(0xFFE85A50).copy(alpha = 0.15f)
                        ),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFBF9)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "स्वागतम्",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF2E2E2E),
                            textAlign = TextAlign.Center,
                            letterSpacing = (-0.5).sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "भाषा चयन गर्नुहोस्",
                            fontSize = 14.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF999999),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Nepali Button
                        ModernLanguageButton(
                            text = "नेपाली",
                            flag = "🇳🇵",
                            backgroundColor = Color(0xFFE85A50),
                            shadowColor = Color(0xFFE85A50).copy(alpha = 0.3f)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_NEPALI)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Nep"))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // English Button
                        ModernLanguageButton(
                            text = "English",
                            flag = "🇺🇸",
                            backgroundColor = Color(0xFF2C3E50),
                            shadowColor = Color(0xFF2C3E50).copy(alpha = 0.3f)
                        ) {
                            languagePreference.saveLanguage(LanguagePreference.LANGUAGE_ENGLISH)
                            backStack.removeLastOrNull()
                            backStack.add(AllScreens.HomeScreen("Eng"))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Animated Bottom Branding
            AnimatedVisibility(
                visible = brandingVisible,
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "रक्तसेवा",
                        fontSize = 13.sp,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " • ",
                        fontSize = 13.sp,
                        color = Color(0xFFAAAAAA)
                    )
                    Text(
                        text = "Blood Service",
                        fontSize = 13.sp,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ModernLanguageButton(
    text: String,
    flag: String,
    backgroundColor: Color,
    shadowColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 8.dp,
        animationSpec = tween(200),
        label = ""
    )

    Button(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .scale(scale)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(16.dp),
                spotColor = shadowColor
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = flag,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.3.sp
            )
        }
    }
}
