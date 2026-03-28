package com.project.presentation.features.character.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.presentation.R
import com.project.presentation.ui.components.info.ErrorScreen
import com.project.presentation.ui.components.info.LoadingScreen
import com.project.presentation.ui.components.topbar.SwapiTopAppBar
import com.project.presentation.ui.theme.AccentCyan
import com.project.presentation.ui.theme.DarkBackground
import com.project.presentation.ui.theme.OutlineColor
import com.project.presentation.ui.theme.SoftViolet
import com.project.presentation.ui.theme.SurfaceColor
import com.project.presentation.ui.theme.TextPrimary
import com.project.presentation.ui.theme.TextSecondary

@Composable
fun CharacterDetailScreen(
    onBackClick: (Unit) -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            val title = (state as? DetailState.Success)?.data?.character?.name
                ?: stringResource(R.string.loading_title)
            SwapiTopAppBar(
                title = title,
                onBackClick = { onBackClick(Unit) }
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_xlarge),
                    vertical = dimensionResource(R.dimen.padding_xlarge)
                )
        ) {
            when (val s = state) {
                is DetailState.Loading -> LoadingScreen()
                is DetailState.Error -> ErrorScreen(message = s.message, onRetry = { })
                is DetailState.Success -> {
                    val fullData = s.data

                    SectionHeader(title = stringResource(R.string.biometric_report))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        DetailBlock(
                            label = stringResource(R.string.homeworld),
                            value = fullData.homeworldName,
                            modifier = Modifier.weight(1f),
                            isAccent = true
                        )
                        DetailBlock(
                            label = stringResource(R.string.birth_year),
                            value = fullData.character.birthYear,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        DetailBlock(
                            label = stringResource(R.string.species_label),
                            value = if (fullData.species.isEmpty()) stringResource(R.string.loading_value) else fullData.species.joinToString(
                                ", "
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        DetailBlock(
                            label = stringResource(R.string.height_label),
                            value = stringResource(
                                R.string.height_format,
                                fullData.character.height
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_huge)))

                    SectionHeader(title = stringResource(R.string.archive_appearances))

                    fullData.films.forEach { filmTitle ->
                        FilmItem(title = filmTitle)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Column(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))) {
        Text(
            text = "[ $title ]",
            color = AccentCyan,
            fontSize = dimensionResource(R.dimen.text_body).value.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_micro))
                    .background(SoftViolet)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_micro)))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.stroke_thin))
                    .background(OutlineColor)
            )
        }
    }
}

@Composable
private fun DetailBlock(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isAccent: Boolean = false
) {
    Surface(
        modifier = modifier,
        shape = CutCornerShape(
            topEnd = dimensionResource(R.dimen.corner_cut_medium),
            bottomStart = dimensionResource(R.dimen.corner_cut_medium)
        ),
        color = SurfaceColor.copy(alpha = 0.5f),
        border = BorderStroke(dimensionResource(R.dimen.stroke_thin), OutlineColor)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_normal)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = TextSecondary,
                fontSize = dimensionResource(R.dimen.text_small).value.sp,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_micro)))
            Text(
                text = value.uppercase(),
                color = if (isAccent) AccentCyan else TextPrimary,
                fontSize = dimensionResource(R.dimen.text_large).value.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun FilmItem(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_micro)),
        shape = CutCornerShape(topEnd = dimensionResource(R.dimen.corner_cut_medium)),
        color = SurfaceColor.copy(alpha = 0.3f),
        border = BorderStroke(dimensionResource(R.dimen.stroke_thin), OutlineColor)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(R.dimen.stroke_medium))
                    .background(AccentCyan)
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_normal)))

            Text(
                text = title.uppercase(),
                color = TextPrimary,
                fontSize = dimensionResource(R.dimen.text_medium).value.sp,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_normal)),
                fontFamily = FontFamily.Monospace
            )
        }
    }
}