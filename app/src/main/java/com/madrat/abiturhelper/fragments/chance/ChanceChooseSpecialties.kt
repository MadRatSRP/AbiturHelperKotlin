package com.madrat.abiturhelper.fragments.chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.CompleteSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceChooseSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.chance.ChanceChooseSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.showSnack
import kotlinx.android.synthetic.main.fragment_chance_choose_specialties.*
import kotlinx.android.synthetic.main.fragment_chance_choose_specialties.view.*

class ChanceChooseSpecialties
    : Fragment(), ChanceChooseSpecialtiesMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var chanceChooseSpecialtiesPresenter: ChanceChooseSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listOfAllCompleteSpecialties
                = chanceChooseSpecialtiesPresenter?.returnListOfAllCompleteSpecialties()
        listOfAllCompleteSpecialties?.sortByDescending { it.amountOfStatements }
        showSpecialties(listOfAllCompleteSpecialties)

        chosenSaveCheckedSpecialties.setOnClickListener {view->
            chosenChecker(view)
        }
    }
    fun chosenChecker(view: View) {
        val selectedSpecialties = adapter?.returnSelectedSpecialties()
        selectedSpecialties
                ?.let{ chanceChooseSpecialtiesPresenter?.saveChosenSpecialties(it) }

        val itemStateArray = adapter?.returnItemStateArray()
        itemStateArray
                ?.let { chanceChooseSpecialtiesPresenter?.saveChanceItemStateArray(it) }

        if (selectedSpecialties?.size == 0) {
            view.showSnack(R.string.specialtiesNotChosen)
        } else {
            toSpecialties(R.id.action_chooseSpecialties_to_confirmChoice)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceChooseSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_chance_choose_specialties,
                container, false)
        setupMVP()

        adapter = CompleteSpecialtiesAdapter(
                chanceChooseSpecialtiesPresenter?.returnChanceItemStateArray(),
                chanceChooseSpecialtiesPresenter?.returnChosenSpecialties()
        )
        view.chosenRecyclerView.adapter = adapter
        view.chosenRecyclerView.linearManager()

        return view
    }
    override fun onDestroyView() {
        chanceChooseSpecialtiesPresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        chanceChooseSpecialtiesPresenter = ChanceChooseSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        chosenRecyclerView?.adapter = adapter
    }
    override fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}