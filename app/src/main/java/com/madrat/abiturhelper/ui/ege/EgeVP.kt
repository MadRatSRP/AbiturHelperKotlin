package com.madrat.abiturhelper.ui.ege

import android.os.Bundle

import androidx.fragment.app.Fragment

interface EgeVP {
    interface View {
        fun setUp()
        fun setFragment(fragment: Fragment)
    }

    interface Presenter {
        fun addFragment(fragment: Fragment)
    }
}
