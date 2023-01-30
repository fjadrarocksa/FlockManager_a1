package com.example.flockmanager_a1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flockmanager_a1.db.Flock

class FlockListAdaptor  internal constructor(
    context: Context
) : RecyclerView.Adapter<FlockListAdaptor.FlockViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var flocks = emptyList<Flock>() // Cached copy of words

    inner class FlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flockItemView: TextView = itemView.findViewById(R.id.flock_name_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlockViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FlockViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FlockViewHolder, position: Int) {
        val current = flocks[position]
        holder.flockItemView.text = current.flockName
    }

    internal fun setChickens(flocks: List<Flock>) {
        this.flocks = flocks
        notifyDataSetChanged()
    }

    override fun getItemCount() = flocks.size
}

