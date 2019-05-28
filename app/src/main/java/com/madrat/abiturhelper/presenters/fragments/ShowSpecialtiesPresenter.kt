package com.madrat.abiturhelper.presenters.fragments

import android.os.Bundle
import com.madrat.abiturhelper.adapter.SpecialtiesAdapter
import com.madrat.abiturhelper.interfaces.fragments.ShowSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.MyApplication

class ShowSpecialtiesPresenter: ShowSpecialtiesMVP.Presenter {
    private val myApplication = MyApplication.instance

    override fun initializeAdapter(example: (Specialty, Int) -> Unit): SpecialtiesAdapter {
        return SpecialtiesAdapter{specialty: Specialty, position: Int -> example(specialty, position)}
    }
    override fun getSpecialtiesListByPosition(pos: Int): ArrayList<Specialty>? {
        val faculties = myApplication.returnFaculties()

        return when (pos) {
            //УНТИ
            0 -> faculties?.listUNTI
            //ФЭУ
            1 -> faculties?.listFEU
            //ФИТ
            2 -> faculties?.listFIT
            //МТФ
            3 -> faculties?.listMTF
            //УНИТ
            4 -> faculties?.listUNIT
            //ФЭЭ
            5 -> faculties?.listFEE
            else -> null
        }
    }

    override fun saveCurrentListOfStudents(list: ArrayList<Student>) {
        myApplication.saveCurrentListOfStudents(list)
    }
    override fun returnSpecialtyBundle(list: ArrayList<Student>, specialty: Specialty): Bundle {
        val bundle = Bundle()

        saveCurrentListOfStudents(list)
        bundle.putString("title", specialty.shortName)

        return bundle
    }
    override fun returnUNTI(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNTI()
    override fun returnFEU(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEU()
    override fun returnFIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFIT()
    override fun returnMTF(): ArrayList<ArrayList<Student>>?
            = myApplication.returnMTF()
    override fun returnUNIT(): ArrayList<ArrayList<Student>>?
            = myApplication.returnUNIT()
    override fun returnFEE(): ArrayList<ArrayList<Student>>?
            = myApplication.returnFEE()
}