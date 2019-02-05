package com.madrat.abiturhelper.ui.activity_app

import android.os.Bundle

import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.setup_ege.SetupEge

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AppActivity : AppCompatActivity(), AppActivityVP.View {

    private var appActivityPresenter: AppActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setUp()
    }

    override fun setUp() {
        appActivityPresenter = AppActivityPresenter(this)
        appActivityPresenter?.addFragment(SetupEge())
    }

    override fun setFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit()
    }
}