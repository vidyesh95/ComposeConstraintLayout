package com.example.composeconstraintlayout

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.composeconstraintlayout.ui.theme.ComposeConstraintLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    ComposeConstraintLayoutTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DecoupledConstraintLayout()
        }
    }
}


@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (minWidth < 600.dp) {
            /*decoupledConstraints(margin = 16.dp) // Portrait constraints*/
            decoupledConstraints(margin = 0.dp) // Portrait constraints
        } else {
            /*decoupledConstraints(margin = 32.dp) // Landscape constraints*/
            decoupledConstraints(margin = 0.dp) // Landscape constraints
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(color = Color.Green)
                    .layoutId("greenbox")
            )

            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .layoutId("redbox")
            )
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")
        val guideline = createGuidelineFromTop(0.5f)

        constrain(greenBox) {
            /*top.linkTo(parent.top, margin = margin)*/
            top.linkTo(guideline, margin = margin)
            start.linkTo(parent.start, margin = margin)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox) {
            top.linkTo(parent.top, margin = margin)
            start.linkTo(greenBox.end, margin = margin)
            end.linkTo(parent.end, margin = margin)
            width = Dimension.value(100.dp)
            /*width = Dimension.fillToConstraints*/
            height = Dimension.value(100.dp)
        }
        createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Packed)
    }
}

@Preview(showBackground = true, name = "Light mode")
@Preview(showBackground = true, name = "Dark made", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    MyApp()
}