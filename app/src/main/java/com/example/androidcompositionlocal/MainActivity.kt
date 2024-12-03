package com.example.androidcompositionlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val height = remember { mutableIntStateOf(50) }

            val weight = remember { mutableIntStateOf(10) }

            val imt = remember { mutableDoubleStateOf(height.intValue.toDouble() / weight.intValue.toDouble()) }

            SetChange(height, weight, imt)
        }
    }
}

@Composable
fun SetChange(
    height: MutableIntState,
    weight: MutableIntState,
    imt: MutableDoubleState) {
    val setIMT = { height: Int, weight: Int ->
        if (weight != 0) {
            imt.doubleValue = weight.toDouble() / (height.toDouble()/100).pow(2)
        } else imt.doubleValue = -1.0
    }
    val setHeight = { value: Int ->
        height.intValue = value
        setIMT(value,weight.intValue)
    }
    val setWeight = { value: Int ->
        weight.intValue = value
        setIMT(height.intValue,value)
    }

    val define = {
        setHeight(50)
        setWeight(10)
    }
    ViewIMT(
        height.intValue,
        weight.intValue,
        imt.doubleValue,
        setHeight,
        setWeight,
        define
    )
}

@Composable
fun ViewIMT(
    height: Int,
    weight: Int,
    imt: Double,
    setHeight: (Int) -> Unit,
    setWeight: (Int) -> Unit,
    define: () -> Unit,
) {
    val textOut =
        if (imt < 16) {
            "Выраженный дефицит массы тела"
        } else if (imt >= 16 && imt < 18.5) {
            "Недостаточная масса тела"
        } else if (imt >= 18.5 && imt < 25) {
            "Нормальная масса тела"
        } else if (imt >= 25 && imt < 30) {
            "Избыточная масса тела"
        } else if (imt >= 30 && imt < 35) {
            "Ожирение 1-ой степени"
        } else if (imt >= 35 && imt < 40) {
            "Ожирение 2-ой степени"
        } else if (imt >= 40) {
            "Ожирение 3-ой степени"
        } else "Недопустимые данные"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = "Калькулятор ИМТ",
            fontSize = 32.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(3.dp)
        )
        Text(
            text = "Рост:\n" +
                    "$height см",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .clickable(
                    onClick = {
                        setHeight(height+5)
                    }
                )
        )
        Text(
            text = "Вес:\n" +
                    "$weight кг",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .clickable(
                    onClick = {
                        setWeight(weight+5)
                    }
                )
        )
        Text(
            text = "Коэффициент ИМТ:\n" +
                    "$imt",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = textOut,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(5.dp)
        )
        Text(
            text = "Сбросить",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(5.dp)
                .clickable(
                    onClick = {
                        define()
                    }
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewIMT() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = "Калькулятор ИМТ",
            fontSize = 32.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(3.dp)
        )
        Text(
            text = "Рост:\n" +
                    "175 см",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = "Вес:\n" +
                    "65 кг",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = "Коэффициент ИМТ:\n" +
                    "21",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = "Нормальная масса тела",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(5.dp)
        )
        Text(
            text = "Сбросить",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}