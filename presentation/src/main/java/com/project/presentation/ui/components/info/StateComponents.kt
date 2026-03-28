package com.project.presentation.ui.components.info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.presentation.R
import com.project.presentation.ui.theme.AccentCyan
import com.project.presentation.ui.theme.ErrorNeon
import com.project.presentation.ui.theme.SoftViolet
import com.project.presentation.ui.theme.TextSecondary

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_normal)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = AccentCyan,
            strokeWidth = dimensionResource(R.dimen.stroke_medium),
            modifier = Modifier.size(dimensionResource(R.dimen.progress_size))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        Text(
            text = stringResource(R.string.loading_data_msg),
            color = AccentCyan,
            fontFamily = FontFamily.Monospace,
            fontSize = dimensionResource(R.dimen.text_body).value.sp
        )
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_xlarge)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_detected),
            color = ErrorNeon,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(R.dimen.text_large).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        Text(
            text = message,
            color = TextSecondary,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xlarge)))

        OutlinedButton(
            onClick = onRetry,
            shape = CutCornerShape(dimensionResource(R.dimen.corner_cut_small)),
            border = BorderStroke(dimensionResource(R.dimen.stroke_thin), ErrorNeon),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorNeon)
        ) {
            Text(stringResource(R.string.retry_connection), fontFamily = FontFamily.Monospace)
        }
    }
}