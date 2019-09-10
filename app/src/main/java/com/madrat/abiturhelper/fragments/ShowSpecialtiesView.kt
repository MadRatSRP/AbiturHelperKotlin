package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.presenters.fragments.ShowSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_specialties.*

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var presenter: ShowSpecialtiesPresenter? = null

    override fun initializeAdapterAndSetLayoutManager(facultyId: Int) {
        adapter = SpecialtiesAdapter(facultyId) { facultyID, specialty: Specialty, position: Int ->
            facultyID?.let { onSpecialtyClicked(it, specialty, position) }
        }
        specialtiesRecyclerView.adapter = adapter
        specialtiesRecyclerView.linearManager()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        arguments?.getInt("pos")?.let {
            presenter?.initializeViewComponentsAndFillItWithData(it)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title

        return inflater.inflate(R.layout.fragment_specialties, container, false)
    }

    override fun setupMVP() {
        presenter = ShowSpecialtiesPresenter(this)
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        specialtiesRecyclerView?.adapter = adapter
    }
    override fun toStudents(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(R.id.action_showSpecialtiesView_to_showBachelors, bundle)
        }
    }
    override fun moveToStudents(list: ArrayList<Student>, specialty: Specialty) {
        val bundle = presenter?.returnSpecialtyBundle(list, specialty)
        bundle?.let { toStudents(it) }
    }
    override fun onSpecialtyClicked(facultyId: Int, specialty: Specialty, position: Int) {
        val listOfFacultyStudents = presenter?.getListOfFacultyStudentsByFacultyId(facultyId)
        listOfFacultyStudents?.let {
            moveToStudents(it[position], specialty)
        }
    }
}