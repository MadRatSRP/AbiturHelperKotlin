package com.madrat.abiturhelper.ui.activity_app

import androidx.fragment.app.Fragment

interface AppActivityVP {
    interface View {
        fun setUp()
        fun setFragment(fragment: Fragment)
    }

    interface Presenter {
        fun addFragment(fragment: Fragment)
    }
}
