package com.example.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Objects.Installation
import com.example.iceenberg.Objects.Maintenance
import com.example.iceenberg.R


class MaintenanceAdapter(private val maintenances: MutableList<Maintenance>, private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<MaintenanceAdapter.MaintenanceViewHolder>(){

    inner class MaintenanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreMaintenanceTextView)
    }

    interface OnItemClickListener{
        fun onItemClick(maintenance: Maintenance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintenanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_maintenance,parent,false)

        return MaintenanceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return maintenances.size
    }

    fun updateList(newList: List<Maintenance>) {
        maintenances.clear()
        maintenances.addAll(newList)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MaintenanceViewHolder, position: Int) {
        val currentMaintenance = maintenances[position]
        holder.nombreTextView.text = currentMaintenance.name

        holder.itemView.tag = currentMaintenance

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(currentMaintenance)
        }
    }
}