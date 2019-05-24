package com.madrat.abiturhelper.util

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.BuildConfig
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.model.faculties.Unti

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
    return this.filter { it.specialtyFirst == specialtyName || it.specialtySecond == specialtyName
            || it.specialtyThird == specialtyName} as ArrayList<Student>
}