package com.example.sqlitedemo.db

import android.content.ContentValues;
import android.content.Context;
import com.example.sqlitedemo.db.Student
import androidx.room.*

@Dao
interface StudentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int
}



//class StudentDAO (context: Context) {
//    private val dbHelper = MyDatabaseHelper(context);
//
//    fun insertStudent(name: String, age: Int): Long {
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put(MyDatabaseHelper.COLUMN_NAME, name)
//            put(MyDatabaseHelper.COLUMN_AGE, age)
//        }
//        return db.insert(MyDatabaseHelper.TABLE_NAME, null, values)
//    }
//
//    fun getAllStudents(): List<Student> {
//        val students = mutableListOf<Student>()
//        val db = dbHelper.readableDatabase
//        val cursor = db.query(MyDatabaseHelper.TABLE_NAME, null, null, null, null, null, null)
//
//        with(cursor) {
//            while (moveToNext()) {
//                val id = getInt(getColumnIndexOrThrow(MyDatabaseHelper
//                    .COLUMN_ID))
//                val name = getString(getColumnIndexOrThrow(MyDatabaseHelper
//                    .COLUMN_NAME))
//                val age = getInt(getColumnIndexOrThrow(MyDatabaseHelper
//                    .COLUMN_AGE))
//                students.add(Student(id, name, age))
//            }
//        }
//        cursor.close()
//        return students
//    }
//    fun updateStudent(id: Int, name: String, age: Int): Int {
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put(MyDatabaseHelper.COLUMN_NAME, name)
//            put(MyDatabaseHelper.COLUMN_AGE, age)
//        }
//        return db.update(MyDatabaseHelper.TABLE_NAME, values, "${MyDatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()))
//    }
//
//    fun deleteStudent(id: Int): Int {
//        val db = dbHelper.writableDatabase
//
//        return db.delete(MyDatabaseHelper.TABLE_NAME, "${MyDatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()))
//    }
//
//
//}