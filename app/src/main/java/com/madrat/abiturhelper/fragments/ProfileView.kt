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
import com.madrat.abiturhelper.presenters.fragments.ProfilePresenter
import com.madrat.abiturhelper.util.MyApplication
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileView: Fragment(), ProfileMVP.View {
    var profilePresenter: ProfilePresenter? = null
    private val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupFields()

        profileShowFinalList.setOnClickListener {
            val bundle = profilePresenter?.returnBundleWithListID(300)
            toSpecialties(bundle, R.id.action_profile_to_showFittingSpecialties)
        }

        profileUpdateScores.setOnClickListener {

        }

        val score = myApplication.returnScore()
        val additionalScore = myApplication.returnAdditionalScore()

        profileMathsValue.text = score?.maths.toString()
        profileRussianValue.text = score?.russian.toString()
        profilePhysicsValue.text = score?.physics.toString()
        profileComputerScienceValue.text = score?.computerScience.toString()
        profileSocialScienceValue.text = score?.socialScience.toString()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profileTitle)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun setupMVP() {
        profilePresenter = ProfilePresenter(this)
    }
    override fun setupFields() {
        /*mathsValue.text = profilePresenter?.setupMaths()
        russianValue.text = profilePresenter?.setupRussian()
        physicsValue.text = profilePresenter?.setupPhysics()
        computerScienceValue.text = profilePresenter?.setupComputerScience()
        socialScienceValue.text = profilePresenter?.setupSocialScience()*/
    }

    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}