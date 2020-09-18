package com.madrat.abiturhelper.ui.fragments.calculate_chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.adapter.ChancesAdapter
import com.madrat.abiturhelper.databinding.FragmentChanceShowResultsBinding
import com.madrat.abiturhelper.data.interfaces.fragments.chance.ChanceShowResultsMVP
import com.madrat.abiturhelper.data.model.Chance
import com.madrat.abiturhelper.data.presenters.fragments.chance.ChanceShowResultsPresenter
import com.madrat.abiturhelper.util.linearManager

class ChanceShowResults
    : Fragment(), ChanceShowResultsMVP.View {
    private var adapter: ChancesAdapter? = null
    private var presenter: ChanceShowResultsPresenter? = null

    // ViewBinding variables
    private var mBinding: FragmentChanceShowResultsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.chanceShowResultsTitle)

        // ViewBinding Initialization
        mBinding = FragmentChanceShowResultsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ChancesAdapter()
        binding.chancesRecyclerView.linearManager()
        binding.chancesRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMVP()

        val listOfChances = presenter?.returnListOfChances()
        listOfChances?.let { showListOfChances(it) }

        binding.chancesToProfile.setOnClickListener {
            toActionId(null, R.id.action_showResults_to_profile)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null

        adapter = null

        mBinding = null
    }

    override fun setupMVP() {
        presenter = ChanceShowResultsPresenter()
    }
    override fun showListOfChances(listOfChances: ArrayList<Chance>) {
        adapter?.updateListOfChances(listOfChances)
        binding.chancesRecyclerView.adapter = adapter
    }
    override fun toActionId(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}