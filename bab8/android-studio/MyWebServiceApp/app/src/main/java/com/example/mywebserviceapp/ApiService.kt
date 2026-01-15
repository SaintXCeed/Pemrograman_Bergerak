package com.example.mywebserviceapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("api/students")
    fun getStudents(): Call<ApiResponse>

    @POST("api/students")
    fun addStudent(@Body student: Student): Call<ApiResponse>

    @PUT("api/students/{id}")
    fun updateStudent(
        @Path("id") id: Int,
        @Body student: Student
    ): Call<ApiResponse>

    @DELETE("api/students/{id}")
    fun deleteStudent(
        @Path("id") id: Int
    ): Call<ApiResponse>
}


data class ApiResponse(
    val message: String,
//    val data: List<String>? = null
    val data: List<Student>? = null
    )

data class Student(
    val id: Int? = null,
    val name: String,
    val age: Int,
    val major: String
)

