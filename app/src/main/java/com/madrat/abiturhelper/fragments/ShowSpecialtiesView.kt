package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_specialties.*
import kotlinx.android.synthetic.main.fragment_specialties.view.*

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    companion object { val instance = ShowSpecialtiesView() }
    private var adapter: SpecialtiesAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")

        (activity as AppCompatActivity).supportActionBar?.title = title
        val view = inflater.inflate(R.layout.fragment_specialties, container, false)

        //val bundle = Bundle()
        @Suppress("UNCHECKED_CAST")
        val list = arguments?.getSerializable("array") as? ArrayList<Specialty> ?: return null
        //val array = bundle.getSerializable("array")

        adapter = SpecialtiesAdapter()
        view.specialtiesRecyclerView.adapter = adapter

        view.specialtiesRecyclerView.linearManager()

        showSpecialties(list)

        return view
    }

    override fun showSpecialties(specialties: List<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        specialtiesRecyclerView?.adapter = adapter
    }
}