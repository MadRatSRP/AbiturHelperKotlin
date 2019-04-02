package com.madrat.abiturhelper.presenters.fragments

import android.widget.EditText
import com.madrat.abiturhelper.interfaces.fragments.SetupScoreMVP
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.returnInt

class SetupScorePresenter(private var ssv: SetupScoreMVP.View) : SetupScoreMVP.Presenter {

    private val myApplication = MyApplication.instance

    fun addMaths(mathsValue: EditText) {
        myApplication.saveMaths(returnInt(mathsValue))
    }
    fun addRussian(russianValue: EditText) {
        myApplication.saveRussian(returnInt(russianValue))
    }
    fun addPhysics(physicsValue: EditText) {
        myApplication.savePhysics(returnInt(physicsValue))
    }
    fun addComputerScience(computerScienceValue: EditText) {
        myApplication.saveComputerScience(returnInt(computerScienceValue))
    }
    fun addSocialScience(socialScienceValue: EditText) {
        myApplication.saveSocialScience(returnInt(socialScienceValue))
    }

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
