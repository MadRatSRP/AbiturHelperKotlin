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
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.presenters.fragments.WorkWithSpecialtiesPresenter
import com.madrat.abiturhelper.repository.PickUpSpecialtiesRepository
import kotlinx.android.synthetic.main.fragment_work_with_specialties.*

class WorkWithSpecialtiesView
    : Fragment(), WorkWithSpecialtiesMVP.View{
    private var adapter: FacultiesAdapter? = null
    private var workWithSpecialtiesPresenter: WorkWithSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        // Первый шаг - разбить список специальностей по факультетам,
        // выделить из списка студентов тех, кто собирается поступать на бакалавриат
        context?.let { workWithSpecialtiesPresenter?.generateBachelorsAndSpecialtiesLists(it) }

        // Второй шаг - разбить список поступающих по типу баллов
        // и высчитать свободные баллы для факультетов
        //workWithSpecialtiesPresenter?.generateScoreTypedListsAndCalculateAvailableFacultyPlaces()

        // Третий шаг
        //workWithSpecialtiesPresenter?.separateStudentsBySpecialties()

        // Четвёртый шаг
        //context?.let { workWithSpecialtiesPresenter?.checkSpecialtiesForMinimalScore(it) }

        // Пятый шаг
        //workWithSpecialtiesPresenter?.checkForZeroMinimalScore()

        /*workWithSpecialtiesToCurrentList.setOnClickListener {
            toActionId(R.id.action_pickUpSpecialtiesView_to_currentList)
        }

        workWithSpecialtiesToResultScreen.setOnClickListener {
            toActionId(R.id.action_pickUpSpecialtiesView_to_resultView)
        }*/
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.pickUpSpecialtiesTitle)
        val view = inflater.inflate(R.layout.fragment_work_with_specialties, container, false)

        /*adapter = FacultiesAdapter{ faculty: Faculty, position: Int -> onFacultyClicked(faculty, position)}
        view.pickUpSpecialtiesRecyclerView.adapter = adapter*/

        return view
    }

    override fun setupMVP() {
        workWithSpecialtiesPresenter = WorkWithSpecialtiesPresenter(this,
                PickUpSpecialtiesRepository())
    }
    /*override fun showFaculties(faculties: ArrayList<Faculty>) {
        pickUpSpecialtiesRecyclerView.post {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }

        /*activity?.runOnUiThread {
            adapter?.updateFacultiesList(faculties)
            pickUpSpecialtiesRecyclerView.adapter = adapter
        }*/
    }*/
    /*override fun onFacultyClicked(faculty: Faculty, position: Int) {
        showLog("Выбран: ${faculty.name}")
        when (position) {
            //УНТИ
            0 -> moveToSpecialties(position, R.string.titleUNTI)
            //ФЭУ
            1 -> moveToSpecialties(position, R.string.titleFEU)
            //ФИТ
            2 -> moveToSpecialties(position, R.string.titleFIT)
            //МТФ
            3 -> moveToSpecialties(position, R.string.titleMTF)
            //УНИТ
            4 -> moveToSpecialties(position, R.string.titleUNIT)
            //ФЭЭ
            5 -> moveToSpecialties(position, R.string.titleFEE)
        }
    }*/
    override fun toActionId(actionId: Int) {
        view?.let {
            Navigation.findNavController(it)
                .navigate(/*R.id.action_pickUpSpecialtiesView_to_showSpecialtiesView*/actionId)
        }
    }
    /*override fun moveToSpecialties(position: Int, titleId: Int) {
        val bundle = context?.let { workWithSpecialtiesPresenter?.returnFacultyBundle(it, position, titleId) }
        bundle?.let { toActionId(it) }
    }*/
}