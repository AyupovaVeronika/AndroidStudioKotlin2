package com.example.kotlin368910

import android.os.Environment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.net.URL
import java.util.Date

class MainViewModel: ViewModel() {

    private val storageDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    @OptIn(DelicateCoroutinesApi::class)
    fun upload(strUrl: String) {
        val networkDispatcher = newSingleThreadContext("Network")
        val diskDispatcher = newSingleThreadContext("Disk")

        var url = URL("https://")


        GlobalScope.launch(diskDispatcher) {
            url = URL(strUrl.trim())
        }

        GlobalScope.launch(networkDispatcher) {
            withContext(Dispatchers.IO) {
                url.openStream()
            }.use { input ->
                FileOutputStream("${storageDir.absolutePath}/pract_6/${Date().time}.jpg").use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
    
}