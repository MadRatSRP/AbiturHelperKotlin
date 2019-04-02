package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.ProfileMVP
import com.madrat.abiturhelper.presenters.fragments.ProfilePresenter
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment(), ProfileMVP.View {
    var profilePresenter: ProfilePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        setupFields()
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
        mathsValue.text = profilePresenter?.setupMaths()
        russianValue.text = profilePresenter?.setupRussian()
        physicsValue.text = profilePresenter?.setupPhysics()
        computerScienceValue.text = profilePresenter?.setupComputerScience()
        socialScienceValue.text = profilePresenter?.setupSocialScience()
    }
}