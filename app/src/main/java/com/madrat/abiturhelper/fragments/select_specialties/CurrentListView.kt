package com.madrat.abiturhelper.fragments.select_specialties

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
import com.madrat.abiturhelper.databinding.FragmentCurrentListBinding
import com.madrat.abiturhelper.interfaces.fragments.CurrentListMVP
import com.madrat.abiturhelper.model.Faculty
import com.madrat.abiturhelper.presenters.fragments.CurrentListPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog

class CurrentListView: Fragment(), CurrentListMVP.View {
    private var adapter: FacultiesAdapter? = null
    private var presenter: CurrentListPresenter? = null

    // ViewBinding variables
    private var mBinding: FragmentCurrentListBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.currentListTitle)

        // ViewBinding initialization
        mBinding = FragmentCurrentListBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = FacultiesAdapter{ faculty: Faculty, position: Int ->
            onFacultyClicked(faculty, position)}
        binding.currentListRecyclerView.linearManager()
        binding.currentListRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMVP()

        checkFacultyListSize()

        binding.currentListSwipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                checkFacultyListSize()
                binding.currentListSwipeRefresh.isRefreshing = false
            }, 1500)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null

        adapter = null

        mBinding = null
    }

    override fun setupMVP() {
        presenter = CurrentListPresenter()
    }
    private fun checkFacultyListSize() {
        val facultyList = presenter?.returnFacultyList()

        showLog(facultyList?.size.toString())
        if (facultyList?.size == null) showMoveToWorkWithSpecialtiesAlertDialog()
        else showFaculties(facultyList)
    }
    private fun showMoveToWorkWithSpecialtiesAlertDialog() {
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
    override fun showFaculties(faculties: ArrayList<Faculty>) {
        adapter?.updateFacultiesList(faculties)
        binding.currentListRecyclerView.adapter = adapter
    }
    override fun onFacultyClicked(faculty: Faculty, position: Int) {
        showLog("Выбран: ${faculty.name}")
        when (position) {
            //УНТИ
            0 -> moveToSpecialties(position, R.string.facultyUNTI)
            //ФЭУ
            1 -> moveToSpecialties(position, R.string.facultyFEU)
            //ФИТ
            2 -> moveToSpecialties(position, R.string.facultyFIT)
            //МТФ
            3 -> moveToSpecialties(position, R.string.facultyMTF)
            //УНИТ
            4 -> moveToSpecialties(position, R.string.facultyUNIT)
            //ФЭЭ
            5 -> moveToSpecialties(position, R.string.facultyFEE)
        }
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(actionId, bundle)
        }
    }
    override fun moveToSpecialties(position: Int, titleId: Int) {
        val bundle = context?.let { presenter?.returnFacultyBundle(it, position, titleId) }
        bundle?.let { toSpecialties(it, R.id.action_currentList_to_showSpecialtiesView) }
    }
}