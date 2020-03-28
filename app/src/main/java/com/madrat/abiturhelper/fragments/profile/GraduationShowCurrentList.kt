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
import com.madrat.abiturhelper.databinding.FragmentGraduationShowCurrentListBinding
import com.madrat.abiturhelper.interfaces.fragments.profile.GraduationShowCurrentListMVP
import com.madrat.abiturhelper.model.Graduation
import com.madrat.abiturhelper.presenters.fragments.profile.GraduationShowCurrentListPresenter
import com.madrat.abiturhelper.util.linearManager

class GraduationShowCurrentList: Fragment(), GraduationShowCurrentListMVP.View {
    private var adapter: SelectedSpecialtiesAdapter? = null
    private var presenter: GraduationShowCurrentListPresenter? = null

    private var mBinding: FragmentGraduationShowCurrentListBinding? = null
    private val binding get() = mBinding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupMVP()

        val graduationList = presenter?.returnGraduationList()
        graduationList?.let { showGraduation(it) }

        binding.showCurrentToProfile.setOnClickListener {
            toSpecialties(null, R.id.action_show_current_list_to_profile)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar
                ?.setTitle(R.string.profileGraduationShowCurrentListTitle)

        // Инициализируем mBinding
        mBinding = FragmentGraduationShowCurrentListBinding.inflate(inflater,
                container, false)
        val view = binding.root

        adapter = SelectedSpecialtiesAdapter()
        binding.showCurrentRecyclerView.adapter = adapter
        binding.showCurrentRecyclerView.linearManager()

        return view
    }
    override fun onDestroyView() {
        presenter = null
        adapter = null
        super.onDestroyView()
    }

    override fun setupMVP() {
        presenter = GraduationShowCurrentListPresenter()
    }
    override fun showGraduation(graduationList: ArrayList<Graduation>) {
        adapter?.updateGraduationList(graduationList)
        binding.showCurrentRecyclerView.adapter = adapter
    }
    override fun toSpecialties(bundle: Bundle?, actionId: Int) {
        view?.let { Navigation.findNavController(it).navigate(actionId, bundle) }
    }
}