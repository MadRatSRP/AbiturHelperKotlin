package com.madrat.abiturhelper.ui.result

class ResultPresenter(private var rv: ResultVP.View) : ResultVP.Presenter {

    lateinit var maths : String

    /*val maths = arguments?.getString("maths")

    maths_value.text = maths

    val russian = arguments?.getString("russian")
    val physics = arguments?.getString("physics")
    val computerScience = arguments?.getString("computerScience")
    val socialScience = arguments?.getString("socialScience")*/

    override fun addEgeScore(): String {
        return rv.setEgeScore()
    }
}
