package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.CompleteSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.SelectSpecialtiesForCalculatingMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.SelectSpecialtiesForCalculatingPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_select_specialties_for_calculation.*
import kotlinx.android.synthetic.main.fragment_select_specialties_for_calculation.view.*

class SelectSpecialtiesForCalculating
    : Fragment(), SelectSpecialtiesForCalculatingMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var selectSpecialtiesForCalculatingPresenter
            : SelectSpecialtiesForCalculatingPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val faculties = selectSpecialtiesForCalculatingPresenter?.returnCompleteListOfSpecilaties()

        val sumOfFaculties = ArrayList<Specialty>()

        faculties?.let {
            for (i in 0 until faculties.size) {
                sumOfFaculties += faculties[i]
            }
        }

        showSpecialties(sumOfFaculties)

        selectSaveCheckedSpecialties.setOnClickListener {
            val selectedSpecialties = adapter?.returnSelectedSpecialties()
            //showLog("Список: ${array?.size}")
            selectSpecialtiesForCalculatingPresenter?.saveSelectedSpecialties(selectedSpecialties)

            val itemStateArray = adapter?.returnItemStateArray()
            selectSpecialtiesForCalculatingPresenter?.saveItemStateArray(itemStateArray)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.selectSpecialtiesForCalculatingTitle)
        val view = inflater.inflate(R.layout.fragment_select_specialties_for_calculation,
                container, false)
        setupMVP()

        adapter = CompleteSpecialtiesAdapter(
            selectSpecialtiesForCalculatingPresenter?.returnItemStateArray()
        )
        view.selectForRecyclerView.adapter = adapter
        view.selectForRecyclerView.linearManager()

        return view
    }

    override fun setupMVP() {
        selectSpecialtiesForCalculatingPresenter = SelectSpecialtiesForCalculatingPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        //adapter?.saveNewChecker(position)
        selectForRecyclerView?.adapter = adapter
    }
}