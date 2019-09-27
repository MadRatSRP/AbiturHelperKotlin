package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.model.FullName
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class SetupScorePresenter(private var view: SetupScoreMVP.View)
    : SetupScoreMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun saveFullName(lastName: String, firstName: String, patronymic: String) {
        val fullName = FullName(lastName, firstName, patronymic)
        myApplication.saveFullName(fullName)
    }
    override fun savePointsAsAScoreModel(maths: Int, russian: Int, physics: Int?,
                                         computerScience: Int?, socialScience: Int?,
                                         additionalScore: String) {
        val score = Score(
                maths, russian, checkScoreForBeingEmpty(physics),
                checkScoreForBeingEmpty(computerScience),
                checkScoreForBeingEmpty(socialScience), additionalScore.toInt()
        )

        myApplication.saveScore(score)
    }
    override fun checkScoreForBeingEmpty(score: Int?): Int {
        return score ?: 0
    }
}
