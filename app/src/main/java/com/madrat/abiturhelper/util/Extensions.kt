package com.madrat.abiturhelper.util

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.madrat.abiturhelper.BuildConfig
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student

fun Any.showLog(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}
fun Any.returnInt(field: String): Int
        = field.toInt()

fun Any.returnString(value: Int?): String? {
    return value.toString()
}

//ViewGroup
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}
fun RecyclerView.linearManager() {
    this.layoutManager = LinearLayoutManager(context)
}

fun Bundle.stringAndSerializable(specialty: Specialty, list: ArrayList<Student>) {
    this.putString("title", specialty.shortName)
    this.putSerializable("array", list)
}
fun Bundle.stringAndSerializable(title: String, list: ArrayList<Specialty>) {
    this.putString("title", title)
    this.putSerializable("array", list)
}

fun ArrayList<Student>.filterForSpecialty(specialtyName: String): ArrayList<Student> {
    val array = this.filter { it.specialtyFirst == specialtyName || it.specialtySecond == specialtyName
            || it.specialtyThird == specialtyName} as ArrayList<Student>
    return array
}

fun View.showSnack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, messageRes, length)
    snack.show()
}