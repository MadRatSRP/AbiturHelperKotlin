package com.madrat.abiturhelper.ui.base_fragment

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
    }

    interface Repository
}