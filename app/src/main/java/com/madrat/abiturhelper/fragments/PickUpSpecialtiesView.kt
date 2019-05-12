package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.FacultiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.PickUpSpecialtiesMVP
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.PickUpSpecialtiesPresenter
import com.madrat.abiturhelper.repository.PickUpSpecialtiesRepository
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.*
import kotlinx.android.synthetic.main.fragment_pick_up_specialties.view.*

class PickUpSpecialtiesView
    : Fragment(), PickUpSpecialtiesMVP.View{
    private var adapter: FacultiesAdapter? = null

    private var pickUpSpecialtiesPresenter: PickUpSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        pickUpSpecialtiesRecyclerView.linearManager()

        // Первый шаг - разбить список специальностей по факультетам,
        // выделить из списка студентов тех, кто собирается поступать на бакалавриат
        context?.let { pickUpSpecialtiesPresenter?.generateBachelorsAndSpecialtiesLists(it) }

        // Второй шаг - разбить список поступающих по типу баллов
        // и высчитать свободные баллы для факультетов
        pickUpSpecialtiesPresenter?.generateScoreTypedListsAndCalculateAvailableFacultyPlaces()

        // Получаем список факультетов и производим апдейт списка
        val facultyList = pickUpSpecialtiesPresenter?.returnFacultyList()
        facultyList?.let { showFaculties(it) }

        // Третий шаг
        pickUpSpecialtiesPresenter?.separateStudentsBySpecialties()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_pick_up_specialties, container, false)

        adapter = FacultiesAdapter{ faculty: Faculty, position: Int -> onFacultyClicked(faculty, position)}
        view.pickUpSpecialtiesRecyclerView.adapter = adapter

        return view
    }

    override fun setupMVP() {
        pickUpSpecialtiesPresenter = PickUpSpecialtiesPresenter(this,
                PickUpSpecialtiesRepository())
    }
    override fun showFaculties(faculties: ArrayList<Faculty>) {
        pickUpSpecialtiesRecyclerView.post {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }

        /*activity?.runOnUiThread {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }*/
    }
    override fun onFacultyClicked(faculty: Faculty, position: Int) {
        showLog("Выбран: ${faculty.name}")
        val faculties = pickUpSpecialtiesPresenter?.returnFaculties()
        faculties?.let {
            when (position) {
                //УНТИ
                0 -> moveToSpecialties(position,"УНТИ", it.untiList)
                //ФЭУ
                1 -> moveToSpecialties(position,"ФЭУ", it.feuList)
                //ФИТ
                2 -> moveToSpecialties(position,"ФИТ", it.fitList)
                //МТФ
                3 -> moveToSpecialties(position,"МТФ", it.mtfList)
                //УНИТ
                4 -> moveToSpecialties(position,"УНИТ", it.unitList)
                //ФЭЭ
                5 -> moveToSpecialties(position,"ФЭЭ", it.feeList)
            }
        }
    }
    override fun toSpecialties(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_pickUpSpecialtiesView_to_showSpecialtiesView, bundle)
        }
    }
    override fun moveToSpecialties(position: Int, title: String, list: ArrayList<Specialty>) {
        val bundle = pickUpSpecialtiesPresenter?.returnFacultyBundle(position, title, list)
        bundle?.let { toSpecialties(it) }
    }
}