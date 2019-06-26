package com.madrat.abiturhelper.fragments.pick_up_specialties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.presenters.fragments.SetupScorePresenter
import com.madrat.abiturhelper.util.toast
import kotlinx.android.synthetic.main.fragment_setup_score.*
import kotlinx.android.synthetic.main.fragment_setup_score.view.*

class SetupScoreView : Fragment(), SetupScoreMVP.View {

    private var setupScorePresenter: SetupScorePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        showSpecialtiesScreen.setOnClickListener { view->
            val checkedFIO = checkForFIO(view.context)
            val checkedScore = checkForScore(view.context)

            if (checkedFIO && checkedScore)
                moveToWorkWithSpecialties(view)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.setupScoreTitle)
        val view = inflater.inflate(R.layout.fragment_setup_score, container, false)

        val spinnerItems = resources.getStringArray(R.array.additionalScoreSpinnerEntries)
        val adapter = ArrayAdapter(context, R.layout.custom_spinner, spinnerItems)
        adapter.setDropDownViewResource(R.layout.custom_spinner)

        view.additionalScoreSpinner.adapter = adapter

        return view
    }

    override fun setupMVP() {
        setupScorePresenter = SetupScorePresenter(this)
    }

    override fun checkForFIO(context: Context): Boolean {
        val firstName = setupScoreFirstNameValue.text.toString()
        val lastName = setupScoreLastNameValue.text.toString()
        val patronymic = setupScorePatronymicValue.text.toString()

        val errorMessage = context.getString(R.string.setupScoreFieldIsEmpty)

        when {
            lastName.isEmpty() -> {
                setupScoreLastNameValue.error = "Фамилия не может быть пустой"
                setupScoreLastNameValue.requestFocus()
            }
            firstName.isEmpty() -> {
                setupScoreFirstNameValue.error = "Имя не может быть пустым"
                setupScoreFirstNameValue.requestFocus()
            }
            patronymic.isEmpty() -> {
                setupScorePatronymicValue.error = "Отчество не может быть пустым"
                setupScorePatronymicValue.requestFocus()
            }
        }
        return firstName.isNotBlank() && lastName.isNotBlank() && patronymic.isNotBlank()
    }

    fun mthsError(): Boolean {
        val setupScoreLessThanPassingMaths = "Балл по математике меньше проходного"
        setupScoreMathsValue.error = setupScoreLessThanPassingMaths
        return false
    }
    fun rssnError(): Boolean {
        val setupScoreLessThanPassingRussian = "Балл по русскому языку меньше проходного"
        setupScoreRussianValue.error = setupScoreLessThanPassingRussian
        return false
    }
    fun phscsError(): Boolean {
        val setupScoreLessThanPassingPhysics = "Балл по физике меньше проходного"
        setupScorePhysicsValue.error = setupScoreLessThanPassingPhysics
        return false
    }
    fun cmptrError(): Boolean {
        val setupScoreLessThanPassingComputerScience = "Балл по информатике меньше проходного"
        setupScoreComputerScienceValue.error = setupScoreLessThanPassingComputerScience
        return false
    }

    fun chkForMaths(): Boolean {
        val maths = setupScoreMathsValue.text.toString()
        val passingMaths = 27

        return if (maths.toInt() <= passingMaths)
            mthsError()
        else true
    }
    fun chkForRussian(): Boolean {
        val russian = setupScoreRussianValue.text.toString()
        val passingRussian = 36

        return if (russian.toInt() <= passingRussian)
            rssnError()
        else true
    }
    fun chkForPhysics(): Boolean {
        val physics = setupScorePhysicsValue.text.toString()
        val passingPhysics = 36

        return if (physics.toInt() <= passingPhysics)
            phscsError()
        else true
    }
    fun chkForComputerScience(): Boolean {
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val passingComputerScience = 40

        return if (computerScience.toInt() <= passingComputerScience)
            cmptrError()
        else true
    }

    fun threeOrMore(): Boolean {
        val maths = chkForMaths()
        val russian = chkForRussian()
        val physics = chkForPhysics()
        val cs = chkForComputerScience()

        val physCompSoc = physics || cs
        return maths && russian && physCompSoc
    }
    fun lessThanThree(): Boolean {
        context.toast("Введено меньше трёх баллов")
        return false
    }
    fun checkForScore(context: Context): Boolean{
        val maths = setupScoreMathsValue.text.toString()
        val russian = setupScoreRussianValue.text.toString()
        val physics = setupScorePhysicsValue.text.toString()
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val socialScience = setupScoreSocialScienceValue.text.toString()

        return if (maths != "" && russian != "" && (physics != ""
                        || computerScience != "" || socialScience != ""))
            threeOrMore()
        else lessThanThree()
    }

    override fun moveToWorkWithSpecialties(view: View) {
        val additionalScore = additionalScoreSpinner.selectedItem.toString()

        setupScorePresenter?.saveFullName(setupScoreLastNameValue.text.toString(),
                setupScoreFirstNameValue.text.toString(),
                setupScorePatronymicValue.text.toString())

        setupScorePresenter?.saveScore(setupScoreMathsValue.text.toString(),
                setupScoreRussianValue.text.toString(),
                setupScorePhysicsValue.text.toString(),
                setupScoreComputerScienceValue.text.toString(),
                setupScoreSocialScienceValue.text.toString(),
                additionalScore)

        Navigation.findNavController(view).navigate(R.id.action_setupScore_to_pickUpSpecialtiesView)
    }

    /*val maths = setupScoreMathsValue.text.toString()
        val russian = setupScoreRussianValue.text.toString()
        val physics = setupScorePhysicsValue.text.toString()
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val socialScience = setupScoreSocialScienceValue.text.toString()

        val notPassingMaths = context.getString(R.string.setupScoreLessThanPassingMaths)
        val notPassingRussian = context.getString(R.string.setupScoreLessThanPassingRussian)
        val notPassingPhysics = context.getString(R.string.setupScoreLessThanPassingPhysics)
        val notPassingComputerScience = context.getString(R.string.setupScoreLessThanPassingComputerScience)
        val notPassingSocialScience = context.getString(R.string.setupScoreLessThanPassingSocialScience)

        val passingMaths = 27
        val passingRussian = 36
        val passingPhysics = 36
        val passingComputerScience = 40
        val passingSocialScience = 42*/

    /*if (physics.isEmpty() && computerScience.isEmpty() && socialScience.isEmpty()) {
        view?.showSnack(R.string.setupScoreLessThanThreeScores)
    }*/

    /*when {
        physics.isEmpty() && computerScience.isEmpty() && socialScience.isEmpty() ->
            view?.showSnack(R.string.setupScoreLessThanThreeScores)

        maths.toInt() <= passingMaths ->
            setupScoreMathsValue.error = notPassingMaths
        russian.toInt() <= passingRussian ->
            setupScoreRussianValue.error = notPassingRussian
        physics.toInt() <= passingPhysics ->
            setupScorePhysicsValue.error = notPassingPhysics
        computerScience.toInt() <= passingComputerScience ->
            setupScoreComputerScienceValue.error = notPassingComputerScience
        socialScience.toInt() <= passingSocialScience ->
            setupScoreSocialScienceValue.error = notPassingSocialScience
    }*/
}
