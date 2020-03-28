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
import com.madrat.abiturhelper.databinding.FragmentGraduationSelectSpecialtiesBinding
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationSelectSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationSelectSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager

class GraduationSelectSpecialties
    : Fragment(), GraduationSelectSpecialtiesMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var presenter: GraduationSelectSpecialtiesPresenter? = null

    private var mBinding: FragmentGraduationSelectSpecialtiesBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.profileApplySelectSpecialtiesForGraduationTitle)

        // Инициализируем mBinding
        mBinding = FragmentGraduationSelectSpecialtiesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupMVP()

        adapter = CompleteSpecialtiesAdapter(
                presenter?.returnItemStateArray(),
                presenter?.returnSelectedSpecialties()
        )
        binding.selectForRecyclerView.adapter = adapter
        binding.selectForRecyclerView.linearManager()

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val listOfAllCompleteSpecialties
                = presenter?.returnListOfAllCompleteSpecialties()
        listOfAllCompleteSpecialties?.sortByDescending { it.amountOfStatements }
        showSpecialties(listOfAllCompleteSpecialties)

        binding.selectSaveCheckedSpecialties.setOnClickListener {view ->
            val selectedSpecialties = adapter?.returnSelectedSpecialties()
            presenter?.saveSelectedSpecialties(selectedSpecialties)

            val itemStateArray = adapter?.returnItemStateArray()
            presenter?.saveItemStateArray(itemStateArray)

            toActionId(R.id.action_select_specialties_to_confirm_choice)
        }
    }
    override fun onDestroyView() {
        presenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        presenter = GraduationSelectSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        binding.selectForRecyclerView.adapter = adapter
    }
    override fun toActionId(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}