package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.CompleteSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationSelectSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationSelectSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_graduation_select_specialties.*
import kotlinx.android.synthetic.main.fragment_graduation_select_specialties.view.*

class GraduationSelectSpecialties
    : Fragment(), GraduationSelectSpecialtiesMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var graduationSelectSpecialtiesPresenter
            : GraduationSelectSpecialtiesPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val faculties = graduationSelectSpecialtiesPresenter?.returnCompleteListOfSpecilaties()

        val sumOfFaculties = ArrayList<Specialty>()

        faculties?.let {
            for (i in 0 until faculties.size) {
                sumOfFaculties += faculties[i]
            }
        }

        showSpecialties(sumOfFaculties)

        selectSaveCheckedSpecialties.setOnClickListener {view ->
            val selectedSpecialties = adapter?.returnSelectedSpecialties()
            //showLog("Список: ${array?.size}")
            graduationSelectSpecialtiesPresenter?.saveSelectedSpecialties(selectedSpecialties)

            val itemStateArray = adapter?.returnItemStateArray()
            graduationSelectSpecialtiesPresenter?.saveItemStateArray(itemStateArray)

            toSpecialties(null, R.id.action_select_specialties_to_confirm_choice)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.profileApplySelectSpecialtiesForGraduationTitle)
        val view = inflater.inflate(R.layout.fragment_graduation_select_specialties,
                container, false)
        setupMVP()

        adapter = CompleteSpecialtiesAdapter(
                graduationSelectSpecialtiesPresenter?.returnItemStateArray(),
                graduationSelectSpecialtiesPresenter?.returnSelectedSpecialties()
        )
        view.selectForRecyclerView.adapter = adapter
        view.selectForRecyclerView.linearManager()

        return view
    }

    override fun setupMVP() {
        graduationSelectSpecialtiesPresenter = GraduationSelectSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        //adapter?.saveNewChecker(position)
        selectForRecyclerView?.adapter = adapter
    }
    /*override*/ fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}