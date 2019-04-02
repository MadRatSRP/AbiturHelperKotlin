package com.madrat.abiturhelper.util

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.madrat.abiturhelper.BuildConfig

fun Fragment.setFragment(fragment: Fragment, fragmentManager: FragmentManager, id: Int) {
    fragmentManager.beginTransaction()
            .replace(id, fragment)
            .commit()
}
fun Fragment.removeFragment(fragment: Fragment, fragmentManager: FragmentManager) {
    fragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
}

fun Any.showLog(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}
fun Any.returnInt(field: String): Int?
        = field.toInt()

fun Any.returnString(value: Int?): String? {
    return value.toString()
}