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
fun Fragment.putScoreValues(maths: EditText,
                            russian: EditText,
                            physics: EditText?,
                            computerScience: EditText?,
                            socialScience: EditText?): Bundle {

    val scoreBundle = Bundle()

    scoreBundle.putInt("maths", maths.text.toString().toInt())
    scoreBundle.putInt("russian", russian.text.toString().toInt())
    physics?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("physics", it) }
    computerScience?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("computerScience", it) }
    socialScience?.text.toString().toIntOrNull()?.let { scoreBundle.putInt("socialScience", it) }

    scoreBundle.putBundle("scoreBundle", scoreBundle)
    return scoreBundle
}
fun Fragment.putAdditionalValues(arguments: Bundle?, soc: EditText): Bundle {
    val additionalBundle = Bundle()
    val scoreBundle = arguments?.getBundle("scoreBundle")

    additionalBundle.putBundle("scoreBundle", scoreBundle)
    additionalBundle.putInt("soc", soc.text.toString().toInt())
    additionalBundle.putBundle("additionalBundle", additionalBundle)
    return additionalBundle
}

fun Any.showLog(message: String){
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}
fun Any.returnInt(field: String): Int?
        = field.toInt()

fun Any.returnString(value: Int?): String? {
    return value.toString()
}