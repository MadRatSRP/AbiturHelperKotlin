package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

interface ShowResultMVP {
    interface View {
        fun setupMVP()
        fun checkField(linearLayout: LinearLayout, textViewValue: TextView, key: String)
        fun setupFields()
    }

    interface Presenter {
        fun returnSum():String?
        fun returnString(bundle: Bundle?, key: String?):String?
    }
}
