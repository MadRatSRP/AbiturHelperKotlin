package com.madrat.abiturhelper.ui.result

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

interface ResultVP {
    interface View {
        fun setupMVP()
        fun checkField(linearLayout: LinearLayout, textViewValue: TextView, key: String)
        fun setupFields()
    }

    interface Presenter {
        fun returnSum():String?
        fun returnString(scoreBundle: Bundle?, key: String?):String?
    }
}
