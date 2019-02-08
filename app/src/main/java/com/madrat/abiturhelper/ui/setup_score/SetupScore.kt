package com.madrat.abiturhelper.ui.setup_score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.base_fragment.BaseFragment
import com.madrat.abiturhelper.ui.base_fragment.BasePresenter
import kotlinx.android.synthetic.main.fragment_setup_score.*
import com.madrat.abiturhelper.ui.result.ResultView


class SetupScore : Fragment(), SetupScoreVP.View {

    private lateinit var setupScorePresenter: SetupScorePresenter
    private lateinit var basePresenter: BasePresenter

    private val passingMaths = 27
    private val passingRussian = 36
    private val passingPhysics = 36
    private val passingComputerScience = 40
    private val passingSocialScience = 42
    private val scoreLimit = 100

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setMVP()

        set_result.setOnClickListener {
            setupScorePresenter.addFieldsListeners()

            setupScorePresenter.addFieldsValues()

            basePresenter.addFragment(ResultView.instance,
                                      R.id.activityFragmentContainer)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.textScore)
        return inflater.inflate(R.layout.fragment_setup_score, container, false)
    }

    override fun setMVP() {
        setupScorePresenter = SetupScorePresenter(this)
        basePresenter = BasePresenter(this.context!!, BaseFragment())
    }

    override fun setFieldsListeners() {
        setupScorePresenter.checkMaths(passingMaths, scoreLimit)
        setupScorePresenter.checkRussian(passingRussian, scoreLimit)
        setupScorePresenter.checkPhysics(passingPhysics, scoreLimit)
        setupScorePresenter.checkComputerScience(passingComputerScience, scoreLimit)
        setupScorePresenter.checkSocialScience(passingSocialScience, scoreLimit)
    }

    override fun setFieldsValues() {
        ResultView.instance.arguments = basePresenter.returnBundle(maths, russian, physics,
                                                                   computer_science, social_science)
    }

    override fun mathsIsValid(math_passing: Int, score_limit: Int){
        when {
            maths?.text.isNullOrBlank() ->
                maths?.error = "Баллы за математику не введены"

            maths?.text.toString().toInt() < math_passing ->
                maths?.error = "Балл по математике меньше проходного(%d)".format(math_passing)

            maths?.text.toString().toInt() > score_limit ->
                maths?.error = "Введённый балл больше %d".format(score_limit)
        }
    }

    override fun russianIsValid(rus_passing: Int, score_limit: Int){
        when {
            russian?.text.isNullOrBlank() ->
                russian?.error = "Баллы за русский язык не введены"

            russian?.text.toString().toInt() < rus_passing ->
                russian?.error = "Балл по русскому языку меньше проходного(%d)".format(rus_passing)

            russian?.text.toString().toInt() > score_limit ->
                russian?.error = "Введённый балл больше %d".format(score_limit)
        }
    }

    override fun physicsIsValid(phys_passing: Int, score_limit: Int){
        when {
            physics?.text.isNullOrBlank() ->
                physics?.error = "Баллы за физику не введены"

            physics?.text.toString().toInt() < phys_passing ->
                physics?.error = "Балл по физике меньше проходного(%d)".format(phys_passing)

            physics?.text.toString().toInt() > score_limit ->
                physics?.error = "Введённый балл больше %d".format(score_limit)
        }
    }

    override fun computerScienceIsValid(comp_passing: Int, score_limit: Int){
        when {
            computer_science?.text.isNullOrBlank() ->
                computer_science?.error = "Баллы за информатику не введены"

            computer_science?.text.toString().toInt() < comp_passing ->
                computer_science?.error = "Балл по информатике меньше проходного(%d)".format(comp_passing)

            computer_science?.text.toString().toInt() > score_limit ->
                computer_science?.error = "Введённый балл больше %d".format(score_limit)
        }
    }

    override fun socialScienceIsValid(soc_passing: Int, score_limit: Int){
        when {
            social_science?.text.isNullOrBlank() ->
                social_science?.error = "Баллы за обществознание не введены"

            social_science?.text.toString().toInt() < soc_passing->
                social_science?.error = "Балл по обществознанию меньше проходного(%d)".format(soc_passing)

            social_science?.text.toString().toInt() > score_limit ->
                social_science?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
}
