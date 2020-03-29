package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.adapter.StudentsAdapter
import com.madrat.abiturhelper.databinding.FragmentShowStudentsBinding
import com.madrat.abiturhelper.interfaces.fragments.ShowStudentsMVP
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.presenters.fragments.ShowStudentsPresenter
import com.madrat.abiturhelper.util.linearManager

class ShowStudentsView
    : Fragment(), ShowStudentsMVP.View {
    private var adapter: StudentsAdapter? = null
    private var presenter: ShowStudentsPresenter? = null

    private var mBinding: FragmentShowStudentsBinding? = null
    private val binding get() = mBinding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val list = presenter?.returnCurrentListOfStudents()
        list?.let { showStudents(it) }
    }

    override fun setupMVP() {
        presenter = ShowStudentsPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title

        // Инициализируем mBinding
        mBinding = FragmentShowStudentsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = StudentsAdapter()
        binding.studentsRecyclerView.linearManager()
        binding.studentsRecyclerView.adapter = adapter
        return view
    }

    override fun showStudents(students: ArrayList<Student>) {
        adapter?.updateBachelorsList(students)
        binding.studentsRecyclerView.adapter = adapter
    }
}