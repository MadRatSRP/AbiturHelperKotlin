package com.madrat.abiturhelper.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentSpecialtiesBinding
import com.madrat.abiturhelper.data.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.model.Student
import com.madrat.abiturhelper.data.presenters.fragments.ShowSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var presenter: ShowSpecialtiesPresenter? = null

    private var mBinding: FragmentSpecialtiesBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title

        // Инициализируем mBinding
        mBinding = FragmentSpecialtiesBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMVP()

        arguments?.getInt("pos")?.let {
            presenter?.initializeViewComponentsAndFillItWithData(it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null

        presenter = null

        mBinding = null
    }

    override fun initializeAdapterAndSetLayoutManager(facultyId: Int) {
        adapter = SpecialtiesAdapter(facultyId) { facultyID, specialty: Specialty, position: Int ->
            facultyID?.let { onSpecialtyClicked(it, specialty, position) }
        }
        binding.specialtiesRecyclerView.adapter = adapter
        binding.specialtiesRecyclerView.linearManager()
    }
    override fun setupMVP() {
        presenter = ShowSpecialtiesPresenter(this)
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        binding.specialtiesRecyclerView.adapter = adapter
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