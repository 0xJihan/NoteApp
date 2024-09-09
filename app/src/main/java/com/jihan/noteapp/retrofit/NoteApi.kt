package com.jihan.noteapp.retrofit

import com.jihan.noteapp.model.NoteRequest
import com.jihan.noteapp.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApi {


    @POST("note/")
    suspend fun createNote ( @Body noteRequest: NoteRequest) : Response<NoteResponse>

    @GET("note/")
    suspend fun getNotes () : Response<List<NoteResponse>>

    @PUT("note/{noteId}")
    suspend fun updateNote ( @Path("noteId") noteId : String, noteRequest: NoteRequest) : Response<NoteResponse>

    @DELETE("note/{noteId}")
    suspend fun deleteNote ( @Path("noteId") noteId :String) : Response<NoteResponse>
}