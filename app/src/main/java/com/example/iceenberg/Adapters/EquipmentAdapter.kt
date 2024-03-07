package com.example.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Objects.Equipments
import com.example.iceenberg.R

class EquipmentAdapter(private val equipments: MutableList<Equipments>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    inner class EquipmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreLocationTextView)
    }

    interface OnItemClickListener {
        fun onItemClick(equipments: Equipments)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipmentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_basic, parent, false)

        return EquipmentViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

    fun updateList(newList: List<Equipments>) {
        equipments.clear()
        equipments.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val currentEquipment = equipments[position]
        holder.nombreTextView.text = currentEquipment.name

        holder.itemView.tag = currentEquipment

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentEquipment)
        }
    }
}