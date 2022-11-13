package com.app.currency.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.currency.cache.CurrencyName


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDown(value: MutableList<CurrencyName>?, onclick:(String)->Unit) {
    val keys= mutableListOf<String>()
    val options= mutableListOf<String>()
    value?.forEach{
        keys.add(it.key)
        options.add(it.key)
        it.name
    }
   // val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText?:"",
            onValueChange = { },
            label = { Text("Select Currencies") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->

                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                       onclick(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}