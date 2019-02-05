package com.madrat.abiturhelper.ui.setup_ege

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.netguru.kissme.Kissme
import kotlinx.android.synthetic.main.fragment_setup_ege.*
import com.madrat.abiturhelper.ui.result.ResultView


class SetupEge : Fragment(), SetupEgeVP.View {

    private var setupEgePresenter: SetupEgePresenter? = null

    private val passingMath = 27
    private val passingRussian = 36
    private val passingPhysics = 36
    private val passingComputerScience = 40
    private val passingSocialScience = 42
    private val scoreLimit = 100

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPresenter()

        set_result.setOnClickListener {

            setupEgePresenter?.addFieldsListeners()

            setupEgePresenter?.addFieldsValues()

            setupEgePresenter?.addFragment(ResultView())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setup_ege, container, false)
    }

    override fun setPresenter() { setupEgePresenter = SetupEgePresenter(this) }

    override fun setFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
                       ?.replace(R.id.container, fragment)
                       ?.commit()
    }

    override fun setFieldsListeners() {
        setupEgePresenter?.checkMaths(passingMath, scoreLimit)
        setupEgePresenter?.checkRussian(passingRussian, scoreLimit)
        setupEgePresenter?.checkPhysics(passingPhysics, scoreLimit)
        setupEgePresenter?.checkComputerScience(passingComputerScience, scoreLimit)
        setupEgePresenter?.checkSocialScience(passingSocialScience, scoreLimit)
    }

    override fun setFieldsValues() {

        val storage = Kissme(name = "kappa")

        storage.putInt("maths", maths_value.text.toString().toInt())
        storage.putInt("russian", russian_value.text.toString().toInt())
        storage.putInt("physics", physics_value.text.toString().toInt())
        storage.putInt("computer_science", computer_science_value.text.toString().toInt())
        storage.putInt("social_science", social_science_value.text.toString().toInt())
    }

    override fun mathsIsValid(math_passing: Int, score_limit: Int){
        when {
            maths_value?.text.isNullOrBlank() ->
                maths_value?.error = "Баллы за математику не введены"

            maths_value?.text.toString().toInt() < math_passing ->
                maths_value?.error = "Балл по математике меньше проходного(%d)".format(math_passing)

            maths_value?.text.toString().toInt() > score_limit ->
                maths_value?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun russianIsValid(rus_passing: Int, score_limit: Int){
        when {
            russian_value?.text.isNullOrBlank() ->
                russian_value?.error = "Баллы за русский язык не введены"

            russian_value?.text.toString().toInt() < rus_passing ->
                russian_value?.error = "Балл по русскому языку меньше проходного(%d)".format(rus_passing)

            russian_value?.text.toString().toInt() > score_limit ->
                russian_value?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun physicsIsValid(phys_passing: Int, score_limit: Int){
        when {
            physics_value?.text.isNullOrBlank() ->
                physics_value?.error = "Баллы за физику не введены"

            physics_value?.text.toString().toInt() < phys_passing ->
                physics_value?.error = "Балл по физике меньше проходного(%d)".format(phys_passing)

            physics_value?.text.toString().toInt() > score_limit ->
                physics_value?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun computerScienceIsValid(comp_passing: Int, score_limit: Int){
        when {
            computer_science_value?.text.isNullOrBlank() ->
                computer_science_value?.error = "Баллы за информатику не введены"

            computer_science_value?.text.toString().toInt() < comp_passing ->
                computer_science_value?.error = "Балл по информатике меньше проходного(%d)".format(comp_passing)

            computer_science_value?.text.toString().toInt() > score_limit ->
                computer_science_value?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
    override fun socialScienceIsValid(soc_passing: Int, score_limit: Int){
        when {
            social_science_value?.text.isNullOrBlank() ->
                social_science_value?.error = "Баллы за обществознание не введены"

            social_science_value?.text.toString().toInt() < soc_passing->
                social_science_value?.error = "Балл по обществознанию меньше проходного(%d)".format(soc_passing)

            social_science_value?.text.toString().toInt() > score_limit ->
                social_science_value?.error = "Введённый балл больше %d".format(score_limit)
        }
    }
}
