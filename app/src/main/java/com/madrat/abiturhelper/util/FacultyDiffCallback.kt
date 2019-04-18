package com.madrat.abiturhelper.util

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.madrat.abiturhelper.model.Faculty


class EmployeeDiffCallback(private val oldFaculties: ArrayList<Faculty>,
                           private val newFaculties: ArrayList<Faculty>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldFaculties.size

    override fun getNewListSize(): Int = newFaculties.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFaculties[oldItemPosition].name === newFaculties[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldFaculties[oldItemPosition].entriesFreeAmount == newFaculties[newItemPosition].entriesFreeAmount

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}