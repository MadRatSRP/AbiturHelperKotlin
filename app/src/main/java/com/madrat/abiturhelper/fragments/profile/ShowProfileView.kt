package com.madrat.abiturhelper.fragments.profile

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
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.ShowProfilePresenter
import com.madrat.abiturhelper.util.showSnack
import kotlinx.android.synthetic.main.fragment_profile.*

class ShowProfileView: Fragment(), ProfileMVP.View {
    private var showProfilePresenter: ShowProfilePresenter? = null

    //private var adapter: SpecialtiesAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupScoreFields()
        setupSpecialtiesFields()

        var isMathsEditable = true
        var isRussianEditable = true
        var isPhysicsEditable = true
        var isComputerScienceEditable = true
        var isSocialScienceEditable = true
        var isAdditionalScoreEditable = true

        profileMathsEditValue.setOnClickListener {
            isMathsEditable = checkFieldForBeingEditable(isMathsEditable,
                    profileMathsValue, profileMathsEditValue)
        }
        profileRussianEditValue.setOnClickListener {
            isRussianEditable = checkFieldForBeingEditable(isRussianEditable,
                    profileRussianValue, profileRussianEditValue)
        }
        profilePhysicsEditValue.setOnClickListener {
            isPhysicsEditable = checkFieldForBeingEditable(isPhysicsEditable,
                    profilePhysicsValue, profilePhysicsEditValue)
        }
        profileComputerScienceEditValue.setOnClickListener {
            isComputerScienceEditable = checkFieldForBeingEditable(isComputerScienceEditable,
                    profileComputerScienceValue, profileComputerScienceEditValue)
        }
        profileSocialScienceEditValue.setOnClickListener {
            isSocialScienceEditable = checkFieldForBeingEditable(isSocialScienceEditable,
                    profileSocialScienceValue, profileSocialScienceEditValue)
        }
        profileAdditionalScoreEditValue.setOnClickListener {
            isAdditionalScoreEditable = checkFieldForBeingEditable(isAdditionalScoreEditable,
                    profileAdditionalScoreValue, profileAdditionalScoreEditValue)
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
        profileApplySelectSpecialtiesForGraduation.setOnClickListener {
            toSpecialties(null, R.id.action_profile_to_selectSpecialtiesForCalculating)
        }
        profileAddToListsCalculatePositions.setOnClickListener {
            toSpecialties(null, R.id.action_profile_to_show_current_list)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profileTitle)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        /*adapter = SpecialtiesAdapter(null)
        view.profileAddToListsRecyclerView.adapter = adapter
        view.profileAddToListsRecyclerView.linearManager()*/

        return view
    }
    override fun onDestroyView() {
        showProfilePresenter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        showProfilePresenter = ShowProfilePresenter(this)
    }
    override fun setupScoreFields() {
        val score = showProfilePresenter?.returnCheckedScore()
        val additionalScore = showProfilePresenter?.returnAdditionalScore()

        profileMathsValue.setText(score?.maths.toString())
        profileRussianValue.setText(score?.russian.toString())
        profilePhysicsValue.setText(score?.physics.toString())
        profileComputerScienceValue.setText(score?.computerScience.toString())
        profileSocialScienceValue.setText(score?.socialScience.toString())
        profileAdditionalScoreValue.setText(additionalScore.toString())
    }
    override fun setupSpecialtiesFields() {
        val amountOfFinalSpecialties = showProfilePresenter?.returnAmountOfFinalSpecialties()
        profileFinalListOfSpecialtiesAmountValue.setText(amountOfFinalSpecialties.toString())

        /*val selectedSpecialties = showProfilePresenter?.returnSelectedSpecialties()
        showSelectedSpecialties(selectedSpecialties)*/
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
    override fun setFieldEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = true
        editField.requestFocus()
        editField.text.clear()
        imageButton.setImageResource(R.drawable.ic_save)
    }
    override fun setFieldNonEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = false
        imageButton.setImageResource(R.drawable.ic_edit)
    }
    override fun checkFieldForBeingEditable(boolean: Boolean, editField: EditText,
                                            imageButton: ImageButton): Boolean {
        return if (boolean) {
            setFieldEditable(editField, imageButton)
            false
        } else {
            setFieldNonEditable(editField, imageButton)
            true
        }
    }

    override fun showSelectedSpecialties(specialties: ArrayList<Specialty>?) {
        /*specialties?.let { adapter?.updateSpecialtiesList(it) }
        profileAddToListsRecyclerView?.adapter = adapter*/
    }
}