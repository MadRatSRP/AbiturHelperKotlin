package com.madrat.abiturhelper.ui.setup_score

import androidx.fragment.app.Fragment

class SetupScorePresenter(private var ssv: SetupScoreVP.View) : SetupScoreVP.Presenter {
    private val TAG = "SetupScorePresenter"

    override fun addFragment(fragment: Fragment) { ssv.setFragment(fragment) }

    override fun addFieldsListeners() {
        ssv.setFieldsListeners()
    }

    override fun addFieldsValues() {
        ssv.setFieldsValues()
    }

    override fun checkMaths(math_passing: Int, score_limit: Int) {
        ssv.mathsIsValid(math_passing, score_limit)
    }
    override fun checkRussian(rus_passing: Int, score_limit: Int) {
        ssv.russianIsValid(rus_passing, score_limit)
    }
    override fun checkPhysics(phys_passing: Int, score_limit: Int) {
        ssv.physicsIsValid(phys_passing, score_limit)
    }
    override fun checkComputerScience(comp_passing: Int, score_limit: Int) {
        ssv.computerScienceIsValid(comp_passing, score_limit)
    }
    override fun checkSocialScience(soc_passing: Int, score_limit: Int) {
        ssv.socialScienceIsValid(soc_passing, score_limit)
    }
}
