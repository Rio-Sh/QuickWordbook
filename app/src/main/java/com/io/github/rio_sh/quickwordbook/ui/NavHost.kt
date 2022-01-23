package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.io.github.rio_sh.quickwordbook.ui.cards.CardsBody
import com.io.github.rio_sh.quickwordbook.ui.detail.DetailBody
import com.io.github.rio_sh.quickwordbook.ui.home.HomeBody

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = QuickWordBookScreen.Home.name,
        modifier = modifier
    ) {
        composable(QuickWordBookScreen.Home.name) {
            HomeBody(
                onFabClicked = { navController.navigate(QuickWordBookScreen.Detail.name) }
            )
        }

        composable(QuickWordBookScreen.Cards.name) {
            CardsBody(
                onCardClicked = { wordId -> navigateToDetail(wordId, navController) }
            )
        }

        composable(
            "${QuickWordBookScreen.Detail.name}?wordId={wordId}",
            arguments = listOf(
                navArgument("wordId") {
                nullable = true
                defaultValue = null
                type = NavType.IntType
            })
        ) { backStackEntry ->
            DetailBody(
                wordId = backStackEntry.arguments?.getInt("wordId"),
                onEditDoneButtonClicked = { navController.navigate(QuickWordBookScreen.Home.name) }
            )
        }
    }
}

private fun navigateToDetail(
    wordId: Int?,
    navController: NavHostController
) {
    navController.navigate("${QuickWordBookScreen.Detail.name}?wordId=${wordId}")
}