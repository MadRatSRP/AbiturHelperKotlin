package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.model.FullName
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class SetupScorePresenter(private var ssv: SetupScoreMVP.View)
    : SetupScoreMVP.Presenter {
    val myApplication = MyApplication.instance

    override fun saveFullName(lastName: String, firstName: String, patronymic: String) {
        val fullName = FullName(lastName, firstName, patronymic)
        myApplication.saveFullName(fullName)
    }
    override fun saveScore(maths: String, russian: String, physics: String, computerScience: String,
                  socialScience: String, additionalScore: String) {
        val scoreMaths = returnIntFromString(maths)
        val scoreRussian = returnIntFromString(russian)
        val scorePhysics = checkTextForBeingEmpty(physics)
        val scoreComputerScience = checkTextForBeingEmpty(computerScience)
        val scoreSocialScience = checkTextForBeingEmpty(socialScience)
        val scoreAdditionalScore = checkTextForBeingEmpty(additionalScore)

        val score = Score(scoreMaths, scoreRussian, scorePhysics, scoreComputerScience,
                scoreSocialScience, scoreAdditionalScore)
        myApplication.saveScore(score)
    }
    override fun checkTextForBeingEmpty(text: String): Int {
        return if (text.isEmpty()) {
            0
        } else text.toInt()
    }

    override fun returnIntFromString(text: String)
            = text.toInt()
}
