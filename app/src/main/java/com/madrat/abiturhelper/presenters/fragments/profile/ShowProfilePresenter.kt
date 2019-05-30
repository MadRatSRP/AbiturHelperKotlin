package com.madrat.abiturhelper.presenters.fragments.profile

import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.profile.ShowProfileMVP
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class ShowProfilePresenter(private var pv: ShowProfileMVP.View) : ShowProfileMVP.Presenter {
    var myApplication = MyApplication.instance

    override fun updateScores(maths: String, russian: String, physics: String, computerScience: String,
                              socialScience: String, additionalScore: String) {
        val checkedMaths = checkTextForBeingEmpty(maths)
        val checkedRussian = checkTextForBeingEmpty(russian)
        val checkedPhysics = checkTextForBeingEmpty(physics)
        val checkedComputerScience = checkTextForBeingEmpty(computerScience)
        val checkedSocialScience = checkTextForBeingEmpty(socialScience)
        val checkedAdditionalScore = checkTextForBeingEmpty(additionalScore)

        val score = Score(checkedMaths, checkedRussian, checkedPhysics,
                checkedComputerScience, checkedSocialScience)

        myApplication.saveScore(score)
        myApplication.saveAdditionalScore(checkedAdditionalScore)
    }
    override fun checkTextForBeingEmpty(text: String?): Int {
        return if (text == null || text == "") {
            0
        } else text.toInt()
    }
    override fun checkIntForBeingEmpty(value: Int?): Int
            = value ?: 0

    override fun returnAmountOfFinalSpecialties(): Int? {
        val specialties = myApplication.returnCompleteListOfSpecilaties()
        return specialties?.sumBy { it.size }
    }

    override fun returnScore() = myApplication.returnScore()
    override fun returnCheckedScore(): Score {
        val score = returnScore()

        val checkedMaths = checkIntForBeingEmpty(score?.maths)
        val checkedRussian = checkIntForBeingEmpty(score?.russian)
        val checkedPhysics = checkIntForBeingEmpty(score?.physics)
        val checkedComputerScience = checkIntForBeingEmpty(score?.computerScience)
        val checkedSocialScience = checkIntForBeingEmpty(score?.socialScience)
        val checkedAdditionalScore = checkIntForBeingEmpty(score?.additionalScore)

        return Score(checkedMaths, checkedRussian, checkedPhysics,
                checkedComputerScience, checkedSocialScience, checkedAdditionalScore)
    }
    override fun returnAdditionalScore() = myApplication.returnAdditionalScore()
    override fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }

    override fun returnSelectedSpecialties()
            = myApplication.returnSelectedSpecialties()
}