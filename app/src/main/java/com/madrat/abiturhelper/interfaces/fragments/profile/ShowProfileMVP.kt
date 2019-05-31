package com.madrat.abiturhelper.interfaces.fragments.profile

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.madrat.abiturhelper.model.FullName
import com.madrat.abiturhelper.model.Score
import com.madrat.abiturhelper.model.Specialty

interface ShowProfileMVP {
    interface View {
        fun setupMVP()
        fun setupScoreFields()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
        fun setupSpecialtiesFields()
    }

    interface Presenter {
        fun updateScores(maths: String, russian: String, physics: String, computerScience: String,
                         socialScience: String, additionalScore: String)

        fun setFieldEditable(editField: EditText, imageButton: ImageButton)
        fun setFieldNonEditable(editField: EditText, imageButton: ImageButton)
        fun checkFieldForBeingEditable(boolean: Boolean?, editField: EditText,
                                       imageButton: ImageButton): Boolean


        fun returnScore(): Score?
        fun returnBundleWithListID(listId: Int): Bundle
        fun checkTextForBeingEmpty(text: String?): Int
        fun checkIntForBeingEmpty(value: Int?): Int
        fun returnCheckedScore(): Score
        fun returnAmountOfFinalSpecialties(): Int?

        fun returnSelectedSpecialties(): ArrayList<Specialty>?
        fun returnFullName(): FullName?
    }
}