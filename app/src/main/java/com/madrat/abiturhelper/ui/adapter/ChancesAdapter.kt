package com.madrat.abiturhelper.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.databinding.ListChancesBinding
import com.madrat.abiturhelper.data.model.Chance
import com.madrat.abiturhelper.util.inflate

class ChancesAdapter: RecyclerView.Adapter<ChancesAdapter.ChancesHolder>(){
    private var chances = ArrayList<Chance>()

    fun updateListOfChances(new_chances: ArrayList<Chance>) {
        chances.clear()
        chances.addAll(new_chances)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChancesHolder
            = ChancesHolder(parent.inflate(R.layout.list_chances))
    override fun onBindViewHolder(holder: ChancesHolder, position: Int){
        holder.bind(chances[position])
    }
    override fun getItemCount(): Int
            = chances.size

    inner class ChancesHolder(private val holderView: View): RecyclerView.ViewHolder(holderView) {
        private val binding = ListChancesBinding.bind(holderView)

        fun bind(chance: Chance) {
            with(binding) {
                // Название специальности и факультета
                chanceSpecialtyNameValue.text = chance.specialtyName
                chanceFacultyNameValue.text = chance.facultyName
                // Шанс поступления и минимальный балл
                chanceMinimalScoreValue.text = chance.minimalScore.toString()
                chanceChanceValue.text = chance.chance.toString()
                // Количество мест, поступающих и предполагаемая позиция
                chanceTotalOfEntriesValue.text = chance.totalOfEntries.toString()
                chanceAmountOfStudentsValue.text = chance.amountOfStudents.toString()
                chancePositionValue.text = chance.supposedPosition.toString()
            }

        }
    }
}