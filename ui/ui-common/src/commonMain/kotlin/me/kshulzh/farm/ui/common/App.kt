package me.kshulzh.farm.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import me.kshulzh.farm.client.ItemServiceHttpClient
import me.kshulzh.farm.dto.ItemDto
import me.kshulzh.farm.http.HttpClientImpl
import me.kshulzh.farm.id
import me.kshulzh.farm.ui.common.apps.ItemsApp
import me.kshulzh.farm.ui.common.apps.ItemsApp1
import me.kshulzh.farm.ui.common.apps.ItemsApp2
import me.kshulzh.farm.ui.common.components.Items
import me.kshulzh.farm.ui.common.components.ListItemItems
import me.kshulzh.farm.ui.common.components.ListItems

@Composable
fun App() {
    ItemsApp2()
}
