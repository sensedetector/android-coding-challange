package com.shiftkey.codingchallenge.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shiftkey.codingchallenge.R
import com.shiftkey.codingchallenge.domain.Shift

class ShiftsAdapter(private val onClick: (Int) -> Unit): RecyclerView.Adapter<ShiftViewHolder>() {

    private var items = emptyList<Shift>()

    fun setShifts(shifts: List<Shift>) {
        val oldSize = items.size
        items = ArrayList(shifts)
        notifyItemRangeInserted(oldSize - 1, shifts.size - oldSize)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        return ShiftViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shift_item, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class ShiftViewHolder(itemView: View, private val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val titleView = itemView.findViewById<TextView>(R.id.title)
    private val startDateView = itemView.findViewById<TextView>(R.id.startDate)
    private val shiftKindView = itemView.findViewById<TextView>(R.id.shiftKind)

    fun bind(shift: Shift) {
        titleView.text = shift.localizedSpecialty.name
        startDateView.text = shift.startTime
        shiftKindView.text = shift.shiftKind

        itemView.setOnClickListener { onClick(shift.shiftId) }
    }
}
