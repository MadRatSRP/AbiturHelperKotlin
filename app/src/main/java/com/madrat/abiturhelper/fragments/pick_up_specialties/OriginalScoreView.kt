package com.madrat.abiturhelper.fragments.pick_up_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
//import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import kotlinx.android.synthetic.main.fragment_setup_score.*
import com.madrat.abiturhelper.presenters.fragments.OriginalScorePresenter
import com.madrat.abiturhelper.interfaces.fragments.OriginalScoreMVP

class OriginalScoreView : Fragment(), OriginalScoreMVP.View {

    private lateinit var originalScorePresenter: OriginalScorePresenter

    private val passingMaths = 27
    private val passingRussian = 36
    private val passingPhysics = 36
    private val passingComputerScience = 40
    private val passingSocialScience = 42
    private val scoreLimit = 100

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        toAdditionalScore.setOnClickListener { view->
            //originalScorePresenter.addFieldsListeners()
            originalScorePresenter.saveUserData(profileMathsValue.text.toString(), profileRussianValue.text.toString(),
                                             profilePhysicsValue.text.toString(), profileComputerScienceValue.text.toString(),
                                             profileSocialScienceValue.text.toString())
            Navigation.findNavController(view).navigate(R.id.action_setupScore_to_setupAdditional)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.setUpScoreTitle)
        return inflater.inflate(R.layout.fragment_setup_score, container, false)
    }

    override fun setupMVP() {
        originalScorePresenter = OriginalScorePresenter(this)
    }

    override fun setFieldsListeners() {
        originalScorePresenter.checkMaths(passingMaths, scoreLimit)
        originalScorePresenter.checkRussian(passingRussian, scoreLimit)
        originalScorePresenter.checkPhysics(passingPhysics, scoreLimit)
        originalScorePresenter.checkComputerScience(passingComputerScience, scoreLimit)
        originalScorePresenter.checkSocialScience(passingSocialScience, scoreLimit)
    }

    override fun mathsIsValid(math_passing: Int, score_limit: Int){
        when {
            profileMathsValue?.text.isNullOrBlank() ->
                profileMathsValue?.error = "Баллы за математику не введены"

            profileMathsValue?.text.toString().toInt() < math_passing ->
                profileMathsValue?.error = "Балл по математике меньше проходного(%d)".format(math_passing)

            profileMathsValue?.text.toString().toInt() > score_limit ->
                profileMathsValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun russianIsValid(rus_passing: Int, score_limit: Int){
        when {
            profileRussianValue?.text.isNullOrBlank() ->
                profileRussianValue?.error = "Баллы за русский язык не введены"

            profileRussianValue?.text.toString().toInt() < rus_passing ->
                profileRussianValue?.error = "Балл по русскому языку меньше проходного(%d)".format(rus_passing)

            profileRussianValue?.text.toString().toInt() > score_limit ->
                profileRussianValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun physicsIsValid(phys_passing: Int, score_limit: Int){
        when {
            profilePhysicsValue?.text.isNullOrBlank() ->
                profilePhysicsValue?.error = "Баллы за физику не введены"

            profilePhysicsValue?.text.toString().toInt() < phys_passing ->
                profilePhysicsValue?.error = "Балл по физике меньше проходного(%d)".format(phys_passing)

            profilePhysicsValue?.text.toString().toInt() > score_limit ->
                profilePhysicsValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun computerScienceIsValid(comp_passing: Int, score_limit: Int){
        when {
            profileComputerScienceValue?.text.isNullOrBlank() ->
                profileComputerScienceValue?.error = "Баллы за информатику не введены"

            profileComputerScienceValue?.text.toString().toInt() < comp_passing ->
                profileComputerScienceValue?.error = "Балл по информатике меньше проходного(%d)".format(comp_passing)

            profileComputerScienceValue?.text.toString().toInt() > score_limit ->
                profileComputerScienceValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun socialScienceIsValid(soc_passing: Int, score_limit: Int){
        when {
            profileSocialScienceValue?.text.isNullOrBlank() ->
                profileSocialScienceValue?.error = "Баллы за обществознание не введены"

            profileSocialScienceValue?.text.toString().toInt() < soc_passing->
                profileSocialScienceValue?.error = "Балл по обществознанию меньше проходного(%d)".format(soc_passing)

            profileSocialScienceValue?.text.toString().toInt() > score_limit ->
                profileSocialScienceValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
}
