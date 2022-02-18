package com.io.github.rio_sh.quickwordbook.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.io.github.rio_sh.quickwordbook.ui.add.AddCardBody
import com.io.github.rio_sh.quickwordbook.ui.add.AddCardRoute
import com.io.github.rio_sh.quickwordbook.ui.add.AddCardViewModel
import com.io.github.rio_sh.quickwordbook.ui.cards.CardsBody
import com.io.github.rio_sh.quickwordbook.ui.cards.CardsRoute
import com.io.github.rio_sh.quickwordbook.ui.cards.CardsViewModel
import com.io.github.rio_sh.quickwordbook.ui.edit.EditCardBody
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
        startDestination = QuickWordbookScreen.Home.name,
        modifier = modifier
    ) {
        composable(QuickWordbookScreen.Home.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeRoute(
                homeViewModel = homeViewModel,
                onAddFabClicked = { navController.navigate(QuickWordbookScreen.AddCard.name) },
                onCardsFabClicked = { navController.navigate(QuickWordbookScreen.Cards.name) },
                onEditClicked = { wordId -> navigateToEdit(wordId, navController) }
            )
        }

        composable(QuickWordbookScreen.AddCard.name) {
            val addCardViewModel = hiltViewModel<AddCardViewModel>()
            AddCardRoute(
                addCardViewModel = addCardViewModel,
                onAddWord = { navController.popBackStack() },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable(QuickWordbookScreen.Cards.name) {
            val cardsViewModel = hiltViewModel<CardsViewModel>()
            CardsRoute(
                cardsViewModel = cardsViewModel,
                onBackClicked = { navController.navigate(QuickWordbookScreen.Home.name) {
                    popUpTo(navController.graph.findStartDestination().id)
                } },
                onAddFabClicked = { navController.navigate(QuickWordbookScreen.AddCard.name) },
                onEditClicked = { wordId -> navigateToEdit(wordId, navController) },
            )
        }

        composable(
            "${QuickWordbookScreen.EditCard.name}/{wordId}",
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
    navController.navigate("${QuickWordbookScreen.EditCard.name}/${wordId}")
}