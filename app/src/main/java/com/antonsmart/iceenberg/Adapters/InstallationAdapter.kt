package com.antonsmart.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonsmart.iceenberg.Objects.Installation
import com.antonsmart.iceenberg.R

class InstallationAdapter(private val installations: List<Installation>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<InstallationAdapter.InstallationViewHolder>() {

    inner class InstallationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreInstallationTextView)
    }

    interface OnItemClickListener {
        fun onItemClick(installation: Installation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_installation, parent, false)

        return InstallationViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return installations.size
    }

    override fun onBindViewHolder(holder: InstallationViewHolder, position: Int) {
        val currentInstallation = installations[position]
        holder.nombreTextView.text = currentInstallation.name

        holder.itemView.tag = currentInstallation

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentInstallation)
        }
    }

}