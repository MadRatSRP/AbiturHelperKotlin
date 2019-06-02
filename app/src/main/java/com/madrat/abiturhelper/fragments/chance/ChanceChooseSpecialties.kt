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
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_chance_choose_specialties.*
import kotlinx.android.synthetic.main.fragment_chance_choose_specialties.view.*

class ChanceChooseSpecialties: Fragment() {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val faculties = myApplication.returnCompleteListOfSpecilaties()
        val sumOfFaculties = ArrayList<Specialty>()

        faculties?.let {
            for (i in 0 until faculties.size) {
                sumOfFaculties += faculties[i]
            }
        }

        showSpecialties(sumOfFaculties)

        chosenSaveCheckedSpecialties.setOnClickListener {view ->
            val selectedSpecialties = adapter?.returnSelectedSpecialties()
            selectedSpecialties?.let { myApplication.saveChosenSpecialties(it) }

            val itemStateArray = adapter?.returnItemStateArray()
            itemStateArray?.let { myApplication.saveChanceItemStateArray(it) }

            toSpecialties(R.id.action_chooseSpecialties_to_confirmChoice)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceChooseSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_chance_choose_specialties,
                container, false)

        adapter = CompleteSpecialtiesAdapter(
                myApplication.returnChanceItemStateArray()
        )
        view.chosenRecyclerView.adapter = adapter
        view.chosenRecyclerView.linearManager()

        return view
    }

    /*override*/ fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        chosenRecyclerView?.adapter = adapter
    }
    /*override*/ fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}