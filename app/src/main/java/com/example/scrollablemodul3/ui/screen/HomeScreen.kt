package com.example.scrollablemodul3.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.scrollablemodul3.R
import com.example.scrollablemodul3.domain.model.Category
import com.example.scrollablemodul3.domain.model.Game
import com.example.scrollablemodul3.ui.ScrollableUiState
import com.example.scrollablemodul3.ui.SortMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onSettingClick: () -> Unit,
    onSortModeChange: (SortMode) -> Unit,
    onExit: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(stringResource(R.string.head_title))
        },
        navigationIcon = {
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        actions = {
            IconButton(onClick = { showMenu = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.sort_button)
                )
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.sort_by_name)) },
                    onClick = {
                        onSortModeChange(SortMode.NAME)
                        showMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.sort_by_order)) },
                    onClick = {
                        onSortModeChange(SortMode.ORDER)
                        showMenu = false
                    }
                )
            }
            IconButton(onClick = onSettingClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.setting_button)
                )
            }
        }
    )
}

@Composable
fun HomeScreen(
    uiState: ScrollableUiState,
    onDetailClick: (Int) -> Unit,
    onIntentClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onSortModeChange: (SortMode) -> Unit,
    onMoveUp: (Category) -> Unit,
    onMoveDown: (Category) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            HomeAppBar(
                onSettingClick = onSettingsClick,
                onSortModeChange = onSortModeChange,
                onExit = onExit
            )
        }
    ) { innerPadding ->
        val categories = remember(uiState.categoriesWithGames, uiState.sortMode, uiState.selectedLocale) {
            val keys = uiState.categoriesWithGames.keys.toList()
            when (uiState.sortMode) {
                SortMode.NAME -> keys.sortedBy { context.getString(it.nameResId) }
                SortMode.ORDER -> keys.sortedBy { it.displayOrder }
            }
        }
        val allGames = uiState.categoriesWithGames.values.flatten()

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            if (allGames.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.featured_games),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                    ItemCarousel(
                        items = allGames,
                        onDetailClick = { gameId ->
                            val index = allGames.indexOfFirst { it.id == gameId }
                            onDetailClick(index)
                        }
                    )
                }
            }

            categories.forEach { category ->
                item {
                    CategoryHeader(
                        category = category,
                        onToggleFavorite = { onToggleFavorite(category.id) },
                        onMoveUp = { onMoveUp(category) },
                        onMoveDown = { onMoveDown(category) }
                    )
                }
                items(uiState.categoriesWithGames[category] ?: emptyList()) { game ->
                    ItemCard(
                        item = game,
                        onDetailClick = {
                            val index = allGames.indexOfFirst { it.id == game.id }
                            onDetailClick(index)
                        },
                        onIntentClick = { onIntentClick(game.steamUrl) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryHeader(
    category: Category,
    onToggleFavorite: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(category.nameResId),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onMoveUp) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Move Up")
        }
        IconButton(onClick = onMoveDown) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Move Down")
        }
        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(R.string.favorite_category_content_description),
                tint = if (category.isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}

@Composable
fun ItemCard(
    item: Game,
    onDetailClick: () -> Unit,
    onIntentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = stringResource(id = item.titleResId),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = item.titleResId),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(id = item.subtitleResId),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(id = item.descriptionResId),
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        onClick = onIntentClick,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.steam_button),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = onDetailClick,
                    ) {
                        Text(
                            text = stringResource(R.string.detail_button),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarouselCard(
    item: Game,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onDetailClick,
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = item.detailImageResId),
                contentDescription = stringResource(id = item.titleResId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(400.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun ItemCarousel(
    items: List<Game>,
    onDetailClick: (Int) -> Unit,
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState)
    ) {
        items(items) { item ->
            CarouselCard(
                item = item,
                onDetailClick = { onDetailClick(item.id) },
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}
