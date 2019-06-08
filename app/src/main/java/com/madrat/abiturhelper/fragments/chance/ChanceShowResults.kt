package com.madrat.abiturhelper.fragments.chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.ChancesAdapter
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceShowResultsMVP
import com.madrat.abiturhelper.model.Chance
import com.madrat.abiturhelper.presenters.fragments.chance.ChanceShowResultsPresenter
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_chance_show_results.*
import kotlinx.android.synthetic.main.fragment_chance_show_results.view.*

class ChanceShowResults
    : Fragment(), ChanceShowResultsMVP.View {
    private var adapter: ChancesAdapter? = null
    private var chanceShowResultsPresenter: ChanceShowResultsPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val listOfChances = chanceShowResultsPresenter?.returnListOfChances()
        listOfChances?.let { showListOfChances(it) }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.chanceShowResultsTitle)
        val view = inflater.inflate(R.layout.fragment_chance_show_results,
                container, false)

        adapter = ChancesAdapter()
        view.chancesRecyclerView.linearManager()
        view.chancesRecyclerView.adapter = adapter
        return view
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
        chancesRecyclerView.adapter = adapter
    }
}