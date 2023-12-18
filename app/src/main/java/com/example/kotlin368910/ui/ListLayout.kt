package com.example.kotlin368910.ui

import android.system.ErrnoException
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader

@Composable
fun MyList(letDirectory: File, file: File) {

    val dateArray = ArrayList<String>()
    letDirectory.mkdirs()

    try {
        val inputStream = FileInputStream(file)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            dateArray.add(line ?: "")
        }
    } catch (err: FileNotFoundException) {
        dateArray.add("Список пуст")
        Log.d("main", "no file")
    } catch (err: ErrnoException) {
        Log.d("main", err.message.toString())
    }

    LazyColumn() {
        items(dateArray.size) { i ->
            Text(text = dateArray[i])
        }
    }
}