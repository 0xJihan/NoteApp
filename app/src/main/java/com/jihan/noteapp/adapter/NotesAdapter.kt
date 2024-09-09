package com.jihan.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jihan.noteapp.databinding.ItemBinding
import com.jihan.noteapp.model.NoteResponse
import javax.inject.Inject

class NotesAdapter : ListAdapter<NoteResponse, NotesAdapter.NoteViewHolder>(NoteDiffUtils()) {

    class NoteViewHolder(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(note: NoteResponse) {
            item.title.text = note.title
            item.desc.text = note.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = ItemBinding.inflate(LayoutInflater.from(parent.context))

        return NoteViewHolder(item)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

         holder.itemView.setOnClickListener {
             // TODO: Navigate to detail screen with note data
         }
        note?.let {
            holder.bind(it)
        }
    }


    class NoteDiffUtils : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

    }

}
