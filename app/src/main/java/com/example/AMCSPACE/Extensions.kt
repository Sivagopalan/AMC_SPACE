package com.example.AMCSPACE

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT)
        .show()
}