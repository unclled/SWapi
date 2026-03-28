package com.project.presentation.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.domain.model.Character
import com.project.presentation.R
import com.project.presentation.ui.theme.AccentCyan
import com.project.presentation.ui.theme.SurfaceColor
import com.project.presentation.ui.theme.TextPrimary
import com.project.presentation.ui.theme.TextSecondary

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = CutCornerShape(
            topStart = dimensionResource(R.dimen.corner_cut_medium),
            bottomEnd = dimensionResource(R.dimen.corner_cut_medium)
        ),
        color = SurfaceColor.copy(alpha = 0.8f),
        border = BorderStroke(dimensionResource(R.dimen.stroke_thin), AccentCyan.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(AccentCyan.copy(alpha = 0.1f), Color.Transparent)
                    )
                )
                .padding(dimensionResource(R.dimen.padding_normal))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = character.name.uppercase(),
                    fontSize = dimensionResource(R.dimen.text_medium).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontFamily = FontFamily.Monospace
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = character.gender.uppercase(),
                        fontSize = dimensionResource(R.dimen.text_small).value.sp,
                        color = AccentCyan,
                        fontFamily = FontFamily.Monospace
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xlarge)),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        StatInfo(stringResource(R.string.height_label), character.height)
                        StatInfo(stringResource(R.string.mass_label), character.mass)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatInfo(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = dimensionResource(R.dimen.text_micro).value.sp,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = if (value == stringResource(R.string.unknown)) stringResource(R.string.unknown_abbr) else value,
            color = TextPrimary,
            fontSize = dimensionResource(R.dimen.text_large).value.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}