package com.madrat.abiturhelper.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.madrat.abiturhelper.R

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_result.*

class ResultView : Fragment(), ResultVP.View {

    companion object { val instance = ResultView() }

    private var resultPresenter: ResultPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setMVP()

        resultValue.text = resultPresenter?.addEgeScore()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textResult)
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun setMVP() {
        resultPresenter = ResultPresenter(this)
    }

    override fun setEgeScore(): String {

        val maths = arguments?.getString("maths")

        mathsValue.text = maths

        val russian = arguments?.getString("russian")
        val physics = arguments?.getString("physics")
        val computerScience = arguments?.getString("computerScience")
        val socialScience = arguments?.getString("socialScience")

        return maths + russian + physics + computerScience + socialScience
    }
}
