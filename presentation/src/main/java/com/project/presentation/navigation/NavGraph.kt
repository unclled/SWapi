package com.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.presentation.features.character.detail.CharacterDetailScreen
import com.project.presentation.features.character.list.CharactersListScreen

@Composable
fun SwapiNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CharactersListRoute
    ) {
        composable<CharactersListRoute> {
            CharactersListScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(CharacterDetailRoute(characterId = id))
                }
            )
        }

        composable<CharacterDetailRoute> { backStackEntry ->
            CharacterDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}