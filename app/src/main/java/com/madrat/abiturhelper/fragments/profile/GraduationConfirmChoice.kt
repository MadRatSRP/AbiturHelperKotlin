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
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationConfirmChoiceMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationConfirmChoicePresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.*
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.view.*

class GraduationConfirmChoice : Fragment(), GraduationConfirmChoiceMVP.View {
    private var adapter: SpecialtiesAdapter? = null
    private var graduationConfirmChoicePresenter: GraduationConfirmChoicePresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()
        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val selectedSpecialties
                = graduationConfirmChoicePresenter?.returnSelectedSpecialties()
        selectedSpecialties?.let { showSelectedSpecialties(it) }

        // Отправляем список в фунцию для добавления пользователя в списки поступающих и
        // создания списка модели Graduation
        val graduationList = selectedSpecialties
                ?.let { graduationConfirmChoicePresenter?.calculateGraduationData(it) }
        graduationConfirmChoicePresenter?.saveGraduationList(graduationList)

        confirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(null, R.id.action_confirm_choice_to_show_current_list)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity)
                .supportActionBar?.setTitle(R.string.profileGraduationConfirmChoiceTitle)
        val view = inflater.inflate(R.layout.fragment_graduation_confirm_choice,
                container, false)

        adapter = SpecialtiesAdapter(null, null)
        view.confirmChoiceRecyclerView.adapter = adapter
        view.confirmChoiceRecyclerView.linearManager()
        return view
    }

    override fun setupMVP() {
        graduationConfirmChoicePresenter = GraduationConfirmChoicePresenter()
    }
    override fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        confirmChoiceRecyclerView.adapter = adapter
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}