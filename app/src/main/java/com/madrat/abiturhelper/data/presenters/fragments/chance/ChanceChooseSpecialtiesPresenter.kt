package com.madrat.abiturhelper.data.presenters.fragments.chance

import android.util.SparseBooleanArray
import com.madrat.abiturhelper.data.interfaces.fragments.chance.ChanceChooseSpecialtiesMVP
import com.madrat.abiturhelper.data.model.Specialty
import com.madrat.abiturhelper.util.MyApplication

class ChanceChooseSpecialtiesPresenter
    : ChanceChooseSpecialtiesMVP.Presenter {
    private var myApplication = MyApplication.instance

    override fun saveChosenSpecialties(listOfChosenSpecialties: ArrayList<Specialty>)
            = myApplication.saveChosenSpecialties(listOfChosenSpecialties)
    override fun saveChanceItemStateArray(chanceItemStateArray: SparseBooleanArray)
            = myApplication.saveChanceItemStateArray(chanceItemStateArray)

    override fun returnListOfAllCompleteSpecialties()
            = myApplication.returnListOfAllCompleteSpecialties()
    override fun returnChanceItemStateArray()
            = myApplication.returnChanceItemStateArray()
    override fun returnChosenSpecialties()
            = myApplication.returnChosenSpecialties()
}