package com.madrat.abiturhelper.fragments.pick_up_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.pick_up_specialties.SetupScoreMVP
import com.madrat.abiturhelper.presenters.fragments.SetupScorePresenter
import kotlinx.android.synthetic.main.fragment_setup_score.*
import kotlinx.android.synthetic.main.fragment_setup_score.view.*

class SetupScoreView : Fragment(), SetupScoreMVP.View {

    private lateinit var setupScorePresenter: SetupScorePresenter

    private val passingMaths = 27
    private val passingRussian = 36
    private val passingPhysics = 36
    private val passingComputerScience = 40
    private val passingSocialScience = 42
    private val scoreLimit = 100

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        showSpecialtiesScreen.setOnClickListener { view->

            var additionalScore = ""
            additionalScoreSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    additionalScore = parent?.getItemAtPosition(position).toString()
                }

            }

            setupScorePresenter.saveScore(setupScoreMathsValue.text.toString(),
                    setupScoreRussianValue.text.toString(),
                    setupScorePhysicsValue.text.toString(),
                    setupScoreComputerScienceValue.text.toString(),
                    setupScoreSocialScienceValue.text.toString(),
                    additionalScore)

            Navigation.findNavController(view).navigate(R.id.action_setupScore_to_pickUpSpecialtiesView)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.setUpScoreTitle)
        val view = inflater.inflate(R.layout.fragment_setup_score, container, false)

        val spinnerItems = resources.getStringArray(R.array.additionalScoreSpinnerEntries)
        val adapter = ArrayAdapter(context, R.layout.custom_spinner, spinnerItems)
        adapter.setDropDownViewResource(R.layout.custom_spinner)

        view.additionalScoreSpinner.adapter = adapter

        return view
    }

    override fun setupMVP() {
        setupScorePresenter = SetupScorePresenter(this)
    }
}
