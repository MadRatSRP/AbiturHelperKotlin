package com.madrat.abiturhelper.fragments.calculate_chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentChanceConfirmChoiceBinding
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceConfirmChoiceMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.chance.ChanceConfirmChoicePresenter
import com.madrat.abiturhelper.util.linearManager

class ChanceConfirmChoice
    : Fragment(), ChanceConfirmChoiceMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var chanceConfirmChoicePresenter: ChanceConfirmChoicePresenter? = null

    private var mBinding: FragmentChanceConfirmChoiceBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceConfirmChoiceTitle)

        // Инициализируем mBinding
        mBinding = FragmentChanceConfirmChoiceBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = SpecialtiesAdapter(null, null)
        binding.chanceConfirmChoiceRecyclerView.adapter = adapter
        binding.chanceConfirmChoiceRecyclerView.linearManager()
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val chosenSpecialties = chanceConfirmChoicePresenter?.returnChosenSpecialties()
        chosenSpecialties?.let { showSelectedSpecialties(it) }

        val listOfChances = chosenSpecialties
                ?.let { chanceConfirmChoicePresenter?.calculateChancesData(it) }
        listOfChances?.let { chanceConfirmChoicePresenter?.saveListOfChances(it) }

        binding.chanceConfirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(R.id.action_confirmChoice_to_showResults)
        }
    }
    override fun onDestroyView() {
        chanceConfirmChoicePresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        chanceConfirmChoicePresenter = ChanceConfirmChoicePresenter()
    }
    override fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        binding.chanceConfirmChoiceRecyclerView.adapter = adapter
    }
    override fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}