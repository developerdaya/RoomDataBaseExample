package com.developerdaya.roomdatabaseexample.setupRoom
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val profile: String,
    val experience: Int,
    val salary: Double
)
