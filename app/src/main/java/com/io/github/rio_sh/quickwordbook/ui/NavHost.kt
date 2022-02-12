package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.io.github.rio_sh.quickwordbook.ui.add.AddCardBody
import com.io.github.rio_sh.quickwordbook.ui.cards.CardsBody
import com.io.github.rio_sh.quickwordbook.ui.edit.EditCardBody
import com.io.github.rio_sh.quickwordbook.ui.home.HomeBody
import com.io.github.rio_sh.quickwordbook.ui.home.HomeRoute
import com.io.github.rio_sh.quickwordbook.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeRoute(
                homeViewModel = homeViewModel,
                onAddFabClicked = { navController.navigate(QuickWordBookScreen.AddCard.name) },
                onCardsFabClicked = { navController.navigate(QuickWordBookScreen.Cards.name) },
                onEditClicked = { wordId -> navigateToEdit(wordId, navController) }
            )
        }

        composable(QuickWordBookScreen.AddCard.name) {
            AddCardBody(
                onAddDoneButtonClicked = { navController.navigate(QuickWordBookScreen.Home.name) }
            )
        }

        composable(QuickWordBookScreen.Cards.name) {
            CardsBody(
                onEditClicked = { wordId -> navigateToEdit(wordId, navController) }
            )
        }

        composable(
            "${QuickWordBookScreen.EditCard.name}/{wordId}",
            arguments = listOf(
                navArgument("wordId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            EditCardBody(
                wordId = backStackEntry.arguments!!.getInt("wordId"),
                onEditDoneButtonClicked = { navController.popBackStack() }
            )
        }
    }
}

private fun navigateToEdit(
    wordId: Int,
    navController: NavHostController
) {
    navController.navigate("${QuickWordBookScreen.EditCard.name}/${wordId}")
}