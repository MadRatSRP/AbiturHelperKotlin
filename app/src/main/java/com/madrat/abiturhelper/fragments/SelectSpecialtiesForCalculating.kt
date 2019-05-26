package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.CompleteSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.SelectSpecialtiesForCalculatingMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.SelectSpecialtiesForCalculatingPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_select_specialties_for_calculation.*
import kotlinx.android.synthetic.main.fragment_select_specialties_for_calculation.view.*


class SelectSpecialtiesForCalculating
    : Fragment(), SelectSpecialtiesForCalculatingMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var selectSpecialtiesForCalculatingPresenter
            : SelectSpecialtiesForCalculatingPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val faculties = selectSpecialtiesForCalculatingPresenter?.returnCompleteListOfSpecilaties()

        selectForSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showSpecialties(faculties?.get(position))
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.selectSpecialtiesForCalculatingTitle)
        val view = inflater.inflate(R.layout.fragment_select_specialties_for_calculation,
                container, false)

        adapter = CompleteSpecialtiesAdapter()
        view.selectForRecyclerView.adapter = adapter
        view.selectForRecyclerView.linearManager()

        return view
    }

    override fun setupMVP() {
        selectSpecialtiesForCalculatingPresenter = SelectSpecialtiesForCalculatingPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        selectForRecyclerView?.adapter = adapter
    }
}