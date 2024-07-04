package com.fxzly.bakeryinventorymanagement.ui.component

sealed class ScreenNav(val route: String) {
    object Product : ScreenNav("product")
    object Ingredients : ScreenNav("ingredient")
    object Supplier : ScreenNav("supplier")
    object Recipe : ScreenNav("recipe")
    object Profile : ScreenNav("profile")
}