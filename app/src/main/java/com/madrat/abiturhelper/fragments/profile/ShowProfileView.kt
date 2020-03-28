package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.FragmentShowProfileBinding
import com.madrat.abiturhelper.interfaces.fragments.profile.ShowProfileMVP
import com.madrat.abiturhelper.presenters.fragments.profile.ShowProfilePresenter
import com.madrat.abiturhelper.util.showSnack

class ShowProfileView: Fragment(), ShowProfileMVP.View {
    private var showProfilePresenter: ShowProfilePresenter? = null

    private var mBinding: FragmentShowProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profileTitle)

        // Инициализируем mBinding
        mBinding = FragmentShowProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupScoreFields()
        setupSpecialtiesFields()

        // Булевы значения для ФИО
        var isLastNameEditable: Boolean? = true
        var isFirstNameEditable: Boolean? = true
        var isPatronymicEditable: Boolean? = true
        // Булевы значения для баллов
        var isMathsEditable: Boolean? = true
        var isRussianEditable: Boolean? = true
        var isPhysicsEditable: Boolean? = true
        var isComputerScienceEditable: Boolean? = true
        var isSocialScienceEditable: Boolean? = true
        var isAdditionalScoreEditable: Boolean? = true

        binding.profileLastNameEditValue.setOnClickListener {
            isLastNameEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isLastNameEditable,
                    binding.profileLastNameValue,
                    binding.profileLastNameEditValue
            )
        }
        binding.profileFirstNameEditValue.setOnClickListener {
            isFirstNameEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isFirstNameEditable,
                    binding.profileFirstNameValue,
                    binding.profileFirstNameEditValue
            )
        }
        binding.profilePatronymicEditValue.setOnClickListener {
            isPatronymicEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isPatronymicEditable,
                    binding.profilePatronymicValue,
                    binding.profilePatronymicEditValue
            )
        }
        binding.profileMathsEditValue.setOnClickListener {
            isMathsEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isMathsEditable,
                    binding.profileMathsValue,
                    binding.profileMathsEditValue)
        }
        binding.profileRussianEditValue.setOnClickListener {
            isRussianEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isRussianEditable,
                    binding.profileRussianValue,
                    binding.profileRussianEditValue)
        }
        binding.profilePhysicsEditValue.setOnClickListener {
            isPhysicsEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isPhysicsEditable,
                    binding.profilePhysicsValue,
                    binding.profilePhysicsEditValue)
        }
        binding.profileComputerScienceEditValue.setOnClickListener {
            isComputerScienceEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isComputerScienceEditable,
                    binding.profileComputerScienceValue,
                    binding.profileComputerScienceEditValue)
        }
        binding.profileSocialScienceEditValue.setOnClickListener {
            isSocialScienceEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isSocialScienceEditable,
                    binding.profileSocialScienceValue,
                    binding.profileSocialScienceEditValue)
        }
        binding.profileAdditionalScoreEditValue.setOnClickListener {
            isAdditionalScoreEditable = showProfilePresenter?.checkFieldForBeingEditable(
                    isAdditionalScoreEditable,
                    binding.profileAdditionalScoreValue,
                    binding.profileAdditionalScoreEditValue)
        }

        binding.profileUpdateScores.setOnClickListener {v->
            showProfilePresenter?.updateScores(
                    binding.profileMathsValue.text.toString(),
                    binding.profileRussianValue.text.toString(),
                    binding.profilePhysicsValue.text.toString(),
                    binding.profileComputerScienceValue.text.toString(),
                    binding.profileSocialScienceValue.text.toString(),
                    binding.profileAdditionalScoreValue.text.toString())
            v.showSnack(R.string.profileUpdateScoresMessage)
        }
        binding.profileShowFinalList.setOnClickListener {
            val bundle = showProfilePresenter?.returnBundleWithListID(300)
            toActionId(bundle, R.id.action_profile_to_showFittingSpecialties)
        }
        binding.profileApplySelectSpecialtiesForGraduation.setOnClickListener {
            toActionId(null, R.id.action_profile_to_selectSpecialtiesForCalculating)
        }
        binding.profileAddToListsCalculatePositions.setOnClickListener {
            toActionId(null, R.id.action_profile_to_show_current_list)
        }
        binding.profileChanceShowResults.setOnClickListener {
            toActionId(null, R.id.action_profile_to_showResults)
        }
    }

    override fun onDestroyView() {
        showProfilePresenter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        showProfilePresenter = ShowProfilePresenter(this)
    }
    override fun setupScoreFields() {
        val fullName = showProfilePresenter?.returnFullName()
        val score = showProfilePresenter?.returnCheckedScore()

        fullName?.let {
            binding.profileLastNameValue.setText(fullName.lastName)
            binding.profileFirstNameValue.setText(fullName.firstName)
            binding.profilePatronymicValue.setText(fullName.patronymic)
        }
        score?.let {
            binding.profileMathsValue.setText(score.maths.toString())
            binding.profileRussianValue.setText(score.russian.toString())
            binding.profilePhysicsValue.setText(score.physics.toString())
            binding.profileComputerScienceValue.setText(score.computerScience.toString())
            binding.profileSocialScienceValue.setText(score.socialScience.toString())
            binding.profileAdditionalScoreValue.setText(score.additionalScore.toString())
        }
    }
    override fun setupSpecialtiesFields() {
        val amountOfFinalSpecialties = showProfilePresenter?.returnAmountOfFinalSpecialties()
        binding.profileFinalListOfSpecialtiesAmountValue.setText(amountOfFinalSpecialties.toString())

        val amountOfSpecialtiesWithChance = showProfilePresenter?.returnAmountOfSpecialtiesWithChance()
        binding.profileChanceAmountValue.setText(amountOfSpecialtiesWithChance.toString())

        val amountOfGraduatedSpecialties = showProfilePresenter?.returnAmountOfGraduatedSpecialties()
        binding.profileApplyAmountValue.setText(amountOfGraduatedSpecialties.toString())
    }
    override fun toActionId(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}