package com.madrat.abiturhelper.ui.result

class ResultPresenter(private var rv: ResultVP.View) : ResultVP.Presenter {

    override fun addEgeScore(): String {
        return rv.setEgeScore()
    }
}
