package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.presenters.fragments.ShowProfilePresenter
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.showSnack
import kotlinx.android.synthetic.main.fragment_profile.*

class ShowProfileView: Fragment(), ProfileMVP.View {
    var showProfilePresenter: ShowProfilePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupScoreFields()

        var isMathsEditable = true
        var isRussianEditable = true
        var isPhysicsEditable = true
        var isComputerScienceEditable = true
        var isSocialScienceEditable = true
        var isAdditionalScoreEditable = true

        profileMathsEditValue.setOnClickListener {
            isMathsEditable = if (isMathsEditable) {
                setFieldEditable(profileMathsValue, profileMathsEditValue)
                false
            } else {
                setFieldNonEditable(profileMathsValue, profileMathsEditValue)
                true
            }
        }
        profileRussianEditValue.setOnClickListener {
            isRussianEditable = if (isRussianEditable) {
                setFieldEditable(profileRussianValue, profileRussianEditValue)
                false
            } else {
                setFieldNonEditable(profileRussianValue, profileRussianEditValue)
                true
            }
        }
        profilePhysicsEditValue.setOnClickListener {
            isPhysicsEditable = if (isPhysicsEditable) {
                setFieldEditable(profilePhysicsValue, profilePhysicsEditValue)
                false
            } else {
                setFieldNonEditable(profilePhysicsValue, profilePhysicsEditValue)
                true
            }
        }
        profileComputerScienceEditValue.setOnClickListener {
            isComputerScienceEditable = if (isComputerScienceEditable) {
                setFieldEditable(profileComputerScienceValue, profileComputerScienceEditValue)
                false
            } else {
                setFieldNonEditable(profileComputerScienceValue, profileComputerScienceEditValue)
                true
            }
        }
        profileSocialScienceEditValue.setOnClickListener {
            isSocialScienceEditable = if (isSocialScienceEditable) {
                setFieldEditable(profileSocialScienceValue, profileSocialScienceEditValue)
                false
            } else {
                setFieldNonEditable(profileSocialScienceValue, profileSocialScienceEditValue)
                true
            }
        }
        profileAdditionalScoreEditValue.setOnClickListener {
            isAdditionalScoreEditable = if (isAdditionalScoreEditable) {
                setFieldEditable(profileAdditionalScoreValue, profileAdditionalScoreEditValue)
                false
            } else {
                setFieldNonEditable(profileAdditionalScoreValue, profileAdditionalScoreEditValue)
                true
            }
        }

        profileUpdateScores.setOnClickListener {v->
            showProfilePresenter?.updateScores(profileMathsValue.text.toString(),
                    profileRussianValue.text.toString(),
                    profilePhysicsValue.text.toString(),
                    profileComputerScienceValue.text.toString(),
                    profileSocialScienceValue.text.toString(),
                    profileAdditionalScoreValue.text.toString())
            v.showSnack(R.string.profileUpdateScoresMessage)
        }
        profileShowFinalList.setOnClickListener {
            val bundle = showProfilePresenter?.returnBundleWithListID(300)
            toSpecialties(bundle, R.id.action_profile_to_showFittingSpecialties)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profileTitle)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onDestroyView() {
        showProfilePresenter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        showProfilePresenter = ShowProfilePresenter(this)
    }
    override fun setupScoreFields() {
        val score = showProfilePresenter?.returnScore()
        val additionalScore = showProfilePresenter?.returnAdditionalScore()

        profileMathsValue.setText(score?.maths.toString())
        profileRussianValue.setText(score?.russian.toString())
        profilePhysicsValue.setText(score?.physics.toString())
        profileComputerScienceValue.setText(score?.computerScience.toString())
        profileSocialScienceValue.setText(score?.socialScience.toString())
        profileAdditionalScoreValue.setText(additionalScore.toString())
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
    override fun setFieldEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = true
        editField.requestFocus()
        imageButton.setImageResource(R.drawable.ic_save)
    }
    override fun setFieldNonEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = false
        imageButton.setImageResource(R.drawable.ic_edit)
    }

}