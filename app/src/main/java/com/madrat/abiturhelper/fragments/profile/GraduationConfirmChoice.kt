package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.databinding.FragmentGraduationConfirmChoiceBinding
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationConfirmChoiceMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationConfirmChoicePresenter
import com.madrat.abiturhelper.util.linearManager

class GraduationConfirmChoice : Fragment(), GraduationConfirmChoiceMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var presenter: GraduationConfirmChoicePresenter? = null

    // ViewBinding variables
    private var mBinding: FragmentGraduationConfirmChoiceBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.profileGraduationConfirmChoiceTitle)

        // Инициализируем mBinding
        mBinding = FragmentGraduationConfirmChoiceBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = SpecialtiesAdapter(null, null)
        binding.confirmChoiceRecyclerView.adapter = adapter
        binding.confirmChoiceRecyclerView.linearManager()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMVP()

        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val selectedSpecialties
                = presenter?.returnSelectedSpecialties()
        selectedSpecialties?.let { showSelectedSpecialties(it) }

        // Отправляем список в фунцию для добавления пользователя в списки поступающих и
        // создания списка модели Graduation
        val graduationList = selectedSpecialties
                ?.let { presenter?.calculateGraduationData(it) }
        presenter?.saveGraduationList(graduationList)

        binding.confirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(null, R.id.action_confirm_choice_to_show_current_list)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null

        adapter = null

        mBinding = null
    }

    override fun setupMVP() {
        presenter = GraduationConfirmChoicePresenter()
    }
    override fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        binding.confirmChoiceRecyclerView.adapter = adapter
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}