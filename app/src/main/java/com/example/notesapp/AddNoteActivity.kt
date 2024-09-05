package com.example.notesapp

import Note
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.util.UUID

private const val ID = "id"
private const val TITLE = "title"
private const val CONTENT = "content"
private const val TIMESTAMP = "timestamp"
private const val TAG = "MainActivity"

class AddNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNote(view: View) {
        val addNoteTitle: EditText = findViewById(R.id.editTextTitle)
        val addNote: EditText = findViewById(R.id.editTextNote)

        val titleText = addNoteTitle.text.toString()
        val noteText = addNote.text.toString()

        if (noteText.isEmpty() || titleText.isEmpty()) {
            return
        }

        val note =
            Note(UUID.randomUUID().toString(), titleText, noteText, LocalDateTime.now().toString())
        val dataToSave: HashMap<String, Any> = HashMap();

        val db = Firebase.firestore

        dataToSave[ID] = note.id
        dataToSave[TITLE] = note.title
        dataToSave[CONTENT] = note.content
        dataToSave[TIMESTAMP] = note.timestamp

        db.collection("notes")
            .document(note.title)
            .set(dataToSave)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ${note.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

}