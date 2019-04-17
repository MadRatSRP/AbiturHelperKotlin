package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.OriginalScoreMVP
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class OriginalScorePresenter(private var ssv: OriginalScoreMVP.View) : OriginalScoreMVP.Presenter {

    override fun saveUserData(maths: String, russian: String, physics: String,
                              computerScience: String, socialScience: String) {
        val myApplication = MyApplication.instance

        myApplication.saveScore(Score(returnInt(maths), returnInt(russian), physics.toIntOrNull(),
                computerScience.toIntOrNull(), socialScience.toIntOrNull()))
    }

    override fun addFieldsListeners() {
        ssv.setFieldsListeners()
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
