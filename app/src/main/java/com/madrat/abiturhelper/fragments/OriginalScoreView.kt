package com.madrat.abiturhelper.fragments

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
            originalScorePresenter.saveUserData(mathsValue.text.toString(), russianValue.text.toString(),
                                             physicsValue.text.toString(), computerScienceValue.text.toString(),
                                             socialScienceValue.text.toString())
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
            mathsValue?.text.isNullOrBlank() ->
                mathsValue?.error = "Баллы за математику не введены"

            mathsValue?.text.toString().toInt() < math_passing ->
                mathsValue?.error = "Балл по математике меньше проходного(%d)".format(math_passing)

            mathsValue?.text.toString().toInt() > score_limit ->
                mathsValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun russianIsValid(rus_passing: Int, score_limit: Int){
        when {
            russianValue?.text.isNullOrBlank() ->
                russianValue?.error = "Баллы за русский язык не введены"

            russianValue?.text.toString().toInt() < rus_passing ->
                russianValue?.error = "Балл по русскому языку меньше проходного(%d)".format(rus_passing)

            russianValue?.text.toString().toInt() > score_limit ->
                russianValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun physicsIsValid(phys_passing: Int, score_limit: Int){
        when {
            physicsValue?.text.isNullOrBlank() ->
                physicsValue?.error = "Баллы за физику не введены"

            physicsValue?.text.toString().toInt() < phys_passing ->
                physicsValue?.error = "Балл по физике меньше проходного(%d)".format(phys_passing)

            physicsValue?.text.toString().toInt() > score_limit ->
                physicsValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun computerScienceIsValid(comp_passing: Int, score_limit: Int){
        when {
            computerScienceValue?.text.isNullOrBlank() ->
                computerScienceValue?.error = "Баллы за информатику не введены"

            computerScienceValue?.text.toString().toInt() < comp_passing ->
                computerScienceValue?.error = "Балл по информатике меньше проходного(%d)".format(comp_passing)

            computerScienceValue?.text.toString().toInt() > score_limit ->
                computerScienceValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun socialScienceIsValid(soc_passing: Int, score_limit: Int){
        when {
            socialScienceValue?.text.isNullOrBlank() ->
                socialScienceValue?.error = "Баллы за обществознание не введены"

            socialScienceValue?.text.toString().toInt() < soc_passing->
                socialScienceValue?.error = "Балл по обществознанию меньше проходного(%d)".format(soc_passing)

            socialScienceValue?.text.toString().toInt() > score_limit ->
                socialScienceValue?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
}
