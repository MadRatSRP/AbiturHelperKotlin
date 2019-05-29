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
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.*
import kotlinx.android.synthetic.main.fragment_graduation_confirm_choice.view.*

class GraduationConfirmChoice: Fragment() {
    private var adapter: SpecialtiesAdapter? = null
    val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedSpecialties = myApplication.returnSelectedSpecialties()
        selectedSpecialties?.let { showSelectedSpecialties(it) }

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

        adapter = SpecialtiesAdapter(null)
        view.confirmChoiceRecyclerView.adapter = adapter
        view.confirmChoiceRecyclerView.linearManager()

        return view
    }

    /*override*/fun showSelectedSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        confirmChoiceRecyclerView.adapter = adapter
    }
    /*override*/ fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}