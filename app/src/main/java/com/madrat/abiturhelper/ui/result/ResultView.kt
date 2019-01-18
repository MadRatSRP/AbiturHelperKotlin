package com.madrat.abiturhelper.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.madrat.abiturhelper.R

import androidx.fragment.app.Fragment
import com.netguru.kissme.Kissme
import kotlinx.android.synthetic.main.fragment_result.*

class ResultView : Fragment(), ResultVP.View {

    /*@BindView(R.id.result)
    internal var tv: TextView? = null*/

    //private int maths, russian;
    //private var unbinder: Unbinder? = null
    private var resultPresenter: ResultPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
    }

    override fun setUp() {
        resultPresenter = ResultPresenter(this)
        //tv!!.text = resultPresenter!!.addEgeScore()
        //tv!!.text = setEgeScore()
        val storage = Kissme(name = login)
        val maths = storage.getInt("maths", 0)
        val russian = storage.getInt("russian", 0)
        result.text = (maths + russian).toString()

        //return "Общая сумма баллов = " + (maths + russian).toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        //unbinder = ButterKnife.bind(this, view)
        return view
    }

    /*override fun setEgeScore(): String {
        val storage = Kissme(name = "jopa")
        /*storage.putInt("sum",
                storage.getInt("maths") + storage.getInt("russian"))*/
        /*val sum = storage.putInt("sum",
                storage.getInt("maths", 0) +
                storage.getInt("russian", 1))*/

        //return "Общая сумма баллов = " + sum.toString()

        val maths = storage.getInt("maths", 0)
        val russian = storage.getInt("russian", 0)


        return "Общая сумма баллов = " + (maths + russian).toString()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        //unbinder!!.unbind()
    }

    companion object {

        val instance = ResultView()
    }
}
