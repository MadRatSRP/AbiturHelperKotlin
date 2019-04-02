package com.madrat.abiturhelper.presenters.fragments

import android.widget.EditText
import com.madrat.abiturhelper.interfaces.fragments.SetupScoreMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class SetupScorePresenter(private var ssv: SetupScoreMVP.View) : SetupScoreMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun saveUserData(maths: String, russian: String, physics: String,
                              computerScience: String, socialScience: String) {
        myApplication.saveMaths(returnInt(maths))
        myApplication.saveRussian(returnInt(russian))
        myApplication.savePhysics(returnInt(physics))
        myApplication.saveComputerScience(returnInt(computerScience))
        myApplication.saveSocialScience(returnInt(socialScience))
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
