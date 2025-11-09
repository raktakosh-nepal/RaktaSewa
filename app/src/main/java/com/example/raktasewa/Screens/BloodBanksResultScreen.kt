package com.example.raktasewa.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.raktasewa.Constants.Fonts
import com.example.raktasewa.Models.BloodBank
import com.example.raktasewa.Nav.AllScreens
import com.example.raktasewa.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun BloodBanksResultScreen(
    backStack: SnapshotStateList<AllScreens>,
    bloodBanks: List<BloodBank>,
    language: String
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Title
            Text(
                text = if (language == "Nep") "रक्त बैंक निर्देशिका" else "Blood Bank Directory",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.ManropeFamily,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Count with icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFFDC3545),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (language == "Nep")
                        "${bloodBanks.size} रक्त बैंक नजिकै फेला पर्यो"
                    else
                        "${bloodBanks.size} blood banks found nearby",
                    fontSize = 14.sp,
                    fontFamily = Fonts.ManropeFamily,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                placeholder = {
                    Text(
                        text = if (language == "Nep")
                            "रक्त बैंक, स्थान, प्रकार खोज्नुहोस्..."
                        else
                            "Search blood banks, locations, types...",
                        fontSize = 14.sp,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF94A3B8)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE2E8F0),
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Fonts.ManropeFamily
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (bloodBanks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "😔",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (language == "Nep")
                                "कुनै रक्त बैंक फेला परेन"
                            else
                                "No blood banks found",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Fonts.ManropeFamily,
                            color = DarkGray
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(bloodBanks) { index, bloodBank ->
                        var visible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            delay(index * 50L)
                            visible = true
                        }

                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn() + slideInVertically(
                                initialOffsetY = { it / 2 }
                            )
                        ) {
                            BloodBankCard(
                                bloodBank = bloodBank,
                                language = language,
                                index = index,
                                onCallClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${bloodBank.contact}")
                                    }
                                    context.startActivity(intent)
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
fun BloodBankCard(
    bloodBank: BloodBank,
    language: String,
    index: Int,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Hospital header with icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hospital icon/image placeholder
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFF0F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏥",
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = bloodBank.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color(0xFF1E293B),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = bloodBank.address,
                            fontSize = 13.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF64748B),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = bloodBank.contact,
                            fontSize = 13.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Blood info card
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
                            fontSize = 11.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFDC3545)
                        ) {
                            Text(
                                text = bloodBank.type,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Fonts.ManropeFamily,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Stock status
                        val isCritical = bloodBank.quantity < 10
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCritical) Color(0xFFFEE2E2) else Color(0xFFDCFCE7)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isCritical) "⚠️" else "✓",
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isCritical) {
                                        if (language == "Nep") "महत्वपूर्ण स्टक" else "Critical Stock"
                                    } else {
                                        if (language == "Nep") "राम्रो स्टक" else "Good Stock"
                                    },
                                    fontSize = 11.sp,
                                    fontFamily = Fonts.ManropeFamily,
                                    color = if (isCritical) Color(0xFFDC2626) else Color(0xFF16A34A),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = if (language == "Nep") "उपलब्ध एकाइहरू" else "Available Units",
                            fontSize = 11.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${bloodBank.quantity.roundToInt()}.0",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFFDC3545),
                            letterSpacing = (-1).sp
                        )
                        Text(
                            text = if (language == "Nep") "एकाइहरू" else "units",
                            fontSize = 12.sp,
                            fontFamily = Fonts.ManropeFamily,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact button
            Button(
                onClick = onCallClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDC3545)
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (language == "Nep") "रक्त बैंक सम्पर्क गर्नुहोस्" else "Contact Blood Bank",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Fonts.ManropeFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRowModern(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = BloodRed,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                fontFamily = Fonts.ManropeFamily,
                color = DarkGray.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 13.sp,
                fontFamily = Fonts.ManropeFamily,
                color = DarkGray,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
