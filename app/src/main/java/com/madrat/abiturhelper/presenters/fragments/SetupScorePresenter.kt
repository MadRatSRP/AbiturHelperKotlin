package com.madrat.abiturhelper.presenters.fragments

import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.model.FullName
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class SetupScorePresenter(private var view: SetupScoreMVP.View)
    : SetupScoreMVP.Presenter {
    companion object {
        const val VIEW_LAST_NAME = 200

        const val VIEW_FIRST_NAME = 300

        const val VIEW_PATRONYMIC = 400
    }

    val myApplication = MyApplication.instance

    override fun checkIsFullNameAndScoreValid(lastName: String, firstName: String, patronymic: String,
                                              maths: Int?, russian: Int?, physics: Int?,
                                              computerScience: Int?, socialScience: Int?,
                                              additionalScore: Int) {
        val isFullNameValid = checkIsFullNameValid(lastName, firstName, patronymic)
        val isScoreValid = checkForScore(maths, russian, physics, computerScience, socialScience)

        if (isFullNameValid && isScoreValid)
            if (maths != null && russian != null) {
                saveFullNameAndScore(lastName, firstName, patronymic, maths, russian, physics,
                        computerScience, socialScience, additionalScore)
            }
    }

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

    override fun checkIsFullNameValid(lastName: String, firstName: String, patronymic: String)
            : Boolean {
        when {
            lastName.isEmpty() -> {
                view.showErrorOnView(VIEW_LAST_NAME)
            }
            firstName.isEmpty() -> {
                view.showErrorOnView(VIEW_FIRST_NAME)
            }
            patronymic.isEmpty() -> {
                view.showErrorOnView(VIEW_PATRONYMIC)
            }
        }
        return firstName.isNotBlank() && lastName.isNotBlank() && patronymic.isNotBlank()
    }
    override fun checkForScore(maths: Int?, russian: Int?, physics: Int?, computerScience: Int?,
                               socialScience: Int?): Boolean{
        return if (maths != null && russian != null && (physics != null || computerScience != null
                        || socialScience != null))
            view.checkForPassing()
        else view.lessThanThree()
    }
}
