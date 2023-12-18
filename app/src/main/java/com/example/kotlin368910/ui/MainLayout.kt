package com.example.kotlin368910.ui

import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.kotlin368910.ResultAppNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(camera: ResultAppNavigator.Camera, upload: (String) -> Unit, startActivity: (Intent) -> Unit) {
    val inputvalue = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {


        Card {
            Button(
                onClick = { camera.launch() },
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Камера")
            }

        }

        Box(Modifier.background(Pink40)) {
            TextField(
                value = inputvalue.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp),
                onValueChange = { inputvalue.value = it },
                placeholder = { Text(text = "Url") }
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Button(
                onClick = {
                    upload(inputvalue.value.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .weight(1f)
            ) {
                Text(text = "Загрузить")
            }

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_PICK)
                    val path = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/pract_6/"
                    val uri = Uri.parse(path)
                    intent.setDataAndType(uri, "*/*")
                    startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .weight(1f)
            ) {
                Text(text = "Открыть папку")
            }

        }
    }
}