package com.app.currency.android.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.currency.DatabaseDriverFactory
import com.app.currency.Greeting
import com.app.currency.android.R
import com.app.currency.android.component.DropDown

import com.app.currency.android.component.Heading
import com.app.currency.dao.AppDao
import org.koin.androidx.compose.getViewModel

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedDb = AppDao(DatabaseDriverFactory(this))

        /*     sharedDb.insertAllinCache("ABC", "1111111")
             sharedDb.insertAllinCache("CBC", "2111111")*/







        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen(Greeting().greeting())
                    //sharedDb.getAllLatestPrices()
                    LaunchedEffect(key1 = Unit) {

                    }


                }
            }
        }
    }
}

@Composable
fun MainScreen(text: String) {
    val qunatity = remember {
        mutableStateOf<String>("")
    }
    val vm = getViewModel<MainVm>()
    Log.v("kkkkkkkk", vm.dataCurrency.value.toString())


    val selectedKey = remember {
        mutableStateOf(vm.dataCurrency.value?.get(0)?.key ?: "")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {


            Image(
                painterResource(id = R.drawable.bg),
                contentDescription = "bg",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column {
                if (vm.isLoading.value) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color.White)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Heading()
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White.copy(alpha = 0.9f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .height(55.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        placeholder = {
                            Text(
                                "Enter Quantity",
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        value = qunatity.value, onValueChange = {
                            if(it.length<=8){
                                qunatity.value  = it.filter { it.isDigit() }

                            }
                        },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )


                    Log.v("kkkkkk", selectedKey.value.toString())

                    DropDown(vm.dataCurrency.value) {
                        selectedKey.value = it

                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
          Text(text = "Updated At :"+vm.mdate.value, color = Color.White, fontSize = 11.sp)

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    )


                }

                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    val l1 = vm.dataCurrencyValue.value

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val selectionKey=selectedKey.value
                        val selectedObj=  l1?.filter { it.key==selectionKey }
                        val selectedCurrencyPerDollar= selectedObj?.getOrNull(0)?.value_?.toDouble()?:0.00

                        val fQuantity=if(qunatity.value.isNotEmpty()){
                            qunatity.value.toInt()
                        }else{
                            0
                        }


                        items(l1?.size ?: 0) { pos ->
                            val valueNum = l1?.get(pos)?.value_ ?: "0.00"
                            val keyNum = l1?.get(pos)?.key ?: ""

                            val final= ((valueNum.toFloat()).div(selectedCurrencyPerDollar))*fQuantity


                            Card(
                                elevation = 4.dp,
                                backgroundColor = Color(0xFFB1A5DF),
                                modifier = Modifier.height(80.dp)
                            ) {
                                Column(modifier = Modifier.padding(top = 16.dp)) {
                                    Text(
                                        text = final.toFloat().toString(),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                    )
                                    Text(
                                        text = keyNum,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )

                                }

                            }

                        }
                    }

                }


            }
        }

    }

}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainScreen("Hello, Android!")
    }
}
