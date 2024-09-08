package ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.notesapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import models.Note
import java.time.LocalDateTime

private const val TITLE = "title"
private const val CONTENT = "content"
private const val TIMESTAMP = "timestamp"
private const val TAG = "AddNoteActivity"

class AddNoteActivity : ComponentActivity() {
    private val db = Firebase.firestore

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
            Note(titleText, noteText, LocalDateTime.now().toString())
        val dataToSave: HashMap<String, Any> = HashMap()

        dataToSave[TITLE] = note.title
        dataToSave[CONTENT] = note.content
        dataToSave[TIMESTAMP] = note.timestamp

        db.collection("notes")
            .add(dataToSave)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding note", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Error adding document", e)
            }
    }
}