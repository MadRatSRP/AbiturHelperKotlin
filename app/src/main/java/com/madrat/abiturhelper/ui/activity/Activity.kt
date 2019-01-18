package com.madrat.abiturhelper.ui.activity

import android.os.Bundle

import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.standard.StandardView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Activity : AppCompatActivity(), ActivityVP.View {

    private var activityPresenter: ActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setUp()
    }

    override fun setUp() {
        activityPresenter = ActivityPresenter(this)
        activityPresenter!!.addFragment(StandardView())
    }

    override fun setFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit()
    }
}