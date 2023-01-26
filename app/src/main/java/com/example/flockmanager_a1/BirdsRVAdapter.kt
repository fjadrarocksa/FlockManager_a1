package com.example.flockmanager_a1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flockmanager_a1.db.Note


class BirdsRVAdapter : ListAdapter<Note, BirdsRVAdapter.BirdHolder>(DiffCallback()) {

    class BirdHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var listener: RecyclerClickListener
    fun setItemListener(listener: RecyclerClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
        val birdHolder = BirdHolder(v)

        val noteDelete = birdHolder.itemView.findViewById<ImageView>(R.id.note_delete)
        noteDelete.setOnClickListener {
            listener.onItemRemoveClick(birdHolder.adapterPosition)
        }

        val note = birdHolder.itemView.findViewById<CardView>(R.id.note)
        note.setOnClickListener {
            listener.onItemClick(birdHolder.adapterPosition)
        }

        return birdHolder
    }

    override fun onBindViewHolder(holder: BirdHolder, position: Int) {
        val currentItem = getItem(position)
        val noteText = holder.itemView.findViewById<TextView>(R.id.note_text)
        noteText.text = currentItem.noteText
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.dateAdded == newItem.dateAdded

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }
}