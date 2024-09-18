package com.example.notesapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.example.notesapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.notesapp.models.Note

private const val TAG = "NoteDetailsActivity"

class NoteDetailsActivity : ComponentActivity() {
    private val db = Firebase.firestore
    private lateinit var contentText: EditText
    private var originalContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        @Suppress("DEPRECATION") val note: Note? = intent.getParcelableExtra("NOTE")

        originalContent = note?.content

        Log.d(TAG, "$note")

        contentText = findViewById(R.id.noteDetails)

        val titleTextView: TextView = findViewById(R.id.detailTextView)

        titleTextView.text = note?.title
        contentText.setText(note?.content)

        val saveButton: Button = findViewById(R.id.saveButton)

        contentText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                saveButton.visibility = if (note?.content != s.toString()) View.VISIBLE else View.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        onBackPress()
    }

    private fun onBackPress() {
        val backPressCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                showSaveConfirmationDialog()
            }
        }

        onBackPressedDispatcher.addCallback(this, backPressCallback)

        contentText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val currentContent = s.toString()
                backPressCallback.isEnabled = currentContent != originalContent
            }
        })
    }

    private fun showSaveConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("You have unsaved changes. Do you want to save before exiting?")
            .setPositiveButton("Save") { dialog, id ->
                saveNote()
                onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("Discard") { dialog, id ->
                Log.d(TAG, "Discard pressed")
                finish()
            }
            .setNeutralButton("Cancel") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
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

    fun onSaveUpdate(view: View) {
        saveNote()
    }

    private fun saveNote() {
        val noteId = intent.getStringExtra("NOTE_ID")
        val noteRef = db.collection("notes").document(noteId!!)

        noteRef
            .update("content", contentText.text.toString())
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                finish()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }
}