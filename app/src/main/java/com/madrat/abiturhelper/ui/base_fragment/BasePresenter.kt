package com.madrat.abiturhelper.ui.base_fragment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BasePresenter(context: Context,
                    private var bvi: BaseFragment) : BaseVP.Presenter {

    var fragmentManager = (context as AppCompatActivity).supportFragmentManager!!

    override fun addFragment(fragment: Fragment, id: Int) {
        bvi.setFragment(fragment, fragmentManager, id)
    }

    override fun removeFragment(fragment: Fragment) {
        bvi.unsetFragment(fragment, fragmentManager)
    }
}