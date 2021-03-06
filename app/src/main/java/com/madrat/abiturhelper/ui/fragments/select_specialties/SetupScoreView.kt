package com.madrat.abiturhelper.ui.fragments.select_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.util.Constants
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.FragmentSetupScoreBinding
import com.madrat.abiturhelper.data.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.data.presenters.fragments.SetupScorePresenter
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.toast

class SetupScoreView : Fragment(), SetupScoreMVP.View {
    // ViewBinding
    private var mBinding: FragmentSetupScoreBinding? = null
    private val binding get() = mBinding!!
    // MVP Presenter
    private var presenter: SetupScorePresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.setupScoreTitle)

        // ViewBinding initialization
        mBinding = FragmentSetupScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        // Presenter initialization
        presenter = SetupScorePresenter(this)
        presenter?.initializeApplication(MyApplication.instance)

        val spinnerItems = resources.getStringArray(R.array.additionalScoreSpinnerEntries)
        val adapter = context?.let { ArrayAdapter(it, R.layout.custom_spinner, spinnerItems) }
        adapter?.setDropDownViewResource(R.layout.custom_spinner)

        binding.additionalScoreSpinner.adapter = adapter

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moveToSpecialtiesScreen.setOnClickListener {
            onShowSpecialtiesScreenClicked()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null

        mBinding = null
    }
    
    override fun onShowSpecialtiesScreenClicked()  {
        presenter?.checkIsFullNameAndScoreValid(
                // FullName
                binding.setupScoreLastNameValue.text.toString(),
                binding.setupScoreFirstNameValue.text.toString(),
                binding.setupScorePatronymicValue.text.toString(),
                // Score
                binding.setupScoreMathsValue.text.toString().toIntOrNull(),
                binding.setupScoreRussianValue.text.toString().toIntOrNull(),
                binding.setupScorePhysicsValue.text.toString().toIntOrNull(),
                binding.setupScoreComputerScienceValue.text.toString().toIntOrNull(),
                binding.setupScoreSocialScienceValue.text.toString().toIntOrNull(),
                // AdditionalScore
                binding.additionalScoreSpinner.selectedItem.toString().toInt()
        )
    }
    override fun showErrorMessageByMessageId(messageId: Int) {
        when (messageId) {
            Constants.IS_NULL_LAST_NAME -> {
                binding.setupScoreLastNameValue.error = context?.getString(
                        R.string.score_error_message_last_name_is_null)
                binding.setupScoreLastNameValue.requestFocus()
            }
            Constants.IS_NULL_FIRST_NAME -> {
                binding.setupScoreFirstNameValue.error = context?.getString(
                        R.string.score_error_message_first_name_is_null)
                binding.setupScoreFirstNameValue.requestFocus()
            }
            Constants.IS_NULL_PATRONYMIC -> {
                binding.setupScorePatronymicValue.setText(context?.getString(
                        R.string.score_patronymic_is_null))
            }
            Constants.LESS_THAN_PASSING_MATHS -> {
                binding.setupScoreMathsValue.error = context?.getString(
                        R.string.errorMessageMaths)
            }
            Constants.LESS_THAN_PASSING_RUSSIAN -> {
                binding.setupScoreRussianValue.error = context?.getString(
                        R.string.errorMessageRussian)
            }
            Constants.LESS_THAN_PASSING_PHYSIC -> {
                binding.setupScorePhysicsValue.error = context?.getString(
                        R.string.errorMessagePhysics)
            }
            Constants.LESS_THAN_PASSING_COMPUTER_SCIENCE -> {
                binding.setupScoreComputerScienceValue.error = context?.getString(
                        R.string.errorMessageComputerScience)
            }
            Constants.LESS_THAN_PASSING_SOCIAL_SCIENCE -> {
                binding.setupScoreSocialScienceValue.error = context?.getString(
                        R.string.errorMessageSocialScience)
            }
            Constants.LESS_THAN_THREE -> {
                context?.getText(R.string.score_error_message_less_than_three_score)?.let { context.toast(it) }
            }
        }
    }
    override fun moveToWorkWithSpecialtiesView() {
        moveToSelectedFragment(R.id.action_setupScore_to_workWithSpecialtiesView)
    }
}
