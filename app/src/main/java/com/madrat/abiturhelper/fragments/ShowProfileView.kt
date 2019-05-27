package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.presenters.fragments.ShowProfilePresenter
import com.madrat.abiturhelper.util.MyApplication
import kotlinx.android.synthetic.main.fragment_profile.*

class ShowProfileView: Fragment(), ProfileMVP.View {
    var showProfilePresenter: ShowProfilePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupScoreFields()

        profileUpdateScores.setOnClickListener {
            showProfilePresenter?.updateScores(profileMathsValue.text.toString().toInt(),
                    profileRussianValue.text.toString().toInt(),
                    profilePhysicsValue.text.toString().toInt(),
                    profileComputerScienceValue.text.toString().toInt(),
                    profileSocialScienceValue.text.toString().toInt(),
                    profileAdditionalScoreValue.text.toString().toInt())
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

        profileMathsValue.text = score?.maths.toString()
        profileRussianValue.text = score?.russian.toString()
        profilePhysicsValue.text = score?.physics.toString()
        profileComputerScienceValue.text = score?.computerScience.toString()
        profileSocialScienceValue.text = score?.socialScience.toString()
        profileAdditionalScoreValue.text = additionalScore.toString()
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }

}