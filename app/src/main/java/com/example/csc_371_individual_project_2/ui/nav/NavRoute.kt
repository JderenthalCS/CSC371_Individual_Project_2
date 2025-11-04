package com.example.csc_371_individual_project_2.ui.nav

/**
 * NAVROUTE
 *  - Lists all destinations in app
 *
 */
sealed class NavRoute(val route: String) { // Sealed = Safer

    data object Splash : NavRoute("splash")              // Splash screen route

    data object AuthChoice : NavRoute("auth_choice")     // The screen that lets the user choose Login or Register.

    data object Login : NavRoute("login")                // Login screen route

    data object Register : NavRoute("register")          // Register screen route

    data object Rules : NavRoute("rules")                // Rules screen route

    data object Quiz : NavRoute("quiz")                  // Quiz screen route

    data object Result : NavRoute("result/{score}/{total}")         // Results screen route

    data object History : NavRoute("history")

}