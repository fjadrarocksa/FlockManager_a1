package com.example.flockmanager_a1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flockmanager_a1.db.Chicken

class ChickenListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ChickenListAdapter.ChickenViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var chickens = emptyList<Chicken>() // Cached copy of words

    inner class ChickenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chickenItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChickenViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ChickenViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChickenViewHolder, position: Int) {
        val current = chickens[position]
        holder.chickenItemView.text = current.birdType
    }

    internal fun setChickens(chickens: List<Chicken>) {
        this.chickens = chickens
        notifyDataSetChanged()
    }

    override fun getItemCount() = chickens.size
}
/*
public class ChickenListAdapter
    : ListAdapter<Chicken, ChickenListAdapter.ChickenViewHolder>(ChickensComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChickenViewHolder {
        return ChickenViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChickenViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.birdType)
    }

    class ChickenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chickenItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            chickenItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ChickenViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ChickenViewHolder(view)
            }
        }
    }

    class ChickensComparator : DiffUtil.ItemCallback<Chicken>() {
        override fun areItemsTheSame(oldItem: Chicken, newItem: Chicken): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Chicken, newItem: Chicken): Boolean {
            return oldItem.uid == newItem.uid
        }
    }
}
*/