package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SelectedSpecialtiesAdapter
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_calculate_user_places.*
import kotlinx.android.synthetic.main.fragment_calculate_user_places.view.*

class CalculateUserPlacesView: Fragment() {
    var adapter: SelectedSpecialtiesAdapter? = null

    val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*val selectedSpecialties = myApplication.returnSelectedSpecialties()
        selectedSpecialties?.let { showSelectedSpecialties(it) }*/

        val graduationList = myApplication.returnGraduationList()
        graduationList?.let { showGraduation(it) }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.calculateUserPlacesTitle)
        val view = inflater.inflate(R.layout.fragment_calculate_user_places,
                container, false)

        adapter = SelectedSpecialtiesAdapter()

        view.calculateRecyclerView.adapter = adapter
        view.calculateRecyclerView.linearManager()

        return view
    }

    /*override*/ fun showGraduation(graduationList: ArrayList<Graduation>) {
        adapter?.updateGraduationList(graduationList)
        calculateRecyclerView.adapter = adapter
    }
}