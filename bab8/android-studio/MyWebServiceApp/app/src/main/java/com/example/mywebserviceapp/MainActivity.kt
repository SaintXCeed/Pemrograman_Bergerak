package com.example.mywebserviceapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi View
        val etId = findViewById<EditText>(R.id.etId)
        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etMajor = findViewById<EditText>(R.id.etMajor)

        val btnSendData = findViewById<Button>(R.id.btnSendData)
        val btnFetchData = findViewById<Button>(R.id.btnFetchData)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        val tvResponse = findViewById<TextView>(R.id.tvResponse)

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.6:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // =======================
        // TAMBAH DATA (CREATE)
        // =======================
        btnSendData.setOnClickListener {

            if (etName.text.isEmpty() || etAge.text.isEmpty() || etMajor.text.isEmpty()) {
                tvResponse.text = "Semua field harus diisi"
                return@setOnClickListener
            }

            val student = Student(
                name = etName.text.toString(),
                age = etAge.text.toString().toInt(),
                major = etMajor.text.toString()
            )

            apiService.addStudent(student).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    tvResponse.text = response.body()?.message ?: "Berhasil"
                    clearInput(etId, etName, etAge, etMajor)
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    tvResponse.text = "Failure: ${t.message}"
                }
            })
        }

        // =======================
        // AMBIL DATA (READ)
        // =======================
        btnFetchData.setOnClickListener {
            apiService.getStudents().enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.data ?: emptyList()
                        val text = list.joinToString("\n") {
                            "ID: ${it.id}, Nama: ${it.name}, Umur: ${it.age}, Jurusan: ${it.major}"
                        }
                        tvResponse.text = text
                    } else {
                        tvResponse.text = "Error: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    tvResponse.text = "Failure: ${t.message}"
                }
            })
        }

        // =======================
        // UPDATE DATA
        // =======================
        btnUpdate.setOnClickListener {

            if (etId.text.isEmpty()) {
                tvResponse.text = "ID harus diisi"
                return@setOnClickListener
            }

            val id = etId.text.toString().toInt()

            val student = Student(
                name = etName.text.toString(),
                age = etAge.text.toString().toInt(),
                major = etMajor.text.toString()
            )

            apiService.updateStudent(id, student).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    tvResponse.text = response.body()?.message ?: "Data diupdate"
                    clearInput(etId, etName, etAge, etMajor)
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    tvResponse.text = "Failure: ${t.message}"
                }
            })
        }

        // =======================
        // HAPUS DATA (DELETE)
        // =======================
        btnDelete.setOnClickListener {

            if (etId.text.isEmpty()) {
                tvResponse.text = "ID harus diisi"
                return@setOnClickListener
            }

            val id = etId.text.toString().toInt()

            apiService.deleteStudent(id).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    tvResponse.text = response.body()?.message ?: "Data dihapus"
                    clearInput(etId, etName, etAge, etMajor)
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    tvResponse.text = "Failure: ${t.message}"
                }
            })
        }
    }

    private fun clearInput(vararg editTexts: EditText) {
        editTexts.forEach { it.text.clear() }
    }
}
