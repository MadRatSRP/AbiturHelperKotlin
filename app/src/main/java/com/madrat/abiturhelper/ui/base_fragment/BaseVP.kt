package com.madrat.abiturhelper.ui.base_fragment

import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface BaseVP {
    interface View {
        fun setFragment(fragment: Fragment, fragmentManager: FragmentManager, id: Int)
        fun unsetFragment(fragment: Fragment, fragmentManager: FragmentManager)
    }

    interface Presenter {
        fun addFragment(fragment: Fragment, id: Int)
        fun removeFragment(fragment: Fragment)

        fun putScoreValues(maths: EditText, russian: EditText, physics: EditText?,
                           computerScience: EditText?, socialScience: EditText?):Bundle
        fun putAdditionalValues(arguments: Bundle?, soc: EditText):Bundle
    }

    interface Repository
}