package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.AdditionalScoreMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class AdditionalScorePresenter(private var sav: AdditionalScoreMVP.View)
    : AdditionalScoreMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun saveUserData(essay: String, letter: String, gto: String) {
        myApplication.saveEssay(returnInt(essay))
        myApplication.saveLetter(returnInt(letter))
        myApplication.saveGTO(returnInt(gto))
    }

}