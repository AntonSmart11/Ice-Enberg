package com.example.iceenberg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Objects.Revision
import com.example.iceenberg.R

class RevisionAdapter(private val revisions: MutableList<Revision>, private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<RevisionAdapter.RevisionViewHolder>(){

        inner class RevisionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombreTextView: TextView = itemView.findViewById(R.id.nombreRevisionTextView)
        }

    interface OnItemClickListener{
        fun onItemClick(revision: Revision)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevisionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_revision,parent,false)

        return RevisionViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return revisions.size
    }

    fun updateList(newList: List<Revision>){
        revisions.clear()
        revisions.addAll(newList)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: RevisionViewHolder, position: Int) {
        val currentRevision = revisions[position]
        holder.nombreTextView.text = currentRevision.name

        holder.itemView.tag = currentRevision

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(currentRevision)
        }
    }

    }