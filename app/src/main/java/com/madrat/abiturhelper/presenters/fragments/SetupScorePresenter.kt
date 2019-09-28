package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.model.FullName
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class SetupScorePresenter(private var view: SetupScoreMVP.View)
    : SetupScoreMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun saveFullNameAndScore(lastName: String, firstName: String, patronymic: String,
                                      maths: Int, russian: Int, physics: Int?, computerScience: Int?,
                                      socialScience: Int?, additionalScore: Int) {
        val fullName = FullName(lastName, firstName, patronymic)

        val score = Score(
                maths, russian, checkScoreForBeingEmpty(physics),
                checkScoreForBeingEmpty(computerScience),
                checkScoreForBeingEmpty(socialScience), additionalScore
        )

        myApplication.saveFullName(fullName)
        myApplication.saveScore(score)

        view.moveToWorkWithSpecialtiesView()
    }
    override fun checkScoreForBeingEmpty(score: Int?): Int {
        return score ?: 0
    }
}
