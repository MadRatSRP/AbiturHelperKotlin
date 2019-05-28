package com.madrat.abiturhelper.adapter

import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
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

class CompleteSpecialtiesAdapter(itemStateArray: SparseBooleanArray?)
    : RecyclerView.Adapter<CompleteSpecialtiesAdapter.CompleteSpecialtiesHolder>(){

    private var selectedSpecialties = ArrayList<Specialty>()
    private var specialties = ArrayList<Specialty>()

    private var itemStateArray: SparseBooleanArray = SparseBooleanArray()

    init {
        itemStateArray?.let {
            this.itemStateArray = itemStateArray
        }
    }

    fun updateSpecialtiesList(new_specialties: ArrayList<Specialty>) {
        specialties.clear()
        specialties.addAll(new_specialties)
        this.notifyDataSetChanged()
    }
    fun returnSelectedSpecialties()
            = selectedSpecialties
    fun returnItemStateArray()
            = itemStateArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteSpecialtiesHolder
            = CompleteSpecialtiesHolder(parent.inflate(com.madrat.abiturhelper.R.layout.list_complete_specialties))

    override fun onBindViewHolder(holder: CompleteSpecialtiesHolder, position: Int){
        val selectedSpecialty = specialties[position]

        holder.bind(selectedSpecialty, position)
        /*holder.setOnClickListener(View.OnClickListener {
            val adapterPosition = holder.adapterPosition
            if (!itemStateArray.get(adapterPosition, false)) {
                mCheckedTextView.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            }
            else  {
                mCheckedTextView.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }

            /*if (itemStateArray.get(position, true)) {
                holder.completeCheckbox.isChecked = true
                itemStateArray.put(position, true)
                selectedSpecialties.add(specialties[position])
            }
            else {
                holder.completeCheckbox.isChecked = false
                itemStateArray.put(position, false)
                selectedSpecialties.remove(specialties[position])
            }*/
        })*/

        holder.containerView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (!itemStateArray.get(adapterPosition, false)) {
                holder.completeCheckbox.isChecked = true
                itemStateArray.put(adapterPosition, true)
                selectedSpecialties.add(specialties[adapterPosition])
            }
            else  {
                holder.completeCheckbox.isChecked = false
                itemStateArray.put(adapterPosition, false)
                selectedSpecialties.remove(specialties[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int
            = specialties.size

    inner class CompleteSpecialtiesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(specialty: Specialty, position: Int) {
            specialtyName.text = specialty.shortName
            specialtyEntriesTotalValue.text = specialty.entriesTotal.toString()
            specialtyEntriesFreeValue.text = specialty.entriesFree.toString()
            specialtyAmountOfStatementsValue.text = specialty.amountOfStatements.toString()
            specialtyMinimalScoreText.text = specialty.scoreTitle
            specialtyMinimalScoreValue.text = specialty.minimalScore.toString()

            /*completeCheckbox.isChecked = false

            completeCheckbox.isChecked = itemStateArray.get(position, false)*/

            completeCheckbox.isChecked = itemStateArray.get(position, false)
        }
        /*fun setOnClickListener(onClickListener: View.OnClickListener) {
            //containerView.setOnClickListener { onClickListener }
            containerView.setOnClickListener(onClickListener)
        }*/
    }
}