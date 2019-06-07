package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Chance
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_chances.*

class ChancesAdapter()
    : RecyclerView.Adapter<ChancesAdapter.ChancesHolder>(){

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

    inner class ChancesHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(chance: Chance) {
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