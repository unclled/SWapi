package com.project.presentation.features.character.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.presentation.R
import com.project.presentation.ui.components.card.CharacterCard
import com.project.presentation.ui.components.input.CharacterSearchField
import com.project.presentation.ui.components.topbar.SwapiTopAppBar
import com.project.presentation.ui.theme.DarkBackground

@Composable
fun CharactersListScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: CharactersListViewModel = hiltViewModel()
) {
    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = { SwapiTopAppBar(title = stringResource(R.string.archives_title)) },
        containerColor = DarkBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CharacterSearchField(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_normal),
                    vertical = dimensionResource(R.dimen.padding_medium)
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_normal)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                val rowCount = (characters.itemCount + 1) / 2

                items(
                    count = rowCount,
                    key = { rowIndex ->
                        val firstItemIndex = rowIndex * 2
                        if (firstItemIndex < characters.itemCount) {
                            characters.peek(firstItemIndex)?.id ?: rowIndex
                        } else {
                            rowIndex
                        }
                    }
                ) { rowIndex ->
                    val firstIndex = rowIndex * 2
                    val secondIndex = rowIndex * 2 + 1

                    val firstCharacter =
                        if (firstIndex < characters.itemCount) characters[firstIndex] else null
                    val secondCharacter =
                        if (secondIndex < characters.itemCount) characters[secondIndex] else null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        if (firstCharacter != null) {
                            CharacterCard(
                                character = firstCharacter,
                                onClick = { onNavigateToDetail(firstCharacter.id) },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        if (secondCharacter != null) {
                            CharacterCard(
                                character = secondCharacter,
                                onClick = { onNavigateToDetail(secondCharacter.id) },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}