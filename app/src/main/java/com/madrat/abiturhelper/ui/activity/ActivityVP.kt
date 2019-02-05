package com.madrat.abiturhelper.ui.activity

import android.os.Bundle

import androidx.fragment.app.Fragment

interface ActivityVP {
    interface View {
        fun setMVP()
        //fun setFragment(fragment: Fragment)
    }

    interface Presenter {
        //fun addFragment(fragment: Fragment)
    }
}
