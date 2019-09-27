package com.madrat.abiturhelper.fragments.select_specialties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
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

    private var presenter: SetupScorePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        showSpecialtiesScreen.setOnClickListener { view->
            /*val checkedFIO = checkForFIO(view.context)
            val checkedScore = checkForScore(view.context)


            if (checkedFIO && checkedScore)
                moveToWorkWithSpecialties(view)*/
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
        val adapter = context?.let { ArrayAdapter(it, R.layout.custom_spinner, spinnerItems) }
        adapter?.setDropDownViewResource(R.layout.custom_spinner)

        view.additionalScoreSpinner.adapter = adapter

        return view
    }

    override fun setupMVP() {
        presenter = SetupScorePresenter(this)
    }

    override fun checkForFIO(context: Context): Boolean {
        val firstName = setupScoreFirstNameValue.text.toString()
        val lastName = setupScoreLastNameValue.text.toString()
        val patronymic = setupScorePatronymicValue.text.toString()

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
                setupScorePatronymicValue.setText("—")
            }
        }
        return firstName.isNotBlank() && lastName.isNotBlank() && patronymic.isNotBlank()
    }
    fun showPassingError(scoreField: EditText, passingValue: String)
            : Boolean {
        scoreField.error = passingValue
        return false
    }
    fun mthsError(): Boolean {
        val passingMaths = 27
        val maths = checkTextForBeingEmpty(setupScoreMathsValue.text.toString())//setupScoreMathsValue.text.toString().toInt()
        val mathsError = "Балл по математике меньше проходного (27)"

        return when (maths) {
            0 -> false
            in 1 until passingMaths -> showPassingError(setupScoreMathsValue, mathsError)
            in passingMaths until 101 -> true
            else -> false
        }
    }
    fun rssnError(): Boolean {
        val passingRussian = 36
        val russian = checkTextForBeingEmpty(setupScoreRussianValue.text.toString())//setupScoreRussianValue.text.toString().toInt()
        val russianError = "Балл по русскому языку меньше проходного (36)"

        return when (russian) {
            0 -> false
            in 1 until passingRussian -> showPassingError(setupScoreRussianValue, russianError)
            in passingRussian until 101 -> true
            else -> false
        }
    }
    fun phscsError(): Boolean {
        val passingPhysics = 36
        val physics = checkTextForBeingEmpty(setupScorePhysicsValue.text.toString())//setupScorePhysicsValue.text.toString().toInt()
        val physicsError = "Балл по физике меньше проходного (36)"

        return when (physics) {
            0 -> true
            in 1 until passingPhysics -> showPassingError(setupScorePhysicsValue, physicsError)
            in passingPhysics until 101 -> true
            else -> false
        }
    }
    fun cmptrError(): Boolean {
        val passingComputerScience = 40
        val computerScience = checkTextForBeingEmpty(setupScoreComputerScienceValue.text.toString())//.toInt()
        val computerScienceError = "Балл по информатике меньше проходного (40)"

        return when (computerScience) {
            0 -> true
            in 1 until passingComputerScience -> showPassingError(setupScoreComputerScienceValue, computerScienceError)
            in passingComputerScience until 101 -> true
            else -> false
        }
    }
    fun sclError(): Boolean {
        val passingSocialScience = 42
        val socialScience = checkTextForBeingEmpty(setupScoreSocialScienceValue.text.toString())
        //setupScoreSocialScienceValue.text.toString().toInt()
        val socialScienceError = "Балл по обществознанию меньше проходного (42)"

        return when (socialScience) {
            0 -> true
            in 1 until passingSocialScience -> showPassingError(setupScoreSocialScienceValue, socialScienceError)
            in passingSocialScience until 101 -> true
            else -> false
        }
    }

    fun tpdScore(): Boolean {
        val checkedPhysics = phscsError()
        val checkedComputerScience = cmptrError()
        val checkedSocialScience = sclError()

        return checkedPhysics && checkedComputerScience && checkedSocialScience
    }

    fun checkTypedScore(): Boolean {
        val physics = checkTextForBeingEmpty(setupScorePhysicsValue.text.toString())
        val computerScience = checkTextForBeingEmpty(setupScoreComputerScienceValue.text.toString())
        val socialScience = checkTextForBeingEmpty(setupScoreSocialScienceValue.text.toString())

        return if (physics == 0 && computerScience == 0 && socialScience == 0)
            lessThanThree()
        else tpdScore()
    }

    fun checkForPassing(): Boolean {
        val checkedMaths = mthsError()
        val checkedRussian = rssnError()

        val checkedTypedScore = checkTypedScore(/*checkedPhysics, checkedComputerScience, checkedSocialScience*/)

        return checkedMaths && checkedRussian && checkedTypedScore
    }
    fun checkForNullFields(): Boolean {
        val maths = setupScoreMathsValue.text.toString()
        val russian = setupScoreRussianValue.text.toString()
        val physics = setupScorePhysicsValue.text.toString()
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val socialScience = setupScoreSocialScienceValue.text.toString()

        when {
            maths.isEmpty() ->
                setupScoreMathsValue.setText("0")
            russian.isEmpty() ->
                setupScoreRussianValue.setText("0")
            physics.isEmpty() ->
                setupScorePhysicsValue.setText("0")
            computerScience.isEmpty() ->
                setupScoreComputerScienceValue.setText("0")
            socialScience.isEmpty() ->
                setupScoreSocialScienceValue.setText("0")
            /*maths < setupScoreLessThanPassingMaths ->
                setupScoreMathsValue.setText("eqweq")*/
        }
        /*return maths.isNotBlank() && russian.isNotBlank() && physics.isNotBlank()
                && computerScience.isNotBlank() && socialScience.isNotBlank()*/
        return checkForPassing()
    }
    fun checkForScore(context: Context): Boolean{
        val maths = setupScoreMathsValue.text.toString()
        val russian = setupScoreRussianValue.text.toString()
        val physics = setupScorePhysicsValue.text.toString()
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val socialScience = setupScoreSocialScienceValue.text.toString()

        return if (maths != "" && russian != "" && (physics != ""
                        || computerScience != "" || socialScience != ""))
            checkForNullFields()
        else lessThanThree()
    }

    /*fun chkForMaths(): Boolean {
        val maths = setupScoreMathsValue.text.toString()
        val passingMaths = 27

        return if (maths.toInt() <= passingMaths)
            mthsError(passingMaths)
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
        val checkedComputerScience = checkTextForBeingEmpty(computerScience)
        val passingComputerScience = 40

        return if (checkedComputerScience <= passingComputerScience)
            cmptrError()
        else true
    }*/

    fun lessThanThree(): Boolean {
        context.toast("Введено меньше трёх баллов")
        return false
    }

    override fun moveToWorkWithSpecialties(view: View) {
        val additionalScore = additionalScoreSpinner.selectedItem.toString()

        presenter?.saveFullName(setupScoreLastNameValue.text.toString(),
                setupScoreFirstNameValue.text.toString(),
                setupScorePatronymicValue.text.toString())

        presenter?.savePointsAsAScoreModel(setupScoreMathsValue.text.toString().toInt(),
                setupScoreRussianValue.text.toString().toInt(),
                setupScorePhysicsValue.text.toString().toIntOrNull(),
                setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                setupScoreSocialScienceValue.text.toString().toIntOrNull(), additionalScore)

        Navigation.findNavController(view).navigate(R.id.action_setupScore_to_pickUpSpecialtiesView)
    }

    fun checkTextForBeingEmpty(text: String): Int {
        return if (text.isEmpty()) {
            0
        } else text.toInt()
    }

}
