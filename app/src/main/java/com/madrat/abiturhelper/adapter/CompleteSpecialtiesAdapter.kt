package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_complete_specialties.*
import kotlinx.android.synthetic.main.list_specialties.specialtyAmountOfStatementsValue
import kotlinx.android.synthetic.main.list_specialties.specialtyEntriesFreeValue
import kotlinx.android.synthetic.main.list_specialties.specialtyEntriesTotalValue
import kotlinx.android.synthetic.main.list_specialties.specialtyMinimalScoreText
import kotlinx.android.synthetic.main.list_specialties.specialtyMinimalScoreValue
import kotlinx.android.synthetic.main.list_specialties.specialtyName

class CompleteSpecialtiesAdapter(@NonNull onItemCheckListener: OnItemCheckListener?)
    : RecyclerView.Adapter<CompleteSpecialtiesAdapter.CompleteSpecialtiesHolder>(){
    @NonNull
    private var onItemCheckListener: OnItemCheckListener? = null
    private var specialties = ArrayList<Specialty>()

    interface OnItemCheckListener {
        fun onItemCheck(specialty: Specialty)
        fun onItemUncheck(specialty: Specialty)
    }
    init {
        this.onItemCheckListener = onItemCheckListener
    }

    fun updateSpecialtiesList(new_specialties: ArrayList<Specialty>) {
        specialties.clear()
        specialties.addAll(new_specialties)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteSpecialtiesHolder
            = CompleteSpecialtiesHolder(parent.inflate(com.madrat.abiturhelper.R.layout.list_complete_specialties))

    override fun onBindViewHolder(holder: CompleteSpecialtiesHolder, position: Int){
        val currentSpecialty = specialties[position]

        holder.bind(specialties[position])
        holder.setOnClickListener(View.OnClickListener {
            holder.completeCheckbox.isChecked = !holder.completeCheckbox.isChecked
            if (holder.completeCheckbox.isChecked) {
                onItemCheckListener?.onItemCheck(currentSpecialty)
            } else {
                onItemCheckListener?.onItemUncheck(currentSpecialty)
            }
        })
    }


    override fun getItemCount(): Int
            = specialties.size

    inner class CompleteSpecialtiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(specialty: Specialty) {

            specialtyName.text = specialty.shortName
            specialtyEntriesTotalValue.text = specialty.entriesTotal.toString()
            specialtyEntriesFreeValue.text = specialty.entriesFree.toString()
            specialtyAmountOfStatementsValue.text = specialty.amountOfStatements.toString()
            specialtyMinimalScoreText.text = specialty.scoreTitle
            specialtyMinimalScoreValue.text = specialty.minimalScore.toString()

            completeCheckbox.isClickable = false
        }

        fun setOnClickListener(onClickListener: View.OnClickListener) {
            //containerView.setOnClickListener { onClickListener }
            containerView.setOnClickListener(onClickListener)
        }
    }
}