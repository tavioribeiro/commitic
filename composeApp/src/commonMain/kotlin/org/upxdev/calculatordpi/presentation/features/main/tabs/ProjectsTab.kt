package org.upxdev.calculatordpi.presentation.features.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.upxdev.calculatordpi.theme.AppTheme
import org.upxdev.calculatordpi.screen_utils.WindowType
import org.upxdev.calculatordpi.screen_utils.getWindowSize


@Composable
fun ProjectsTab() {
    val windowSize = getWindowSize()

    val isMedium = windowSize.width == WindowType.Medium

    if (isMedium) {
        Column(
            modifier = Modifier
                .background(AppTheme.colors.color1)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                Modifier
                    .height(391.dp)
                    .fillMaxWidth()
                    .background(AppTheme.colors.color2)
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                Modifier
                    .height(391.dp)
                    .fillMaxWidth()
                    .background(AppTheme.colors.color3)
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        }
    } else {
        Row(
            modifier = Modifier
                .background(AppTheme.colors.color1)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(391.dp)
                    .width(394.dp)
                    .background(AppTheme.colors.color2)
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            Spacer(modifier = Modifier.width(30.dp))

            Column(
                Modifier
                    .height(391.dp)
                    .weight(1f)
                    .background(AppTheme.colors.color9)
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        }
    }
}