package com.madrat.abiturhelper.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.madrat.abiturhelper.R

import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.ui.setup_score.SetupScore
import com.netguru.kissme.Kissme
import kotlinx.android.synthetic.main.fragment_result.*

class ResultView : Fragment(), ResultVP.View {

    private var resultPresenter: ResultPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPresenter()
    }

    override fun setPresenter() {
        resultPresenter = ResultPresenter(this)
        result.text = resultPresenter!!.addEgeScore()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        return view
    }

    override fun setEgeScore(): String {

        val storage = Kissme(name = SetupScore.returnLogin())
        val maths = storage.getInt("maths", 0)
        val russian = storage.getInt("russian", 0)
        val physics = storage.getInt("physics", 0)
        val computerScience = storage.getInt("computer_science", 0)
        val socialScience = storage.getInt("social_science", 0)

        return "Общая сумма баллов = " + (maths + russian + physics
                                                + computerScience + socialScience).toString()
    }
}
