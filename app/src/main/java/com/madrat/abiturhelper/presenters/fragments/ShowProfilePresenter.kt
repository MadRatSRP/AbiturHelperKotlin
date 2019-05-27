package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class ShowProfilePresenter(private var pv: ProfileMVP.View) : ProfileMVP.Presenter {
    var myApplication = MyApplication.instance

    override fun updateScores(maths: Int, russian: Int, physics: Int, computerScience: Int,
                              socialScience: Int, additionalScore: Int) {
        myApplication.saveScore(Score(maths, russian, physics, computerScience, socialScience))
        myApplication.saveAdditionalScore(additionalScore)
    }

    override fun returnScore() = myApplication.returnScore()
    override fun returnAdditionalScore() = myApplication.returnAdditionalScore()
    override fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }
}