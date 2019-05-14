package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.FacultiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.CurrentListMVP
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.presenters.fragments.CurrentListPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import kotlinx.android.synthetic.main.fragment_current_list.*
import kotlinx.android.synthetic.main.fragment_current_list.view.*

class CurrentListView: Fragment(), CurrentListMVP.View {
    private var adapter: FacultiesAdapter? = null
    private var currentListPresenter: CurrentListPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        currentListSwipeRefresh?.setOnRefreshListener {
            Handler().postDelayed({
                checkFacultyListSize()
                currentListSwipeRefresh?.isRefreshing = false
            }, 1500)
        }


    }
    fun checkFacultyListSize() {
        val facultyList = currentListPresenter?.returnFacultyList()
        showLog(facultyList?.size.toString())
        if (facultyList?.size == null) showMoveToWorkWithSpecialtiesAlertDialog()
        else facultyList.let { showFaculties(it) }
    }

    fun showMoveToWorkWithSpecialtiesAlertDialog() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setCancelable(true)
        builder?.setTitle("Переход на экран работы со специальностями")
        builder?.setMessage("Будет выполнен переход на экран работы со специальностями")

        builder?.setPositiveButton("OK"){_, _ ->
            toSpecialties(null,
                    R.id.action_currentList_to_pickUpSpecialtiesView)
        }
        builder?.setNeutralButton("Отмена") {_, _ ->}
        builder?.show()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.currentListTitle)
        val view = inflater.inflate(R.layout.fragment_current_list, container, false)

        adapter = FacultiesAdapter{ faculty: Faculty, position: Int ->
            onFacultyClicked(faculty, position)}
        view.currentListRecyclerView.linearManager()
        view.currentListRecyclerView.adapter = adapter
        return view
    }

    override fun setupMVP() {
        currentListPresenter = CurrentListPresenter()
    }
    override fun showFaculties(faculties: ArrayList<Faculty>) {
        adapter?.updateFacultiesList(faculties)
        currentListRecyclerView.adapter = adapter
    }
    override fun onFacultyClicked(faculty: Faculty, position: Int) {
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
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(actionId, bundle)
        }
    }
    override fun moveToSpecialties(position: Int, titleId: Int) {
        val bundle = context?.let { currentListPresenter?.returnFacultyBundle(it, position, titleId) }
        bundle?.let { toSpecialties(it, R.id.action_currentList_to_showSpecialtiesView) }
    }
}