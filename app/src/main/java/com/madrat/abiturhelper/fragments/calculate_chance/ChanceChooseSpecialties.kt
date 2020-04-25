package com.madrat.abiturhelper.fragments.calculate_chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.CompleteSpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentChanceChooseSpecialtiesBinding
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceChooseSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.chance.ChanceChooseSpecialtiesPresenter
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showSnack

class ChanceChooseSpecialties
    : Fragment(), ChanceChooseSpecialtiesMVP.View {
    private var adapter: CompleteSpecialtiesAdapter? = null
    private var presenter: ChanceChooseSpecialtiesPresenter? = null

    // ViewBinding variables
    private var mBinding: FragmentChanceChooseSpecialtiesBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceChooseSpecialtiesTitle)

        // ViewBinding initialization
        mBinding = FragmentChanceChooseSpecialtiesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupMVP()

        adapter = CompleteSpecialtiesAdapter(
                presenter?.returnChanceItemStateArray(),
                presenter?.returnChosenSpecialties()
        )

        binding.chosenRecyclerView.adapter = adapter
        binding.chosenRecyclerView.linearManager()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfAllCompleteSpecialties
                = presenter?.returnListOfAllCompleteSpecialties()
        listOfAllCompleteSpecialties?.sortByDescending { it.amountOfStatements }
        showSpecialties(listOfAllCompleteSpecialties)

        binding.chosenSaveCheckedSpecialties.setOnClickListener {
            chosenChecker(view)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null

        presenter = null

        mBinding = null
    }

    private fun chosenChecker(view: View) {
        val selectedSpecialties = adapter?.returnSelectedSpecialties()
        selectedSpecialties
                ?.let{ presenter?.saveChosenSpecialties(it) }

        val itemStateArray = adapter?.returnItemStateArray()
        itemStateArray
                ?.let { presenter?.saveChanceItemStateArray(it) }

        if (selectedSpecialties?.size == 0) {
            view.showSnack(R.string.specialtiesNotChosen)
        } else {
            toSpecialties(R.id.action_chooseSpecialties_to_confirmChoice)
        }
    }
    override fun setupMVP() {
        presenter = ChanceChooseSpecialtiesPresenter()
    }
    override fun showSpecialties(specialties: ArrayList<Specialty>?) {
        specialties?.let { adapter?.updateSpecialtiesList(it) }
        binding.chosenRecyclerView.adapter = adapter
    }
    override fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}