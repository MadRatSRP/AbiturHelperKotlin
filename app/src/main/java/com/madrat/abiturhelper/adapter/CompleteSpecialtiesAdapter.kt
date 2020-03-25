package com.madrat.abiturhelper.adapter

import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.databinding.ListCompleteSpecialtiesBinding
import com.madrat.abiturhelper.model.Specialty
import com.madrat.abiturhelper.util.inflate

class CompleteSpecialtiesAdapter(itemStateArray: SparseBooleanArray?,
                                 chosenSpecialties: ArrayList<Specialty>?)
    : RecyclerView.Adapter<CompleteSpecialtiesAdapter.CompleteSpecialtiesHolder>(){
    private var specialties = ArrayList<Specialty>()

    private var selectedSpecialties = ArrayList<Specialty>()
    private var itemStateArray: SparseBooleanArray = SparseBooleanArray()

    init {
        chosenSpecialties?.let {
            this.selectedSpecialties = chosenSpecialties
        }
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
        holder.bind(specialties[position], position)
    }

    override fun getItemCount(): Int
            = specialties.size

    inner class CompleteSpecialtiesHolder (holderView: View): RecyclerView.ViewHolder(holderView) {
        private val binding = ListCompleteSpecialtiesBinding.bind(holderView)

        fun bind(specialty: Specialty, position: Int) {
            with(binding) {
                specialtyName.text = specialty.shortName
                specialtyEntriesTotalValue.text = specialty.entriesTotal.toString()
                specialtyEntriesFreeValue.text = specialty.entriesFree.toString()
                specialtyAmountOfStatementsValue.text = specialty.amountOfStatements.toString()
                specialtyMinimalScoreText.text = specialty.scoreTitle
                specialtyMinimalScoreValue.text = specialty.minimalScore.toString()
            }

            /*completeCheckbox.isChecked = false

            completeCheckbox.isChecked = itemStateArray.get(position, false)*/

            binding.completeCheckbox.isChecked = itemStateArray.get(position, false)

            binding.completeCheckbox.setOnClickListener {
                val adapterPosition = this.adapterPosition

                if (!itemStateArray.get(adapterPosition, false)) {
                    binding.completeCheckbox.isChecked = true
                    itemStateArray.put(adapterPosition, true)
                    selectedSpecialties.add(specialties[adapterPosition])
                }
                else  {
                    binding.completeCheckbox.isChecked = false
                    itemStateArray.put(adapterPosition, false)
                    selectedSpecialties.remove(specialties[adapterPosition])
                }
            }
        }
    }
}