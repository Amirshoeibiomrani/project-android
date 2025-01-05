
package com.example.myapplication

import NotesApiService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.api.Note
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://185.243.48.179:4006/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NotesApiService::class.java)

        setContent {
            MyApplicationTheme {
                val coroutineScope = rememberCoroutineScope()
                val notes = remember { mutableStateListOf<Note>() }
                var isLoading by remember { mutableStateOf(false) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isLoading = true
                                errorMessage = null
                                try {
                                    val response = api.getNotes()
                                    if (response.isSuccessful) {
                                        response.body()?.let { notesResponse ->
                                            notes.clear()
                                            notes.addAll(notesResponse.items)
                                        }
                                    } else {
                                        errorMessage = "Error: ${response.message()}"
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Exception: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(text = "Get Notes")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

//                    LazyColumn(modifier = Modifier.fillMaxSize()) {
//                        items(notes) { note ->
//                            Row(
//                                modifier = Modifier.fillMaxWidth().padding(8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Column {
//                                    Text(text = note.title)
//                                    Text(text = note.content)

                                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                                        items(notes) { note ->

                                            NoteItem(note)

                                        }

                                    }

                                }

                            }

                        }

                    }

                }


                @Composable

                fun NoteItem(note: Note) {

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 8.dp)

                            .clickable { /* Handle click */ },



                        shape = MaterialTheme.shapes.medium

                    ) {

                        Column(

                            modifier = Modifier

                                .padding(16.dp)

                                .background(Color.White)

                        ) {

                            Text(

                                text = note.title,

                                style = MaterialTheme.typography.titleLarge,

                                color = Color(0xFF37474F) // Dark text color

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(

                                text = note.content,

                                style = MaterialTheme.typography.bodyMedium,

                                color = Color(0xFF757575) // Gray text color

                            )





                        }

                    }

                }


                @Preview(showBackground = true)

                @Composable

                fun PreviewNoteItem() {

                    MyApplicationTheme {

                        NoteItem(Note("Sample Title", "This is a sample content for the note.", "2023-10-01"))

                    }

                }









