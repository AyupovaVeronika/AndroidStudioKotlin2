package com.example.kotlin368910

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

sealed class ResultAppNavigator<I : Any?, O : Any?, in ARC : ActivityResultContract<I, O>>(
    private val contract: ARC,
    private val registerForActivityResult:
        (ARC, ActivityResultCallback<O>) -> ActivityResultLauncher<I>,
    private val registerPermissionForActivityResult:
        (ActivityResultContracts.RequestPermission, ActivityResultCallback<Boolean>) -> ActivityResultLauncher<String>,
    private val permission: String
) {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var launcher: ActivityResultLauncher<I>
    var input: I? = null

    class Camera(
        registerForActivityResult:
            (ActivityResultContract<Uri, Boolean>, ActivityResultCallback<Boolean>) -> ActivityResultLauncher<Uri>,
        registerPermissionForActivityResult:
            (ActivityResultContracts.RequestPermission, ActivityResultCallback<Boolean>) -> ActivityResultLauncher<String>
    ) : ResultAppNavigator<Uri, Boolean, ActivityResultContracts.TakePicture>(
        ActivityResultContracts.TakePicture(),
        registerForActivityResult,
        registerPermissionForActivityResult,
        Manifest.permission.CAMERA
    ) {
        override fun createInput(context: Context){
            input = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )
        }
    }



    protected open fun createInput(context: Context) { }

    private fun createLauncher(
        callBack: ActivityResultCallback<O>
    ) {
        launcher = registerForActivityResult(contract, callBack)
    }

    private fun createPermissionLauncher() {
        permissionLauncher =
            registerPermissionForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                when {
                    granted -> {
                        launcher.launch(input)
                    }

                    else -> {
                    }
                }
            }
    }

    fun createLaunchers(
        context: Context,
        callBack: ActivityResultCallback<O>
    ){
        createInput(context)
        createLauncher(callBack)
        createPermissionLauncher()
    }


    fun launch() = permissionLauncher.launch(permission)
}