package com.madrat.abiturhelper.fragments.chance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_chance_confirm_choice.*
import kotlinx.android.synthetic.main.fragment_chance_confirm_choice.view.*

class ChanceConfirmChoice: Fragment() {
    private var adapter: SpecialtiesAdapter? = null
    private var myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Получаем выбранный пользователем список специальностей для проверки
        // и показываем его
        val chosenSpecialties = myApplication.returnChosenSpecialties()
        chosenSpecialties?.let { showSelectedSpecialties(it) }

        /*confirmChoiceShowGraduationList.setOnClickListener {
            toSpecialties(null, R.id.action_confirm_choice_to_show_current_list)
        }*/
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.chanceConfirmChoiceTitle)
        val view = inflater.inflate(R.layout.fragment_chance_confirm_choice,
                container, false)

        adapter = SpecialtiesAdapter(null)
        view.chanceConfirmChoiceRecyclerView.adapter = adapter
        view.chanceConfirmChoiceRecyclerView.linearManager()
        return view
    }

    /*override*/ fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        chanceConfirmChoiceRecyclerView.adapter = adapter
    }
}