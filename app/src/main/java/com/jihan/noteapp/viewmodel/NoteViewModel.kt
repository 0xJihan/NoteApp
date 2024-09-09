package com.jihan.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.noteapp.model.NoteRequest
import com.jihan.noteapp.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesRepository: NotesRepository) : ViewModel() {

    val notesLiveData = notesRepository.notesLiveData
    val statusLiveData = notesRepository.statusLiveData

    fun createNote(noteRequest: NoteRequest) {
        viewModelScope.launch (Dispatchers.IO) {
            notesRepository.createNote(noteRequest)
        }
    }



    fun updateNote(noteId : String,noteRequest: NoteRequest) {
        viewModelScope.launch (Dispatchers.IO) {
            notesRepository.updateNote(noteId,noteRequest)
        }
    }



    fun deleteNote(noteId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            notesRepository.deleteNote(noteId)
        }
    }



    fun getNotes() {
        viewModelScope.launch (Dispatchers.IO) {
            notesRepository.getNotes()
        }
    }




}