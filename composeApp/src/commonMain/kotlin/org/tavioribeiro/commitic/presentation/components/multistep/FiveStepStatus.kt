package org.tavioribeiro.commitic.presentation.components.multistep


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_arrow_forward
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusColors
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusModel
import org.tavioribeiro.commitic.theme.AppTheme



@Composable
fun FiveStepStatus(
    threeStepStatusModel: ThreeStepStatusModel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = when(threeStepStatusModel.stepOneColor){
                            ThreeStepStatusColors.GRAY -> Color.Gray
                            ThreeStepStatusColors.ORANGE -> Color.Yellow
                            ThreeStepStatusColors.GREEN -> Color.Green
                            ThreeStepStatusColors.RED -> Color.Red
                        },
                        shape = RoundedCornerShape(50.dp)
                    )
                    .width(18.dp)
                    .height(18.dp),
            )
            Icon(
                modifier = Modifier.width(30.dp).height(30.dp).padding(horizontal = 4.dp),
                painter = painterResource(Res.drawable.icon_arrow_forward),
                contentDescription = null,
                tint = AppTheme.colors.onColor2
            )

            Box(
                modifier = Modifier
                    .background(
                        color = when(threeStepStatusModel.stepTwoColor){
                            ThreeStepStatusColors.GRAY -> Color.Gray
                            ThreeStepStatusColors.ORANGE -> Color.Yellow
                            ThreeStepStatusColors.GREEN -> Color.Green
                            ThreeStepStatusColors.RED -> Color.Red
                        },
                        shape = RoundedCornerShape(50.dp)
                    )
                    .width(18.dp)
                    .height(18.dp),
            )
            Icon(
                modifier = Modifier.width(30.dp).height(30.dp).padding(horizontal = 4.dp),
                painter = painterResource(Res.drawable.icon_arrow_forward),
                contentDescription = null,
                tint = AppTheme.colors.onColor2
            )

            Box(
                modifier = Modifier
                    .background(
                        color = when(threeStepStatusModel.stepThreeColor){
                            ThreeStepStatusColors.GRAY -> Color.Gray
                            ThreeStepStatusColors.ORANGE -> Color.Yellow
                            ThreeStepStatusColors.GREEN -> Color.Green
                            ThreeStepStatusColors.RED -> Color.Red
                        },
                        shape = RoundedCornerShape(50.dp)
                    )
                    .width(18.dp)
                    .height(18.dp),
            )
            Icon(
                modifier = Modifier.width(30.dp).height(30.dp).padding(horizontal = 4.dp),
                painter = painterResource(Res.drawable.icon_arrow_forward),
                contentDescription = null,
                tint = AppTheme.colors.onColor2
            )

            Box(
                modifier = Modifier
                    .background(
                        color = when(threeStepStatusModel.stepFourColor){
                            ThreeStepStatusColors.GRAY -> Color.Gray
                            ThreeStepStatusColors.ORANGE -> Color.Yellow
                            ThreeStepStatusColors.GREEN -> Color.Green
                            ThreeStepStatusColors.RED -> Color.Red
                        },
                        shape = RoundedCornerShape(50.dp)
                    )
                    .width(18.dp)
                    .height(18.dp),
            )

        }

        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = threeStepStatusModel.currentStep,
            color = AppTheme.colors.onColor2,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
    }
}