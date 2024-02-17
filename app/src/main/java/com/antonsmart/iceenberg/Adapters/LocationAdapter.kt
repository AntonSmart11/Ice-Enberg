package com.antonsmart.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonsmart.iceenberg.Objects.Location
import com.antonsmart.iceenberg.R



class LocationAdapter(private val locations: List<Location>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreLocationTextView)
    }

    interface OnItemClickListener {
        fun onItemClick(location: Location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_basic, parent, false)

        return LocationViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentLocation = locations[position]
        holder.nombreTextView.text = currentLocation.name

        holder.itemView.tag = currentLocation

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentLocation)
        }
    }
}