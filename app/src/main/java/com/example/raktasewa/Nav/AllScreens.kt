package com.example.raktasewa.Nav

import com.example.raktasewa.Models.BloodBank

sealed class AllScreens {
    data object WelcomeScreen: AllScreens()
    data class HomeScreen(val language: String): AllScreens()
    data class LoadinScreen(val message: String, val bloodType: String, val language: String): AllScreens()
    data class BloodBanksResultScreen(val bloodBanks: List<BloodBank>, val language: String): AllScreens()
}