package com.madrat.abiturhelper.ui.standard

import android.os.Bundle

import androidx.fragment.app.Fragment

class StandardPresenter(private var sv: StandardVP.View) : StandardVP.Presenter {
    private val TAG = "StandardPresenter"

    override fun addFragment(fragment: Fragment) { sv.setFragment(fragment) }

    override fun checkMaths(math_passing: Int, score_limit: Int) {
        sv.mathsIsValid(math_passing, score_limit)
    }
    override fun checkRussian(rus_passing: Int, score_limit: Int) {
        sv.russianIsValid(rus_passing, score_limit)
    }
    override fun checkPhysics(phys_passing: Int, score_limit: Int) {
        sv.physicsIsValid(phys_passing, score_limit)
    }
    override fun checkComputerScience(comp_passing: Int, score_limit: Int) {
        sv.computerScienceIsValid(comp_passing, score_limit)
    }
    override fun checkSocialScience(soc_passing: Int, score_limit: Int) {
        sv.socialScienceIsValid(soc_passing, score_limit)
    }
}
