package com.madrat.abiturhelper.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.madrat.abiturhelper.R

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_result.*

class ResultView : Fragment(), ResultVP.View {

    companion object { val instance = ResultView() }

    private var resultPresenter: ResultPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupFields()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textResult)
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun setupMVP() {
        resultPresenter = ResultPresenter(this, arguments!!)
    }

    override fun checkField(linearLayout: LinearLayout, textViewValue: TextView, key: String) {
        textViewValue.text = resultPresenter?.returnString(key)
        if (textViewValue.text.toString().toInt() == 0) {
            linearLayout.visibility = View.GONE
        }
    }

    override fun setupFields() {
        mathsValue.text = resultPresenter?.returnString("maths")
        russianValue.text = resultPresenter?.returnString("russian")

        checkField(physics, physicsValue, "physics")
        checkField(computerScience, computerScienceValue, "computerScience")
        checkField(socialScience, socialScienceValue, "socialScience")

        resultValue.text = resultPresenter?.returnSum()
    }
}
