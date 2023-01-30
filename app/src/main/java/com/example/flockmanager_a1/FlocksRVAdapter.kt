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
import com.example.flockmanager_a1.db.Flock


class FlocksRVAdapter : ListAdapter<Flock, FlocksRVAdapter.FlockHolder>(DiffCallback()) {

    class FlockHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var listener: RecyclerClickListener
    private var flocks = emptyList<Flock>() // Cached copy of words
    fun setItemListener(listener: RecyclerClickListener) {
        this.listener = listener
    }
/*
    internal fun setFlocks(flocks: List<Flock>) {
        this.flocks = flocks
        notifyDataSetChanged()
    }
 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlockHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.flocks_row, parent, false)
        val flockHolder = FlockHolder(v)

        val flockDelete = flockHolder.itemView.findViewById<ImageView>(R.id.flock_delete)
        flockDelete.setOnClickListener {
            listener.onItemRemoveClick(flockHolder.adapterPosition)
        }

        val note = flockHolder.itemView.findViewById<CardView>(R.id.cardview_flock)
        note.setOnClickListener {
            listener.onItemClick(flockHolder.adapterPosition)
        }

        return flockHolder
    }

    override fun onBindViewHolder(holder: FlockHolder, position: Int) {
        val currentItem = getItem(position)
        val flockName = holder.itemView.findViewById<TextView>(R.id.textview_flock)
        flockName.text = currentItem.flockName
    }

    override fun getItemCount() = flocks.size
    class DiffCallback : DiffUtil.ItemCallback<Flock>() {
        override fun areItemsTheSame(oldItem: Flock, newItem: Flock) =
            oldItem.dateAdded == newItem.dateAdded

        override fun areContentsTheSame(oldItem: Flock, newItem: Flock) =
            oldItem == newItem
    }
}