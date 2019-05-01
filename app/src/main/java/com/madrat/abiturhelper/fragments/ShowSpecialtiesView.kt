package com.madrat.abiturhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication
import com.madrat.abiturhelper.util.linearManager
import com.madrat.abiturhelper.util.showLog
import com.madrat.abiturhelper.util.stringAndSerializable
import kotlinx.android.synthetic.main.fragment_specialties.*
import kotlinx.android.synthetic.main.fragment_specialties.view.*

class ShowSpecialtiesView
    : Fragment(), ShowSpecialtiesMVP.View {
    companion object { val instance = ShowSpecialtiesView() }
    private var adapter: SpecialtiesAdapter? = null
    private val myApplication = MyApplication.instance

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")

        (activity as AppCompatActivity).supportActionBar?.title = title
        val view = inflater.inflate(R.layout.fragment_specialties, container, false)

        @Suppress("UNCHECKED_CAST")
        val list = arguments?.getSerializable("array") as? ArrayList<Specialty> ?: return null
        val pos = arguments?.getInt("pos")
        //showLog(pos.toString())

        when (pos) {
            //УНТИ
            0 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onUNTISpecialtyClicked(specialty, position)}
            //ФЭУ
            1 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onFEUSpecialtyClicked(specialty, position)}
            //ФИТ
            2 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onFITSpecialtyClicked(specialty, position)}
            //МТФ
            3 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onMTFSpecialtyClicked(specialty, position)}
            //УНИТ
            4 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onUNITSpecialtyClicked(specialty, position)}
            //ФЭЭ
            5 -> adapter = SpecialtiesAdapter{specialty: Specialty, position: Int -> onFEESpecialtyClicked(specialty, position)}
        }
        view.specialtiesRecyclerView.adapter = adapter

        view.specialtiesRecyclerView.linearManager()

        showSpecialties(list)

        return view
    }

    fun initializeAdapter(): SpecialtiesAdapter {
        return SpecialtiesAdapter{specialty: Specialty, position: Int -> onUNTISpecialtyClicked(specialty, position)}
    }

    override fun showSpecialties(specialties: ArrayList<Specialty>) {
        adapter?.updateSpecialtiesList(specialties)
        specialtiesRecyclerView?.adapter = adapter
    }

    fun onUNTISpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val unti = myApplication.returnUnti()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }

        unti?.let {
            when (position) {
                //АТП
                0 -> {
                    moveToSpecialty(it.specialtiesATP.zaochnBudg)
                    for (i in 0 until 10) {
                        showLog(it.specialtiesATP.zaochnBudg[i].toString())
                    }
                }
                1 -> moveToSpecialty(it.specialtiesATP.zaochnLgot)
                2 -> moveToSpecialty(it.specialtiesATP.zaochnPlat)
                3 -> moveToSpecialty(it.specialtiesATP.ochnBudg)
                4 -> moveToSpecialty(it.specialtiesATP.ochnLgot)
                5 -> moveToSpecialty(it.specialtiesATP.ochnPlat)
                6 -> moveToSpecialty(it.specialtiesATP.ochnCelevoe)
                //КТО
                7 -> moveToSpecialty(it.specialtiesKTO.atkmOchnBudg)
                8 -> moveToSpecialty(it.specialtiesKTO.atkmOchnLgot)
                9 -> moveToSpecialty(it.specialtiesKTO.atkmOchnPlat)
                10 -> moveToSpecialty(it.specialtiesKTO.tmOchnBudg)
                11 -> moveToSpecialty(it.specialtiesKTO.tmOchnLgot)
                12 -> moveToSpecialty(it.specialtiesKTO.tmOchnPlat)
                13 -> moveToSpecialty(it.specialtiesKTO.tmOchnCelevoe)
                14 -> moveToSpecialty(it.specialtiesKTO.vechBudg)
                15 -> moveToSpecialty(it.specialtiesKTO.vechLgot)
                16 -> moveToSpecialty(it.specialtiesKTO.vechPlat)
                //MASH
                17 -> moveToSpecialty(it.specialtiesMASH.tmZaochnBudg)
                18 -> moveToSpecialty(it.specialtiesMASH.tmZaochnLgot)
                19 -> moveToSpecialty(it.specialtiesMASH.tmZaochnPlat)
                //MiTM
                20 -> moveToSpecialty(it.specialtiesMiTM.ochnBudg)
                21 -> moveToSpecialty(it.specialtiesMiTM.ochnLgot)
                22 -> moveToSpecialty(it.specialtiesMiTM.ochnPlat)
                //MHT
                23 -> moveToSpecialty(it.specialtiesMHT.ochnBudg)
                24 -> moveToSpecialty(it.specialtiesMHT.ochnLgot)
                25 -> moveToSpecialty(it.specialtiesMHT.ochnPlat)
                //PTMK
                26 -> moveToSpecialty(it.specialtiesPTMK.zaochnPlat)
                27 -> moveToSpecialty(it.specialtiesPTMK.ochnBudg)
                28 -> moveToSpecialty(it.specialtiesPTMK.ochnLgot)
                29 -> moveToSpecialty(it.specialtiesPTMK.ochnPlat)
                //ТМО
                30 -> {
                    moveToSpecialty(it.specialtiesTMO.oipmZaochnBudg)
                    for (i in 0 until it.specialtiesTMO.oipmZaochnBudg.size) {
                        showLog(it.specialtiesTMO.oipmZaochnBudg[i].toString())
                    }
                }
                31 -> moveToSpecialty(it.specialtiesTMO.oipmZaochnLgot)
                32 -> moveToSpecialty(it.specialtiesTMO.oipmZaochnPlat)
                33 -> moveToSpecialty(it.specialtiesTMO.ochnBudg)
                34 -> moveToSpecialty(it.specialtiesTMO.ochnLgot)
                35 -> moveToSpecialty(it.specialtiesTMO.ochnPlat)
                36 -> moveToSpecialty(it.specialtiesTMO.ochnCelevoe)
                //UTS
                37 -> moveToSpecialty(it.specialtiesUTS.ochnBudg)
                38 -> moveToSpecialty(it.specialtiesUTS.ochnLgot)
                39 -> moveToSpecialty(it.specialtiesUTS.ochnPlat)
                40 -> moveToSpecialty(it.specialtiesUTS.ochnCelevoe)
            }
        }
    }
    fun onFEUSpecialtyClicked(specialty: Specialty, position: Int) {

    }
    fun onFITSpecialtyClicked(specialty: Specialty, position: Int) {}
    fun onMTFSpecialtyClicked(specialty: Specialty, position: Int) {}
    fun onUNITSpecialtyClicked(specialty: Specialty, position: Int) {}
    fun onFEESpecialtyClicked(specialty: Specialty, position: Int) {}

    override fun toSpecialty(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(R.id.action_showSpecialtiesView_to_showBachelors, bundle)
        }
    }
}