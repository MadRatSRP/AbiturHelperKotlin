package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.StudentsAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowStudentsMVP
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.presenters.fragments.ShowStudentsPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_show_bachelors.view.*
import kotlinx.android.synthetic.main.fragment_specialties.*

class ShowStudentsView: Fragment(), ShowStudentsMVP.View {
    private var adapter: StudentsAdapter? = null
    private var showStudentsPresenter: ShowStudentsPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val list = showStudentsPresenter?.returnCurrentListOfStudents()
        list?.let { showSpecialties(it) }
    }

    override fun setupMVP() {
        showStudentsPresenter = ShowStudentsPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title
        val view = inflater.inflate(R.layout.fragment_show_bachelors, container, false)

        adapter = StudentsAdapter()
        view.bachelorsRecyclerView.linearManager()
        view.bachelorsRecyclerView.adapter = adapter
        return view
    }

    override fun showSpecialties(students: ArrayList<Student>) {
        adapter?.updateBachelorsList(students)
        specialtiesRecyclerView?.adapter = adapter
    }
}