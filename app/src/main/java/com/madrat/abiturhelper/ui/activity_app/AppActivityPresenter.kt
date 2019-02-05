package com.madrat.abiturhelper.ui.activity_app

import androidx.fragment.app.Fragment

class AppActivityPresenter(private var av: AppActivityVP.View) : AppActivityVP.Presenter {
    private val TAG = "AppActivityPresenter"

    override fun addFragment(fragment: Fragment) {
        av.setFragment(fragment)
    }


}
