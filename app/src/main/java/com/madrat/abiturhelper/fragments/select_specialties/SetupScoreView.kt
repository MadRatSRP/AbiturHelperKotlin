package com.madrat.abiturhelper.fragments.select_specialties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.presenters.fragments.SetupScorePresenter
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.toast
import kotlinx.android.synthetic.main.fragment_setup_score.*
import kotlinx.android.synthetic.main.fragment_setup_score.view.*

class SetupScoreView : Fragment(), SetupScoreMVP.View {
    companion object {
        // PASSING_SCORE
        const val SCORE_PASSING_MATHS = 27
        const val SCORE_PASSING_RUSSIAN = 36
        const val SCORE_PASSING_PHYSICS = 36
        const val SCORE_PASSING_COMPUTER_SCIENCE = 40
        const val SCORE_PASSING_SOCIAL_SCIENCE = 42
        // SCORE_ID
        const val SCORE_ID_MATHS = 0
        const val SCORE_ID_RUSSIAN = 1
        const val SCORE_ID_PHYSICS = 2
        const val SCORE_ID_COMPUTER_SCIENCE = 3
        const val SCORE_ID_SOCIAL_SCIENCE = 4
    }

    private var presenter: SetupScorePresenter? = null

    init {
        presenter = SetupScorePresenter(this)
    }

    fun onShowSpecialtiesScreenClicked(context: Context)  {
        val checkedFIO = checkIsFullNameValid(
                setupScoreLastNameValue.text.toString(),
                setupScoreFirstNameValue.text.toString(),
                setupScorePatronymicValue.text.toString()
        )
        val checkedScore = checkForScore(context)

        if (checkedFIO && checkedScore)
            presenter?.saveFullNameAndScore(
                    setupScoreLastNameValue.text.toString(),
                    setupScoreFirstNameValue.text.toString(),
                    setupScorePatronymicValue.text.toString(),
                    setupScoreMathsValue.text.toString().toInt(),
                    setupScoreRussianValue.text.toString().toInt(),
                    setupScorePhysicsValue.text.toString().toIntOrNull(),
                    setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                    setupScoreSocialScienceValue.text.toString().toIntOrNull(),
                    additionalScoreSpinner.selectedItem.toString().toInt()
            )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showSpecialtiesScreen.setOnClickListener { view->
            onShowSpecialtiesScreenClicked(view.context)
        }
    }
    override fun moveToWorkWithSpecialtiesView() {
        moveToSelectedFragment(R.id.action_setupScore_to_workWithSpecialtiesView)
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

    fun updateLastNameView() {
        setupScoreLastNameValue.error = "Фамилия не может быть пустой"
        setupScoreLastNameValue.requestFocus()
    }
    fun updateFirstNameView() {
        setupScoreFirstNameValue.error = "Имя не может быть пустым"
        setupScoreFirstNameValue.requestFocus()
    }
    fun updatePatronymicView() {
        setupScorePatronymicValue.setText("—")
    }

    fun checkIsFullNameValid(lastName: String, firstName: String, patronymic: String): Boolean {
        when {
            lastName.isEmpty() -> {
                updateLastNameView()
            }
            firstName.isEmpty() -> {
                updateFirstNameView()
            }
            patronymic.isEmpty() -> {
                updatePatronymicView()
            }
        }
        return firstName.isNotBlank() && lastName.isNotBlank() && patronymic.isNotBlank()
    }

    fun tpdScore(): Boolean {
        val checkedPhysics = scoreError(
                SCORE_ID_PHYSICS, setupScorePhysicsValue.text.toString().toIntOrNull(),
                SCORE_PASSING_PHYSICS
        )
        val checkedComputerScience = scoreError(
                SCORE_ID_COMPUTER_SCIENCE, setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                SCORE_PASSING_COMPUTER_SCIENCE
        )
        val checkedSocialScience = scoreError(
                SCORE_ID_SOCIAL_SCIENCE, setupScoreSocialScienceValue.text.toString().toIntOrNull(),
                SCORE_PASSING_SOCIAL_SCIENCE
        )

        return checkedPhysics && checkedComputerScience && checkedSocialScience
    }

    fun checkTypedScore(): Boolean {
        val physics = checkScoreForBeingEmpty(
                setupScorePhysicsValue.text.toString().toIntOrNull())
        val computerScience = checkScoreForBeingEmpty(
                setupScoreComputerScienceValue.text.toString().toIntOrNull())
        val socialScience = checkScoreForBeingEmpty(
                setupScoreSocialScienceValue.text.toString().toIntOrNull())

        return if (physics == 0 && computerScience == 0 && socialScience == 0)
            lessThanThree()
        else tpdScore()
    }

    fun checkForPassing(): Boolean {
        val checkedMaths = scoreError(
                SCORE_ID_MATHS, setupScoreMathsValue.text.toString().toInt(),
                SCORE_PASSING_MATHS
        )
        val checkedRussian = scoreError(
                SCORE_ID_RUSSIAN, setupScoreRussianValue.text.toString().toInt(),
                SCORE_PASSING_RUSSIAN
        )

        val checkedTypedScore = checkTypedScore(/*checkedPhysics, checkedComputerScience, checkedSocialScience*/)

        return checkedMaths && checkedRussian && checkedTypedScore
    }
    fun checkForScore(context: Context): Boolean{
        val maths = setupScoreMathsValue.text.toString()
        val russian = setupScoreRussianValue.text.toString()
        val physics = setupScorePhysicsValue.text.toString()
        val computerScience = setupScoreComputerScienceValue.text.toString()
        val socialScience = setupScoreSocialScienceValue.text.toString()

        return if (maths != "" && russian != "" && (physics != ""
                        || computerScience != "" || socialScience != ""))
            checkForPassing()
        else lessThanThree()
    }

    fun lessThanThree(): Boolean {
        context.toast("Введено меньше трёх баллов")
        return false
    }

    override fun scoreError(scoreId: Int, score: Int?, passingScore: Int): Boolean {
        return when (checkScoreForBeingEmpty(score)) {
            0 -> true

            in 1 until passingScore -> showErrorMessageByScoreId(scoreId)

            in passingScore until 101 -> true

            else -> true
        }
    }
    override fun showErrorMessageByScoreId(scoreId: Int): Boolean {
        return when (scoreId) {
            SCORE_ID_MATHS -> {
                setupScoreMathsValue.error = context?.getString(
                        R.string.errorMessageMaths)
                false
            }
            SCORE_ID_RUSSIAN -> {
                setupScoreRussianValue.error = context?.getString(
                        R.string.errorMessageRussian)
                false
            }
            SCORE_ID_PHYSICS -> {
                setupScorePhysicsValue.error = context?.getString(
                        R.string.errorMessagePhysics)
                false
            }
            SCORE_ID_COMPUTER_SCIENCE -> {
                setupScoreComputerScienceValue.error = context?.getString(
                        R.string.errorMessageComputerScience)
                false
            }
            SCORE_ID_SOCIAL_SCIENCE -> {
                setupScoreSocialScienceValue.error = context?.getString(
                        R.string.errorMessageSocialScience)
                false
            }
            else -> true
        }
    }
    override fun checkScoreForBeingEmpty(score: Int?): Int {
        return score ?: 0
    }
}
