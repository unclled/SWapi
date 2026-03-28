package com.project.presentation.ui.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.presentation.R
import com.project.presentation.ui.theme.AccentCyan
import com.project.presentation.ui.theme.DarkBackground
import com.project.presentation.ui.theme.SurfaceColor
import com.project.presentation.ui.theme.TextPrimary

@Composable
fun SwapiTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkBackground.copy(alpha = 0.95f))
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.topbar_height))
                .padding(horizontal = dimensionResource(R.dimen.padding_normal))
        ) {
            if (onBackClick != null) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.icon_size_medium))
                        .background(
                            SurfaceColor,
                            shape = CutCornerShape(dimensionResource(R.dimen.corner_cut_small))
                        )
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back_desc),
                        tint = AccentCyan,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small))
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_normal)))
            }

            Text(
                text = title.uppercase(),
                color = TextPrimary,
                fontSize = dimensionResource(R.dimen.text_title).value.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.stroke_medium))) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(AccentCyan.copy(alpha = 0.6f))
            )
            Box(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.padding_huge))
                    .fillMaxHeight()
                    .background(Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.padding_xlarge))
                    .fillMaxHeight()
                    .background(AccentCyan.copy(alpha = 0.6f))
            )
        }
    }
}