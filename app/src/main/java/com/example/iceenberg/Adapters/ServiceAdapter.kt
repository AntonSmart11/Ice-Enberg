package com.example.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Objects.Service
import com.example.iceenberg.R
import com.example.iceenberg.user.Servicios.ServiceActivity

class ServiceAdapter(private val services: MutableList<Service>, private val itemClickListener: ServiceActivity)
    : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

        inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val nombreTextView: TextView = itemView.findViewById(R.id.nombreServiceTextView)
        }

    interface OnItemClickListener {
        fun onItemClick(services: Service)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)

        return ServiceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    fun updateList( newList: List<Service>) {
        services.clear()
        services.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentService = services[position]
        holder.nombreTextView.text = currentService.type_service

        holder.itemView.tag = currentService

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentService)
        }
    }
 }