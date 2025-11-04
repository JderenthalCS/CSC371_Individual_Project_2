package com.example.csc_371_individual_project_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.csc_371_individual_project_2.ui.nav.NavRoute
import com.example.csc_371_individual_project_2.ui.screens.AuthChoiceScreen
import com.example.csc_371_individual_project_2.ui.screens.LoginScreen
import com.example.csc_371_individual_project_2.ui.screens.RegisterScreen
import com.example.csc_371_individual_project_2.ui.screens.SplashScreen
import com.example.csc_371_individual_project_2.ui.theme.CodeIQTheme
import com.example.csc_371_individual_project_2.ui.screens.RulesScreen
import com.example.csc_371_individual_project_2.ui.screens.QuizScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.csc_371_individual_project_2.ui.screens.ResultScreen
import com.example.csc_371_individual_project_2.ui.screens.HistoryScreen



/**
 * MAIN ACTIVITY
 *  - Starting point of the application
 *  - Loads UI and sets up inter-screen navigation
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { // Compose UI
            CodeIQTheme { // Wraps entire UI inside CodeIQTheme (Applies Color, typography, and look)
                val navController = rememberNavController() // Handles moving between screens

                // Navigation Destinations
                NavHost(
                    navController = navController,              // NavController
                    startDestination = NavRoute.Splash.route    // SplashScreen
                ) {

                    /**
                     * SPLASH SCREEN
                     *  - Includes CodeIQ logo
                     *  - Short delay > Transition
                     */
                    composable(NavRoute.Splash.route) {
                        SplashScreen(
                            // Splash Finished > AuthChoice
                            onFinished = {
                                navController.navigate(NavRoute.AuthChoice.route) {
                                    // Remove splash from backstack so users cant return to it
                                    popUpTo(NavRoute.Splash.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    /**
                     * AUTH CHOICE
                     *  - Let's user pick between Login or Register
                     */
                    composable(NavRoute.AuthChoice.route) {
                        AuthChoiceScreen(
                            onLogin = { navController.navigate(NavRoute.Login.route) },      // GOTO Login
                            onRegister = { navController.navigate(NavRoute.Register.route) } // GOTO Register
                        )
                    }

                    /**
                     * LOGIN
                     *  - Displays field for EMAIL & PASSWORD
                     */
                    composable(NavRoute.Login.route) {
                        LoginScreen(
                            onLoggedIn = { navController.navigate(NavRoute.Rules.route)},
                            onBack = { navController.popBackStack() } // Return to previous screen
                        )
                    }

                    /**
                     * REGISTER
                     *  - Displays new user registration fields
                     */
                    composable(NavRoute.Register.route) {
                        RegisterScreen(
                            onRegistered = { navController.popBackStack() }, // Return to AuthChoice
                            onBack = { navController.popBackStack() }
                        )
                    }

                    /**
                     * RULES
                     *  - Displays rules
                     */
                    composable(NavRoute.Rules.route){
                        RulesScreen(
                            onStartQuiz = { navController.navigate(NavRoute.Quiz.route) },
                            onBack = { navController.popBackStack() },
                            onOpenHistory = { navController.navigate(NavRoute.History.route) }
                        )
                    }

                    /**
                     * QUIZ
                     *  - Display Quiz
                     */
                    composable(NavRoute.Quiz.route) {
                        QuizScreen(
                            onFinished = { s, t -> navController.navigate("result/$s/$t") }
                        )
                    }

                    composable(
                        route = NavRoute.Result.route,
                        arguments = listOf(
                            navArgument("score") { type = NavType.IntType }, // <-- fixed
                            navArgument("total") { type = NavType.IntType }  // <-- fixed
                        )
                    ) { backStackEntry -> // <-- fixed casing
                        val score = backStackEntry.arguments?.getInt("score") ?: 0 // <-- fixed variable name
                        val total = backStackEntry.arguments?.getInt("total") ?: 0 // <-- fixed variable name
                        ResultScreen(
                            score = score,
                            total = total,
                            onHome = { navController.popBackStack(NavRoute.AuthChoice.route, false) },
                            onRetry = { navController.navigate(NavRoute.Quiz.route) } // <-- added missing comma above
                        )
                    }

                    composable(NavRoute.History.route) {
                        HistoryScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(NavRoute.Rules.route){
                        RulesScreen(
                            onStartQuiz = { navController.navigate(NavRoute.Quiz.route) },
                            onBack = { navController.popBackStack() },
                            onOpenHistory = { navController.navigate(NavRoute.History.route) }
                        )
                    }



                }
            }
        }
    }
}
