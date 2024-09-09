package com.jihan.noteapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jihan.noteapp.model.NoteRequest
import com.jihan.noteapp.model.NoteResponse
import com.jihan.noteapp.retrofit.NoteApi
import com.jihan.noteapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(private val noteApi: NoteApi) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: LiveData<NetworkResult<List<NoteResponse>>> get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>> get() = _statusLiveData

    // get all notes
    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResult.Loading())
        try {
            val response = noteApi.getNotes()

            handleResponse(response)

        } catch (e: Exception) {
            _notesLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }
    }


    // ====================  create a new note ========================
    suspend fun createNote(noteRequest: NoteRequest) {

        try {
            val response = noteApi.createNote(noteRequest)
            noteResponseHandler(response,"Note created successfully")
        } catch (e: Exception) {
            _notesLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }
    }


    // ====================  update a note =========================
    suspend fun updateNote(noteId: String, noteRequest: NoteRequest) {
        try {
            val response = noteApi.updateNote(noteId, noteRequest)
            noteResponseHandler(response,"Note updated")
        } catch (e: Exception) {
            _statusLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }
    }

    // ====================  delete a note =========================
    suspend fun deleteNote(noteId: String) {
        try {
            val response = noteApi.deleteNote(noteId)
            noteResponseHandler(response,"Note deleted")
        } catch (e: Exception) {
            _statusLiveData.postValue(NetworkResult.Error("Network Error Occurred: ${e.message}"))
        }
    }


    // ====================================  Handle Response For Getting Notes  =================================
    private fun handleResponse(result: Response<List<NoteResponse>>) {
        if (result.isSuccessful && result.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(result.body()!!))
        } else if (result.errorBody() != null) {

            val errorMessage = JSONObject(result.errorBody()!!.charStream().readText())

            _notesLiveData.postValue(NetworkResult.Error(errorMessage.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    private fun noteResponseHandler(response: Response<NoteResponse> , message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}