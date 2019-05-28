package com.madrat.abiturhelper.interfaces.fragments

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.madrat.abiturhelper.model.Score

interface ProfileMVP {
    interface View {
        fun setupMVP()
        fun setupScoreFields()
        fun toSpecialties(bundle: Bundle?, actionId: Int)
        fun setFieldEditable(editField: EditText, imageButton: ImageButton)
        fun setFieldNonEditable(editField: EditText, imageButton: ImageButton)
        fun checkFieldForBeingEditable(boolean: Boolean, editField: EditText, imageButton: ImageButton): Boolean
    }

    interface Presenter {
        fun updateScores(maths: String, russian: String, physics: String, computerScience: String,
                         socialScience: String, additionalScore: String)

        fun returnScore(): Score?
        fun returnAdditionalScore(): Int?
        fun returnBundleWithListID(listId: Int): Bundle
        fun checkTextForBeingEmpty(text: String?): Int
    }
}