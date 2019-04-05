package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.presenters.fragments.SetupAdditionalPresenter
import com.madrat.abiturhelper.interfaces.fragments.SetupAdditionalMVP
import kotlinx.android.synthetic.main.fragment_setup_additional.*

class SetupAdditionalView : Fragment(), SetupAdditionalMVP.View {
    companion object { val instance = SetupAdditionalView() }

    private lateinit var setupAdditionalPresenter: SetupAdditionalPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        showSpecialtiesScreen.setOnClickListener { view->
            /*setupAdditionalPresenter.saveUserData(essayValue.text.toString(),
                                                  letterValue.text.toString(),
                                                  gtoValue.text.toString())*/
            Navigation.findNavController(view).navigate(R.id.action_setupAdditional_to_pickUpSpecialtiesView)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.setUpAdditionalTitle)
        return inflater.inflate(R.layout.fragment_setup_additional,
                                container, false)
    }

    override fun setupMVP() {
        setupAdditionalPresenter = SetupAdditionalPresenter(this)
    }
}