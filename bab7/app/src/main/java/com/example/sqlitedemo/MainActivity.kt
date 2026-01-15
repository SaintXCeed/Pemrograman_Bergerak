package com.example.sqlitedemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.db.AppDatabase
import com.example.sqlitedemo.db.Student
import com.example.sqlitedemo.db.StudentDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var studentDAO: StudentDAO
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Room Database dan DAO
        val db = AppDatabase.getDatabase(this)
        studentDAO = db.studentDao()

        val etNama = findViewById<EditText>(R.id.etNama)
        val etUsia = findViewById<EditText>(R.id.etUsia)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter dengan list kosong terlebih dahulu
        adapter = StudentAdapter(
            mutableListOf(),
            onEdit = { student -> showEditDialog(student) },
            onDelete = { student -> confirmDelete(student) }
        )
        recyclerView.adapter = adapter

        // Ambil data awal dari Room
        refreshData()

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val usia = etUsia.text.toString().toIntOrNull() ?: 0

            if (nama.isNotEmpty()) {
                lifecycleScope.launch {
                    // Membuat objek Student baru (id otomatis 0/auto-increment)
                    val newStudent = Student(name = nama, age = usia)
                    studentDAO.insertStudent(newStudent)

                    etNama.text.clear()
                    etUsia.text.clear()
                    refreshData()

                    Toast.makeText(this@MainActivity, "Data tersimpan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi pembantu untuk memperbarui data di RecyclerView
    private fun refreshData() {
        lifecycleScope.launch {
            val students = studentDAO.getAllStudents()
            adapter.updateData(students)
        }
    }

    private fun showEditDialog(student: Student) {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 10)
        }

        val etNama = EditText(this).apply {
            setText(student.name)
            hint = "Nama"
        }

        val etUsia = EditText(this).apply {
            setText(student.age.toString())
            hint = "Usia"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        layout.addView(etNama)
        layout.addView(etUsia)

        AlertDialog.Builder(this)
            .setTitle("Edit Mahasiswa")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val namaBaru = etNama.text.toString().trim()
                val usiaBaru = etUsia.text.toString().toIntOrNull() ?: 0

                if (namaBaru.isNotEmpty()) {
                    lifecycleScope.launch {
                        // Update menggunakan objek student yang id-nya sudah ada
                        val updatedStudent = student.copy(name = namaBaru, age = usiaBaru)
                        studentDAO.updateStudent(updatedStudent)
                        refreshData()
                        Toast.makeText(this@MainActivity, "Data diperbarui", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Mahasiswa")
            .setMessage("Yakin ingin menghapus ${student.name}?")
            .setPositiveButton("Ya") { _, _ ->
                lifecycleScope.launch {
                    studentDAO.deleteStudent(student)
                    refreshData()
                    Toast.makeText(this@MainActivity, "Data Dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}