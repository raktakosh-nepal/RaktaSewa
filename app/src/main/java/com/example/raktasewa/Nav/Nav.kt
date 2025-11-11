package com.example.raktasewa.Nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry


import androidx.navigation3.ui.NavDisplay
import com.example.raktasewa.Screens.BloodBankDetailScreen
import com.example.raktasewa.Screens.BloodBanksResultScreen
import com.example.raktasewa.Screens.HomeScreen
import com.example.raktasewa.Screens.LoadingScreen
import com.example.raktasewa.Screens.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Nav() {
    val backstack = remember { mutableStateListOf<AllScreens>(AllScreens.WelcomeScreen) }
    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = { key ->
            when(key){
                is AllScreens.WelcomeScreen-> NavEntry(key){
                    WelcomeScreen(backstack)
                }
                is AllScreens.HomeScreen -> NavEntry(key){
                    HomeScreen(backstack, key.language)
                }
                is AllScreens.LoadinScreen -> NavEntry(key){
                    LoadingScreen(backstack, key.message, key.bloodType, key.language)
                }
                is AllScreens.BloodBanksResultScreen -> NavEntry(key){
                    BloodBanksResultScreen(backstack, key.bloodBanks, key.userLatitude, key.userLongitude, key.language)
                }
                is AllScreens.BloodBankDetailScreen -> NavEntry(key){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                        BloodBankDetailScreen(backstack, key.bloodBank, key.userLatitude, key.userLongitude, key.language)
                    }
                }
            }
        }
    )
}