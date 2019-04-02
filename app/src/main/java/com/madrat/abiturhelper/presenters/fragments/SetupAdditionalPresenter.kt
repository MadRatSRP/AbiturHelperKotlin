package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.SetupAdditionalMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class SetupAdditionalPresenter(private var sav: SetupAdditionalMVP.View)
    : SetupAdditionalMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun saveUserData(essay: String, letter: String, gto: String) {
        myApplication.saveEssay(returnInt(essay))
        myApplication.saveLetter(returnInt(letter))
        myApplication.saveGTO(returnInt(gto))
    }

}