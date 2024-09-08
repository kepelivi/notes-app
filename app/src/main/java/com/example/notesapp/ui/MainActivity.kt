package com.example.notesapp.ui

import com.example.notesapp.adapters.NoteAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.notesapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.notesapp.models.Note

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private val db = Firebase.firestore
    private lateinit var listView: ListView
    private lateinit var noteAdapter: NoteAdapter
    private val noteList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        noteAdapter = NoteAdapter(this, noteList)
        listView.adapter = noteAdapter

        loadNotes()

        val addNoteButton: Button = findViewById(R.id.add_button)

        addNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        openNoteDetails()
    }

    private fun loadNotes() {
        db.collection("notes")
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    Log.w(TAG, "Error getting documents.", exception)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    noteList.clear()

                    for (document in snapshots) {
                        val title = document.getString("title") ?: "Untitled"
                        val content = document.getString("content") ?: "No content"
                        val timestamp = document.getString("timestamp") ?: System.currentTimeMillis().toString()

                        noteList.add(Note(title, content, timestamp))
                    }

                    noteAdapter.notifyDataSetChanged()
                }
            }
    }

    private fun openNoteDetails() {
        listView.setOnItemClickListener { parent, view, position, id ->
            val clickedNote: Note = noteAdapter.getItem(position)

            Log.d(TAG, "$clickedNote")

            val intent = Intent(this, NoteDetailsActivity::class.java)

            intent.putExtra("NOTE", clickedNote)

            startActivity(intent)
        }
    }
}