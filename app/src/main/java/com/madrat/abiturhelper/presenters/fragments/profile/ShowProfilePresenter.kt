package com.madrat.abiturhelper.presenters.fragments.profile

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.interfaces.fragments.profile.ShowProfileMVP
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.util.MyApplication

class ShowProfilePresenter(private var pv: ShowProfileMVP.View) : ShowProfileMVP.Presenter {
    var myApplication = MyApplication.instance

    override fun setFieldEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = true
        editField.requestFocus()
        editField.text.clear()
        imageButton.setImageResource(R.drawable.ic_save)
    }
    override fun setFieldNonEditable(editField: EditText, imageButton: ImageButton) {
        editField.isEnabled = false
        imageButton.setImageResource(R.drawable.ic_edit)
    }
    override fun checkFieldForBeingEditable(boolean: Boolean?, editField: EditText,
                                            imageButton: ImageButton): Boolean {
        return when(boolean) {
            true -> {
                setFieldEditable(editField, imageButton)
                false
            }
            false -> {
                setFieldNonEditable(editField, imageButton)
                true
            }
            null -> true
        }
    }
    override fun updateScores(maths: String, russian: String, physics: String, computerScience: String,
                              socialScience: String, additionalScore: String) {
        val checkedMaths = checkTextForBeingEmpty(maths)
        val checkedRussian = checkTextForBeingEmpty(russian)
        val checkedPhysics = checkTextForBeingEmpty(physics)
        val checkedComputerScience = checkTextForBeingEmpty(computerScience)
        val checkedSocialScience = checkTextForBeingEmpty(socialScience)
        val checkedAdditionalScore = checkTextForBeingEmpty(additionalScore)

        val score = Score(checkedMaths, checkedRussian, checkedPhysics,
                checkedComputerScience, checkedSocialScience, checkedAdditionalScore)
        myApplication.saveScore(score)
    }
    override fun checkTextForBeingEmpty(text: String?): Int {
        return if (text == null || text == "") {
            0
        } else text.toInt()
    }
    override fun checkIntForBeingEmpty(value: Int?): Int
            = value ?: 0

    override fun returnAmountOfFinalSpecialties(): Int? {
        val specialties = myApplication.returnCompleteListOfSpecilaties()
        return specialties?.sumBy { it.size }
    }

    override fun returnFullName() = myApplication.returnFullName()
    override fun returnScore() = myApplication.returnScore()
    override fun returnCheckedScore(): Score {
        val score = returnScore()

        val checkedMaths = checkIntForBeingEmpty(score?.maths)
        val checkedRussian = checkIntForBeingEmpty(score?.russian)
        val checkedPhysics = checkIntForBeingEmpty(score?.physics)
        val checkedComputerScience = checkIntForBeingEmpty(score?.computerScience)
        val checkedSocialScience = checkIntForBeingEmpty(score?.socialScience)
        val checkedAdditionalScore = checkIntForBeingEmpty(score?.additionalScore)

        return Score(checkedMaths, checkedRussian, checkedPhysics,
                checkedComputerScience, checkedSocialScience, checkedAdditionalScore)
    }
    override fun returnBundleWithListID(listId: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt("listId", listId)

        return bundle
    }

    override fun returnSelectedSpecialties()
            = myApplication.returnSelectedSpecialties()
}