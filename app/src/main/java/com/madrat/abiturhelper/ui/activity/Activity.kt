package com.madrat.abiturhelper.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.base_fragment.BaseFragment
import com.madrat.abiturhelper.ui.base_fragment.BasePresenter
import com.madrat.abiturhelper.ui.setup_score.SetupScore

class Activity : AppCompatActivity(), ActivityVP.View {

    private var activityPresenter: ActivityPresenter? = null

    private var basePresenter: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setMVP()

        basePresenter?.addFragment(SetupScore(), R.id.activityFragmentContainer)
    }

    override fun setMVP() {
        activityPresenter = ActivityPresenter(this)
        basePresenter = BasePresenter(this, BaseFragment())
    }
}