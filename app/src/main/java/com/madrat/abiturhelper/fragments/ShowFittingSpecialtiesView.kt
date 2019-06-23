package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowFittingSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.ShowFittingSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_show_fitting_specialties.*
import kotlinx.android.synthetic.main.fragment_show_fitting_specialties.view.*

class ShowFittingSpecialtiesView
    : Fragment(), ShowFittingSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var showFittingSpecialtiesPresenter: ShowFittingSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*val faculties = when(arguments?.getInt("listId")) {
            100 -> showFittingSpecialtiesPresenter?.returnListOfSpecialtiesWithZeroMinimalScore()
            200 -> showFittingSpecialtiesPresenter?.returnListOfFittingSpecialties()
            300 -> showFittingSpecialtiesPresenter?.returnCompleteListOfSpecilaties()
            else -> null
        }*/
        val faculties = arguments?.getInt("listId")
                ?.let { showFittingSpecialtiesPresenter?.returnListBasedOnListID(it) }

        fittingSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showSpecialties(faculties?.get(position))
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setupMVP()
        val title = arguments?.getInt("listId")?.let {listId->
            context?.let { showFittingSpecialtiesPresenter?.returnTitleBasedOnListID(it, listId) }
        }
        (activity as AppCompatActivity).supportActionBar?.title = title
                //context?.getString(R.string.resultShowFittingSpecialties)
        val view = inflater.inflate(R.layout.fragment_show_fitting_specialties,
                container, false)

        adapter = SpecialtiesAdapter(null)
        view.fittingRecyclerView.adapter = adapter
        view.fittingRecyclerView.linearManager()
        return view
    }
    override fun onDestroyView() {
        showFittingSpecialtiesPresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        showFittingSpecialtiesPresenter = ShowFittingSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        fittingRecyclerView?.adapter = adapter
    }
}