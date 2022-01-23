package com.io.github.rio_sh.quickwordbook.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.io.github.rio_sh.quickwordbook.data.Word

@Composable
fun HomeBody(
    onFabClicked: () -> Unit = {},
    onCardClicked: (wordId: Int?) -> Unit = {}
) {
    // TODO Implement HomeBody
    Text(text = "This is Home Screen")
}