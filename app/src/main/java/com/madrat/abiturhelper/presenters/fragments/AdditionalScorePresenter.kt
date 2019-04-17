package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.AdditionalScoreMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class AdditionalScorePresenter(private var sav: AdditionalScoreMVP.View)
    : AdditionalScoreMVP.Presenter {

    override fun saveAdditionalScore(additionalScore: Int) {
        val myApplication = MyApplication.instance

        myApplication.saveAdditionalScore(additionalScore)
    }
}