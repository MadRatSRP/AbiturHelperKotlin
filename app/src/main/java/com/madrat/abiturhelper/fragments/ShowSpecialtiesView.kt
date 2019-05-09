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

    private fun onUNTISpecialtyClicked(specialty: Specialty, position: Int) {
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
    private fun onFEUSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val feu = myApplication.returnFeu()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }

        feu?.let {
            when (position) {
                // БИ
                0 -> moveToSpecialty(it.bi.zaochnPlat)
                1 -> moveToSpecialty(it.bi.ochnPlat)
                // ПИ
                2 -> moveToSpecialty(it.pi.kisOchnBudg)
                3 -> moveToSpecialty(it.pi.kisOchnLgot)
                4 -> moveToSpecialty(it.pi.kisOchnPlat)
                5 -> moveToSpecialty(it.pi.ceOchnBudg)
                6 -> moveToSpecialty(it.pi.ceOchnLgot)
                7 -> moveToSpecialty(it.pi.ceOchnPlat)
                // СЦ
                8 -> moveToSpecialty(it.sc.zaochnPlat)
                9 -> moveToSpecialty(it.sc.ochnPlat)
                // ТД
                10 -> moveToSpecialty(it.td.zaochnPlat)
                11 -> moveToSpecialty(it.td.ochnPlat)
                // ЕБ
                12 -> moveToSpecialty(it.eb.zaochnPlat)
                13 -> moveToSpecialty(it.eb.ochnPlat)
                // ЕК
                14 -> moveToSpecialty(it.ek.buaZaochnPlat)
                15 -> moveToSpecialty(it.ek.buaOchnPlat)
                16 -> moveToSpecialty(it.ek.logOchnPlat)
                17 -> moveToSpecialty(it.ek.ocOchnPlat)
                18 -> moveToSpecialty(it.ek.fZaochnPlat)
                19 -> moveToSpecialty(it.ek.fOchnPlat)
                20 -> moveToSpecialty(it.ek.epoOchnPlat)
            }
        }
    }
    private fun onFITSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val fit = myApplication.returnFIT()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }

        fit?.let {
            when (position) {
                // ИАСБ
                0 -> moveToSpecialty(it.iasb.ochnBudg)
                1 -> moveToSpecialty(it.iasb.ochnLgot)
                2 -> moveToSpecialty(it.iasb.ochnPlat)
                // ИБ
                3 -> moveToSpecialty(it.ib.vechPlat)
                4 -> moveToSpecialty(it.ib.ochnBudg)
                5 -> moveToSpecialty(it.ib.ochnLgot)
                6 -> moveToSpecialty(it.ib.ochnPlat)
                // ИБАС
                7 -> moveToSpecialty(it.ibas.ochnBudg)
                8 -> moveToSpecialty(it.ibas.ochnLgot)
                9 -> moveToSpecialty(it.ibas.ochnPlat)
                // ИВТ
                10 -> moveToSpecialty(it.ivt.poZaochnPlat)
                11 -> moveToSpecialty(it.ivt.poOchnBudg)
                12 -> moveToSpecialty(it.ivt.poOchnLgot)
                13 -> moveToSpecialty(it.ivt.poOchnPlat)
                14 -> moveToSpecialty(it.ivt.poOchnCelevoe)
                15 -> moveToSpecialty(it.ivt.saprOchnBudg)
                16 -> moveToSpecialty(it.ivt.saprOchnLgot)
                17 -> moveToSpecialty(it.ivt.saprOchnPlat)
                // ИНН
                18 -> moveToSpecialty(it.inn.zaochnPlat)
                19 -> moveToSpecialty(it.inn.ochnBudg)
                20 -> moveToSpecialty(it.inn.ochnLgot)
                21 -> moveToSpecialty(it.inn.ochnPlat)
                // ИСТ
                22 -> moveToSpecialty(it.ist.isitdOchnBudg)
                23 -> moveToSpecialty(it.ist.isitdOchnLgot)
                24 -> moveToSpecialty(it.ist.isitdOchnPlat)
                25 -> moveToSpecialty(it.ist.itipkOchnBudg)
                26 -> moveToSpecialty(it.ist.itipkOchnLgot)
                27 -> moveToSpecialty(it.ist.itipkOchnPlat)
                28 -> moveToSpecialty(it.ist.zaochnPlat)
                // МОА
                29 -> moveToSpecialty(it.moa.ochnBudg)
                30 -> moveToSpecialty(it.moa.ochnLgot)
                31 -> moveToSpecialty(it.moa.ochnPlat)
                32 -> moveToSpecialty(it.moa.ochnCelevoe)
                // ПРИ
                33 -> moveToSpecialty(it.pri.ochnBudg)
                34 -> moveToSpecialty(it.pri.ochnLgot)
                35 -> moveToSpecialty(it.pri.ochnPlat)
                36 -> moveToSpecialty(it.pri.ochnCelevoe)
                // ПРО
                37 -> moveToSpecialty(it.pro.gdOchnBudg)
                38 -> moveToSpecialty(it.pro.gdOchnLgot)
                39 -> moveToSpecialty(it.pro.gdOchnPlat)
                40 -> moveToSpecialty(it.pro.ivtZaochnPlat)
                41 -> moveToSpecialty(it.pro.ekZaochnPlat)
            }
        }
    }
    private fun onMTFSpecialtyClicked(specialty: Specialty, position: Int) {
        showLog("Выбрана: ${specialty.shortName}")
        val bundle = Bundle()
        val mtf = myApplication.returnMTF()

        fun moveToSpecialty(list: ArrayList<Student>) {
            bundle.stringAndSerializable(specialty, list)
            toSpecialty(bundle)
        }

        mtf?.let {
            when (position) {
                // МАШ
                0 -> moveToSpecialty(it.mash.lZaochnBudg)
                1 -> moveToSpecialty(it.mash.lZaochnLgot)
                2 -> moveToSpecialty(it.mash.lZaochnPlat)
                3 -> moveToSpecialty(it.mash.lOchnBudg)
                4 -> moveToSpecialty(it.mash.lOchnLgot)
                5 -> moveToSpecialty(it.mash.lOchnPlat)
                6 -> moveToSpecialty(it.mash.sZaochnBudg)
                7 -> moveToSpecialty(it.mash.sZaochnLgot)
                8 -> moveToSpecialty(it.mash.sZaochnPlat)
                9 -> moveToSpecialty(it.mash.sOchnBudg)
                10 -> moveToSpecialty(it.mash.sOchnLgot)
                11 -> moveToSpecialty(it.mash.sOchnPlat)
                12 -> moveToSpecialty(it.mash.sOchnCelevoe)
                // СИМ
                13 -> moveToSpecialty(it.sim.zaochnPlat)
                14 -> moveToSpecialty(it.sim.ochnBudg)
                15 -> moveToSpecialty(it.sim.ochnLgot)
                16 -> moveToSpecialty(it.sim.ochnPlat)
                // ТБ
                17 -> moveToSpecialty(it.tb.btpipZaochnPlat)
                18 -> moveToSpecialty(it.tb.btpipOchnBudg)
                19 -> moveToSpecialty(it.tb.btpipOchnLgot)
                20 -> moveToSpecialty(it.tb.btpipOchnPlat)
                // УК
                21 -> moveToSpecialty(it.uk.zaochnPlat)
            }
        }
    }
    fun onUNITSpecialtyClicked(specialty: Specialty, position: Int) {}
    fun onFEESpecialtyClicked(specialty: Specialty, position: Int) {}

    override fun toSpecialty(bundle: Bundle) {
        view?.let {
            Navigation.findNavController(it)
                    .navigate(R.id.action_showSpecialtiesView_to_showBachelors, bundle)
        }
    }
}