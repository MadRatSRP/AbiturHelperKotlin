package com.madrat.abiturhelper.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.ui.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentShowFittingSpecialtiesBinding
import com.madrat.abiturhelper.data.interfaces.fragments.ShowFittingSpecialtiesMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.presenters.fragments.ShowFittingSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager

class ShowFittingSpecialtiesView
    : Fragment(), ShowFittingSpecialtiesMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var presenter: ShowFittingSpecialtiesPresenter? = null

    private var mBinding: FragmentShowFittingSpecialtiesBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setupMVP()
        val title = arguments?.getInt("listId")?.let {listId->
            context?.let { presenter?.returnTitleBasedOnListID(it, listId) }
        }
        (activity as AppCompatActivity).supportActionBar?.title = title
        //context?.getString(R.string.resultShowFittingSpecialties)

        // Инициализируем mBinding
        mBinding = FragmentShowFittingSpecialtiesBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = SpecialtiesAdapter(null,null)
        binding.fittingRecyclerView.adapter = adapter
        binding.fittingRecyclerView.linearManager()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val faculties = when(arguments?.getInt("listId")) {
        100 -> showFittingSpecialtiesPresenter?.returnListOfSpecialtiesWithZeroMinimalScore()
        200 -> showFittingSpecialtiesPresenter?.returnListOfFittingSpecialties()
        300 -> showFittingSpecialtiesPresenter?.returnCompleteListOfSpecilaties()
        else -> null
        }*/
        val faculties = arguments?.getInt("listId")
                ?.let { presenter?.returnListBasedOnListID(it) }

        binding.fittingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showSpecialties(faculties?.get(position))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null

        mBinding = null

        presenter = null
    }

    override fun setupMVP() {
        presenter = ShowFittingSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        binding.fittingRecyclerView.adapter = adapter
    }
}