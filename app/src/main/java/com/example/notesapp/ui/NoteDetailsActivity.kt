package com.example.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.notesapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.notesapp.models.Note

private const val TAG = "NoteDetailsActivity"

class NoteDetailsActivity : ComponentActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        @Suppress("DEPRECATION") val note: Note? = intent.getParcelableExtra("NOTE")

        Log.d(TAG, "$note")

        val textView: TextView = findViewById(R.id.noteDetails)
        val titleTextView: TextView = findViewById(R.id.detailTextView)
        titleTextView.text = note?.title
        textView.text = note?.content
    }

    fun onDelete(view: View) {
        val noteId = intent.getStringExtra("NOTE_ID")
        if (noteId != null) {
            db.collection("notes").document(noteId)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!")
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
    }
}