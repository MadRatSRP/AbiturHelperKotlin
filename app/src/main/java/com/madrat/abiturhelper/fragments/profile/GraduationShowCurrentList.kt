package com.madrat.abiturhelper.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SelectedSpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationShowCurrentListMVP
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationShowCurrentListPresenter
import com.madrat.abiturhelper.util.linearManager
import kotlinx.android.synthetic.main.fragment_graduation_show_current_list.*
import kotlinx.android.synthetic.main.fragment_graduation_show_current_list.view.*

class GraduationShowCurrentList
    : Fragment(), GraduationShowCurrentListMVP.View {
    private var adapter: SelectedSpecialtiesAdapter? = null
    private var graduationShowCurrentListPresenter: GraduationShowCurrentListPresenter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val graduationList = graduationShowCurrentListPresenter?.returnGraduationList()
        graduationList?.let { showGraduation(it) }

        showCurrentToProfile.setOnClickListener {
            toSpecialties(null, R.id.action_show_current_list_to_profile)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.profileGraduationShowCurrentListTitle)
        val view = inflater.inflate(R.layout.fragment_graduation_show_current_list,
                container, false)

        adapter = SelectedSpecialtiesAdapter()
        view.showCurrentRecyclerView.adapter = adapter
        view.showCurrentRecyclerView.linearManager()

        return view
    }
    override fun onDestroyView() {
        graduationShowCurrentListPresenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        graduationShowCurrentListPresenter = GraduationShowCurrentListPresenter()
    }
    override fun showGraduation(graduationList: ArrayList<Graduation>) {
        adapter?.updateGraduationList(graduationList)
        showCurrentRecyclerView.adapter = adapter
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}