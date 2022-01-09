package com.sarftec.newsurelygh.data.local

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalAssets @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getLogo() : Uri = "logo.jpg".toAssetUri("splash")

    private fun String.toAssetUri(folder: String) : Uri {
        return Uri.parse("file:///android_asset/$folder/$this")
    }
}