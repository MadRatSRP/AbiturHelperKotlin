package com.madrat.abiturhelper.presenters.fragments.chance

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.interfaces.fragments.chance.ChanceChooseSpecialtiesMVP
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ChanceChooseSpecialtiesPresenter
    : ChanceChooseSpecialtiesMVP.Presenter {
    private var myApplication = MyApplication.instance

    override fun saveChosenSpecialties(listOfChosenSpecialties: ArrayList<Specialty>)
            = myApplication.saveChosenSpecialties(listOfChosenSpecialties)
    override fun saveChanceItemStateArray(chanceItemStateArray: SparseBooleanArray)
            = myApplication.saveChanceItemStateArray(chanceItemStateArray)

    override fun returnCompleteListOfSpecialties()
            = myApplication.returnCompleteListOfSpecilaties()
    override fun returnChanceItemStateArray()
            = myApplication.returnChanceItemStateArray()
    override fun returnChosenSpecialties()
            = myApplication.returnChosenSpecialties()
}