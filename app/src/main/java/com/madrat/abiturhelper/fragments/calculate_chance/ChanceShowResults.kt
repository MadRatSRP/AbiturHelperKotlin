package com.madrat.abiturhelper.fragments.calculate_chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.ChancesAdapter
import com.madrat.abiturhelper.databinding.FragmentChanceShowResultsBinding
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceShowResultsMVP
import com.madrat.abiturhelper.model.Chance
import com.madrat.abiturhelper.presenters.fragments.chance.ChanceShowResultsPresenter
import com.madrat.abiturhelper.util.linearManager

class ChanceShowResults
    : Fragment(), ChanceShowResultsMVP.View {
    private var adapter: ChancesAdapter? = null
    private var chanceShowResultsPresenter: ChanceShowResultsPresenter? = null

    private var mBinding: FragmentChanceShowResultsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.chanceShowResultsTitle)

        // Инициализируем mBinding
        mBinding = FragmentChanceShowResultsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ChancesAdapter()
        binding.chancesRecyclerView.linearManager()
        binding.chancesRecyclerView.adapter = adapter
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val listOfChances = chanceShowResultsPresenter?.returnListOfChances()
        listOfChances?.let { showListOfChances(it) }

        binding.chancesToProfile.setOnClickListener {
            toActionId(null, R.id.action_showResults_to_profile)
        }
    }
    override fun onDestroyView() {
        chanceShowResultsPresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        chanceShowResultsPresenter = ChanceShowResultsPresenter()
    }
    override fun showListOfChances(listOfChances: ArrayList<Chance>) {
        adapter?.updateListOfChances(listOfChances)
        binding.chancesRecyclerView.adapter = adapter
    }
    override fun toActionId(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}