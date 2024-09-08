package com.example.notesapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.notesapp.R
import com.example.notesapp.models.Note

class NoteAdapter(private val context: Context, private val notes: List<Note>) : BaseAdapter() {
    override fun getCount(): Int = notes.size

    override fun getItem(position: Int): Note = notes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val currentNote = getItem(position)

        Log.d("NoteAdapter", "$currentNote")

        val titleTextView: TextView = rowView.findViewById(R.id.itemTextView)
        titleTextView.text = currentNote.title

        return rowView
    }
}

