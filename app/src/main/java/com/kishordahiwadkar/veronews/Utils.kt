package com.kishordahiwadkar.veronews

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

fun ImageView.loadUrl(imageUrl: String?) {
    Glide.with(context).load(imageUrl ?: "").into(this)
}