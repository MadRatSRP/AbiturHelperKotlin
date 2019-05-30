package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SelectedSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationShowCurrentListMVP
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationShowCurrentListPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_graduation_show_current_list.*
import kotlinx.android.synthetic.main.fragment_graduation_show_current_list.view.*

class GraduationShowCurrentList: Fragment(), GraduationShowCurrentListMVP.View {
    private var adapter: SelectedSpecialtiesAdapter? = null
    private var graduationShowCurrentListPresenter: GraduationShowCurrentListPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val graduationList = graduationShowCurrentListPresenter?.returnGraduationList()
        graduationList?.let { showGraduation(it) }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.calculateUserPlacesTitle)
        val view = inflater.inflate(R.layout.fragment_graduation_show_current_list,
                container, false)

        adapter = SelectedSpecialtiesAdapter()
        view.calculateRecyclerView.adapter = adapter
        view.calculateRecyclerView.linearManager()

        return view
    }
    override fun onDestroyView() {
        graduationShowCurrentListPresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        graduationShowCurrentListPresenter = GraduationShowCurrentListPresenter()
    }
    override fun showGraduation(graduationList: ArrayList<Graduation>) {
        adapter?.updateGraduationList(graduationList)
        calculateRecyclerView.adapter = adapter
    }
}