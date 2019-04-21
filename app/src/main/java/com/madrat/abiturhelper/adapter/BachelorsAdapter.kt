package com.madrat.abiturhelper.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madrat.abiturhelper.R
import com.madrat.abiturhelper.model.Student
import com.madrat.abiturhelper.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_bachelors.*

class BachelorsAdapter
    : RecyclerView.Adapter<BachelorsAdapter.BachelorsHolder>(){
    private var bachelors = ArrayList<Student>()

    fun updateBachelorsList(new_bachelors: ArrayList<Student>) {
        bachelors.clear()
        bachelors.addAll(new_bachelors)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BachelorsHolder
            = BachelorsHolder(parent.inflate(R.layout.list_bachelors))

    override fun onBindViewHolder(holder: BachelorsHolder, position: Int)
            = holder.bind(bachelors[position])

    override fun getItemCount(): Int
            = bachelors.size

    inner class BachelorsHolder internal constructor(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(student: Student) {
            /*facultyName.text = faculty.name
            entriesTotalValue.text = faculty.entriesTotalAmount.toString()
            entriesFreeValue.text = faculty.entriesFreeAmount.toString()*/
            bachelorNameValue.text = student.firstName
            bachelorMathsValue.text = student.maths.toString()
        }
    }
}