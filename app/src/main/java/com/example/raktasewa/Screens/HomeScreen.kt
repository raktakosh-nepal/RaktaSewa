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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.ViewModels.SearchBloodGroupViewModel
import com.example.raktasewa.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.sin

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Composable
fun HomeScreen(
    backStack: SnapshotStateList<AllScreens>,
    language: String,
    viewModel: SearchBloodGroupViewModel = viewModel()
) {
    val data = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    // Animation states - faster timing!
    var topSectionVisible by remember { mutableStateOf(false) }
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var bloodTypesVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50) // Faster
        topSectionVisible = true
        delay(250) // Reduced delay
        bottomSheetVisible = true
        delay(350) // Reduced delay
        bloodTypesVisible = true
    }

    // More dynamic floating animation for decorative elements
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f, // Increased range
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine), // Faster
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Section - Red Gradient Background
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Animated gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE85A50),
                                Color(0xFFDC4F45),
                                Color(0xFFD84639),
                                Color(0xFFE85A50)
                            )
                        )
                    )
            )

            // Decorative circles
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.1f)
            ) {
                drawCircle(
                    color = Color.White,
                    radius = 150.dp.toPx(),
                    center = center.copy(x = size.width * 0.2f, y = size.height * 0.15f)
                )
                drawCircle(
                    color = Color.White,
                    radius = 100.dp.toPx(),
                    center = center.copy(x = size.width * 0.85f, y = size.height * 0.25f)
                )
            }

            AnimatedVisibility(
                visible = topSectionVisible,
                enter = fadeIn(tween(500)) +
                        slideInVertically(
                            initialOffsetY = { -150 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy, // More bounce!
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

        // Bottom Sheet Card with slide-up animation
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(320.dp))

            AnimatedVisibility(
                visible = bottomSheetVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy, // More bouncy!
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
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp, start = 28.dp, end = 28.dp, bottom = 24.dp)
                    ) {
                        // Drag handle
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFFE0E0E0))
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        // Title with icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFFFFE5E2),
                                                Color(0xFFFFD4CF)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🔍",
                                    fontSize = 24.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = if (language == "Nep")
                                    "रक्त समूह चयन गर्नुहोस्"
                                else
                                    "Select your required blood group",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color(0xFF2C3E50),
                                letterSpacing = (-0.2).sp
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Blood Group Grid with staggered animation
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
                                        delay(index * 60L) // Faster staggering
                                        itemVisible = true
                                    }
                                }

                                AnimatedVisibility(
                                    visible = itemVisible,
                                    enter = fadeIn(tween(400)) +
                                            scaleIn(
                                                initialScale = 0.7f, // More dramatic
                                                animationSpec = spring(
                                                    dampingRatio = Spring.DampingRatioLowBouncy, // More bouncy!
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
                                    EnhancedBloodTypeCard(
                                        bloodGroup = bloodGroup,
                                        isSelected = viewModel.selectedBloodGroup == bloodGroup,
                                        onClick = { viewModel.selectBloodGroup(bloodGroup) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Search Button with more energetic animation
                        AnimatedVisibility(
                            visible = viewModel.selectedBloodGroup != null,
                            enter = fadeIn(tween(300)) +
                                    scaleIn(
                                        initialScale = 0.7f, // More dramatic entrance
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
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
                                Row(
                                    modifier = Modifier.clickable {
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
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🚨",
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (language == "Nep")
                                            "आपतकालीन खोज"
                                        else
                                            "Emergency Search",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
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

@Composable
fun EnhancedBloodTypeCard(
    bloodGroup: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1.0f, // More pronounced
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy, // More bouncy!
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 4.dp, // Higher elevation
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = ""
    )

    Card(
        modifier = Modifier
            .scale(scale)
            .fillMaxWidth()
            .height(120.dp)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(20.dp),
                spotColor = if (isSelected) Color(0xFFE85A50).copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF5F4) else Color.White
        ),
        border = BorderStroke(
            width = if (isSelected) 3.dp else 1.5.dp,
            color = if (isSelected) Color(0xFFE85A50) else Color(0xFFE8E8E8)
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
                    fontWeight = FontWeight.Bold,
                    fontFamily = Fonts.ManropeFamily,
                    color = if (isSelected) Color(0xFFE85A50) else Color(0xFF2C3E50),
                    letterSpacing = (-0.5).sp
                )

                // Underline for selected state
                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(3.dp)
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
        targetValue = if (isPressed) 0.92f else 1f, // More dramatic press
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy, // More bouncy!
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
                spotColor = Color(0xFFE85A50).copy(alpha = 0.5f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE85A50),
                            Color(0xFFDC4F45),
                            Color(0xFFE85A50)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.ManropeFamily,
                color = Color.White,
                letterSpacing = 0.sp
            )
        }
    }
}
