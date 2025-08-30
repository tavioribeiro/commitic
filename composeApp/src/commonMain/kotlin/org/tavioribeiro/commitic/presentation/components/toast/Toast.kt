package org.tavioribeiro.commitic.presentation.components.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_check
import commitic.composeapp.generated.resources.icon_close
import commitic.composeapp.generated.resources.icon_folder
import commitic.composeapp.generated.resources.icon_info
import commitic.composeapp.generated.resources.icon_warning
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType


@Composable
internal fun CustomToastView(toast: ToastUiModel) {

    val backgroundColor = when (toast.type) {
        ToastType.SUCCESS -> Color(0xFF4CAF50)
        ToastType.INFO -> Color(0xFF2196F3)
        ToastType.WARNING -> Color(0xFFFF9800)
        ToastType.ERROR -> Color(0xFFF44336)
    }

    val icon = when (toast.type) {
        ToastType.SUCCESS -> painterResource(Res.drawable.icon_check)
        ToastType.INFO -> painterResource(Res.drawable.icon_info)
        ToastType.WARNING -> painterResource(Res.drawable.icon_warning)
        ToastType.ERROR -> painterResource(Res.drawable.icon_close)
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .widthIn(max = 400.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = "Toast Icon",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = toast.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = toast.message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

