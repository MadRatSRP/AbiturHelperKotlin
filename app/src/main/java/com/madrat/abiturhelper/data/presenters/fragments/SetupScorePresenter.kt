package com.madrat.abiturhelper.data.presenters.fragments

import com.madrat.abiturhelper.util.Constants
import com.madrat.abiturhelper.data.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.data.model.FullName
import com.madrat.abiturhelper.data.model.Score
import com.madrat.abiturhelper.util.MyApplication
import io.paperdb.Paper

class SetupScorePresenter(private var view: SetupScoreMVP.View)
    : SetupScoreMVP.Presenter {
    companion object {
        // PASSING_SCORE_VALUE
        const val PASSING_SCORE_MATHS = 27
        const val PASSING_SCORE_RUSSIAN = 36
        const val PASSING_SCORE_PHYSICS = 36
        const val PASSING_SCORE_COMPUTER_SCIENCE = 40
        const val PASSING_SCORE_SOCIAL_SCIENCE = 42
    }
    
    private var myApplication: MyApplication? = null
    
    // Initialization
    fun initializeApplication(myApplication: MyApplication) {
        this.myApplication = myApplication
    }

    override fun checkIsFullNameAndScoreValid(lastName: String, firstName: String, patronymic: String,
                                              maths: Int?, russian: Int?, physics: Int?,
                                              computerScience: Int?, socialScience: Int?,
                                              additionalScore: Int) {
        val isFullNameValid = checkIsFullNameValid(lastName, firstName, patronymic)
        val isScoreValid = checkIsScoreValid(maths, russian, physics, computerScience, socialScience)

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
    
        myApplication?.writeToPaper(Constants.FULL_NAME, fullName)
    
        myApplication?.saveScore(score)
    
        view.moveToWorkWithSpecialtiesView()
    }
    override fun checkIsFullNameValid(lastName: String, firstName: String, patronymic: String)
            : Boolean {
        when {
            lastName.isEmpty() -> {
                view.showErrorMessageByMessageId(Constants.IS_NULL_LAST_NAME)
            }
            firstName.isEmpty() -> {
                view.showErrorMessageByMessageId(Constants.IS_NULL_FIRST_NAME)
            }
            patronymic.isEmpty() -> {
                view.showErrorMessageByMessageId(Constants.IS_NULL_PATRONYMIC)
            }
        }

        return firstName.isNotBlank() && lastName.isNotBlank() && patronymic.isNotBlank()
    }
    override fun checkIsScoreValid(maths: Int?, russian: Int?, physics: Int?,
                                   computerScience: Int?, socialScience: Int?): Boolean {
        val fieldsAreNotNullable = checkForNullability(
                maths, russian, physics, computerScience, socialScience)

        val mathsAndRussianArePassing = checkMathsAndRussianForPassing(
                maths, russian)

        val typedScoreIsPassing = checkTypedScoreForPassing(
                physics, computerScience, socialScience
        )

        return fieldsAreNotNullable && mathsAndRussianArePassing && typedScoreIsPassing
    }
    override fun checkScoreForBeingEmpty(score: Int?): Int {
        return score ?: 0
    }
    override fun checkTypedScoreForPassing(physics: Int?, computerScience: Int?, socialScience: Int?): Boolean {
        /*val checkedPhysics = checkScoreForBeingEmpty(
                setupScorePhysicsValue.text.toString().toIntOrNull())
        val checkedComputerScience = checkScoreForBeingEmpty(
                setupScoreComputerScienceValue.text.toString().toIntOrNull())
        val checkedSocialScience = checkScoreForBeingEmpty(
                setupScoreSocialScienceValue.text.toString().toIntOrNull())

        return if (physics == 0 && computerScience == 0 && socialScience == 0)
            showLessThanThreeMessage()
        else tpdScore()*/

        val checkedPhysics = scoreError(
                Constants.LESS_THAN_PASSING_PHYSIC, physics,
                PASSING_SCORE_PHYSICS
        )
        val checkedComputerScience = scoreError(
                Constants.LESS_THAN_PASSING_COMPUTER_SCIENCE, computerScience,
                PASSING_SCORE_COMPUTER_SCIENCE
        )
        val checkedSocialScience = scoreError(
                Constants.LESS_THAN_PASSING_SOCIAL_SCIENCE, socialScience,
                PASSING_SCORE_SOCIAL_SCIENCE
        )

        return checkedPhysics && checkedComputerScience && checkedSocialScience
    }
    /*fun tpdScore(): Boolean {
        val checkedPhysics = scoreError(
                LESS_THAN_PASSING_PHYSIC, setupScorePhysicsValue.text.toString().toIntOrNull(),
                PASSING_SCORE_PHYSICS
        )
        val checkedComputerScience = scoreError(
                LESS_THAN_PASSING_COMPUTER_SCIENCE, setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                PASSING_SCORE_COMPUTER_SCIENCE
        )
        val checkedSocialScience = scoreError(
                LESS_THAN_PASSING_SOCIAL_SCIENCE, setupScoreSocialScienceValue.text.toString().toIntOrNull(),
                PASSING_SCORE_SOCIAL_SCIENCE
        )

        return checkedPhysics && checkedComputerScience && checkedSocialScience
    }*/
    override fun checkForNullability(maths: Int?, russian: Int?, physics: Int?, computerScience: Int?,
                                     socialScience: Int?): Boolean{
        return if (maths != null && russian != null && (physics != null || computerScience != null
                        || socialScience != null))
            true
        else {
            view.showErrorMessageByMessageId(Constants.LESS_THAN_THREE)
            false
        }
    }
    override fun checkMathsAndRussianForPassing(maths: Int?, russian: Int?): Boolean {
        val checkedMaths = scoreError(
                Constants.LESS_THAN_PASSING_MATHS, maths,
                PASSING_SCORE_MATHS
        )
        val checkedRussian = scoreError(
                Constants.LESS_THAN_PASSING_RUSSIAN, russian,
                PASSING_SCORE_RUSSIAN
        )

        return checkedMaths && checkedRussian
    }
    override fun scoreError(scoreId: Int, score: Int?, passingScore: Int): Boolean {
        return when (checkScoreForBeingEmpty(score)) {
            0 -> true

            in 1 until passingScore -> {
                view.showErrorMessageByMessageId(scoreId)

                false
            }

            in passingScore until 101 -> true

            else -> true
        }
    }
}
