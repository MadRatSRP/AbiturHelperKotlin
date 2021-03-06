package com.madrat.abiturhelper.ui.fragments.calculate_chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.ui.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentChanceConfirmChoiceBinding
import com.madrat.abiturhelper.data.interfaces.fragments.chance.ChanceConfirmChoiceMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.data.presenters.fragments.chance.ChanceConfirmChoicePresenter
import com.madrat.abiturhelper.util.linearManager

class ChanceConfirmChoice
    : Fragment(), ChanceConfirmChoiceMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var presenter: ChanceConfirmChoicePresenter? = null

    // ViewBinding variables
    private var mBinding: FragmentChanceConfirmChoiceBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceConfirmChoiceTitle)

        // ViewBinding initialization
        mBinding = FragmentChanceConfirmChoiceBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = SpecialtiesAdapter(null, null)
        binding.chanceConfirmChoiceRecyclerView.adapter = adapter
        binding.chanceConfirmChoiceRecyclerView.linearManager()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMVP()
        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val chosenSpecialties = presenter?.returnChosenSpecialties()
        chosenSpecialties?.let { showSelectedSpecialties(it) }

        val listOfChances = chosenSpecialties
                ?.let { presenter?.calculateChancesData(it) }
        listOfChances?.let { presenter?.saveListOfChances(it) }

        binding.chanceConfirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(R.id.action_confirmChoice_to_showResults)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null

        adapter = null

        mBinding = null
    }

    override fun setupMVP() {
        presenter = ChanceConfirmChoicePresenter()
    }
    override fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        binding.chanceConfirmChoiceRecyclerView.adapter = adapter
    }
    override fun toSpecialties(actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId) }
    }
}