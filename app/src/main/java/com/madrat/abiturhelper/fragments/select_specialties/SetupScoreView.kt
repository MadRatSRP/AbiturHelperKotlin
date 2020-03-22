package com.madrat.abiturhelper.fragments.select_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.Constants
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.presenters.fragments.SetupScorePresenter
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.toast
import kotlinx.android.synthetic.main.fragment_setup_score.*
import kotlinx.android.synthetic.main.fragment_setup_score.view.*

class SetupScoreView : Fragment(), SetupScoreMVP.View {
    private var presenter: SetupScorePresenter? = null

    //private var mBinding: SetupScoreViewBinding? = null

    init {
        // Инициализируем презентер
        presenter = SetupScorePresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showSpecialtiesScreen.setOnClickListener {
            onShowSpecialtiesScreenClicked()
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
    override fun onDestroyView() {
        presenter?.freeVariables()
        presenter = null

        super.onDestroyView()
    }
    
    override fun onShowSpecialtiesScreenClicked()  {
        presenter?.checkIsFullNameAndScoreValid(
                // FullName
                setupScoreLastNameValue.text.toString(),
                setupScoreFirstNameValue.text.toString(),
                setupScorePatronymicValue.text.toString(),
                // Score
                setupScoreMathsValue.text.toString().toIntOrNull(),
                setupScoreRussianValue.text.toString().toIntOrNull(),
                setupScorePhysicsValue.text.toString().toIntOrNull(),
                setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                setupScoreSocialScienceValue.text.toString().toIntOrNull(),
                // AdditionalScore
                additionalScoreSpinner.selectedItem.toString().toInt()
        )
    }
    override fun showErrorMessageByMessageId(messageId: Int) {
        when (messageId) {
            Constants.IS_NULL_LAST_NAME -> {
                setupScoreLastNameValue.error = context?.getString(
                        R.string.score_error_message_last_name_is_null)
                setupScoreLastNameValue.requestFocus()
            }
            Constants.IS_NULL_FIRST_NAME -> {
                setupScoreFirstNameValue.error = context?.getString(
                        R.string.score_error_message_first_name_is_null)
                setupScoreFirstNameValue.requestFocus()
            }
            Constants.IS_NULL_PATRONYMIC -> {
                setupScorePatronymicValue.setText(context?.getString(
                        R.string.score_patronymic_is_null))
            }
            Constants.LESS_THAN_PASSING_MATHS -> {
                setupScoreMathsValue.error = context?.getString(
                        R.string.errorMessageMaths)
            }
            Constants.LESS_THAN_PASSING_RUSSIAN -> {
                setupScoreRussianValue.error = context?.getString(
                        R.string.errorMessageRussian)
            }
            Constants.LESS_THAN_PASSING_PHYSIC -> {
                setupScorePhysicsValue.error = context?.getString(
                        R.string.errorMessagePhysics)
            }
            Constants.LESS_THAN_PASSING_COMPUTER_SCIENCE -> {
                setupScoreComputerScienceValue.error = context?.getString(
                        R.string.errorMessageComputerScience)
            }
            Constants.LESS_THAN_PASSING_SOCIAL_SCIENCE -> {
                setupScoreSocialScienceValue.error = context?.getString(
                        R.string.errorMessageSocialScience)
            }
            Constants.LESS_THAN_THREE -> {
                context?.getText(R.string.score_error_message_less_than_three_score)
                        ?.let { context.toast(it) }
            }
        }
    }
    override fun moveToWorkWithSpecialtiesView() {
        moveToSelectedFragment(R.id.action_setupScore_to_workWithSpecialtiesView)
    }
}
