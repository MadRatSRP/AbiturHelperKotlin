package com.madrat.abiturhelper.fragments.pick_up_specialties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.WorkWithSpecialtiesMVP
import com.madrat.abiturhelper.presenters.fragments.WorkWithSpecialtiesPresenter
import com.madrat.abiturhelper.util.moveToSelectedFragment
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.showSnack
import kotlinx.android.synthetic.main.fragment_work_with_specialties.*
import kotlin.system.measureTimeMillis

class WorkWithSpecialtiesView
    : Fragment(), WorkWithSpecialtiesMVP.View{
    private var presenter: WorkWithSpecialtiesPresenter? = null

    override fun setupMVP() {
        presenter = WorkWithSpecialtiesPresenter(this)
    }
    override fun onToCurrentListClicked() {
        moveToSelectedFragment(R.id.action_pickUpSpecialtiesView_to_currentList)
    }
    override fun onToResultClicked() {
        moveToSelectedFragment(R.id.action_pickUpSpecialtiesView_to_resultView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val time = measureTimeMillis {
            // Первый шаг - разбить список специальностей по факультетам,
            // выделить из списка студентов тех, кто собирается поступать на бакалавриат
            context?.let { presenter?.generateBachelorsAndSpecialtiesLists(it) }
            // Второй шаг - разбить список поступающих по типу баллов
            // и высчитать свободные баллы для факультетов
            presenter?.generateScoreTypedListsAndCalculateAvailableFacultyPlaces()
            // Третий шаг
            presenter?.separateStudentsBySpecialties()
            // Четвёртый шаг
            context?.let { presenter?.checkSpecialtiesForMinimalScore(it) }
        }
        showLog("Затраченное время: $time")

        view?.showSnack(R.string.workWithSpecialtiesListsArePrepared)

        workToCurrentList.setOnClickListener {
            onToCurrentListClicked()
        }
        workToResult.setOnClickListener {
            onToResultClicked()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.workWithSpecialtiesTitle)
        return inflater.inflate(R.layout.fragment_work_with_specialties, container, false)
    }
    override fun onDestroyView() {
        presenter = null
        super.onDestroyView()
    }
}