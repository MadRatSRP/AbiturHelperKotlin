package com.madrat.abiturhelper.ui.activity

import android.os.Bundle

import androidx.fragment.app.Fragment

class ActivityPresenter(private var av: ActivityVP.View) : ActivityVP.Presenter {
    private val TAG = "ActivityPresenter"

    override fun addFragment(fragment: Fragment) {
        av.setFragment(fragment)
    }


}
