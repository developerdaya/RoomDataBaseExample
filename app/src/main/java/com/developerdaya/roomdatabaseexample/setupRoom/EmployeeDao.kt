package com.developerdaya.roomdatabaseexample.setupRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {

    // Save an employee
    @Insert
    suspend fun insert(employee: Employee)

    // Fetch all employees
    @Query("SELECT * FROM employee")
    suspend fun getAllEmployees(): List<Employee>

    // Delete an employee by ID
    @Query("DELETE FROM employee WHERE id = :employeeId")
    suspend fun deleteById(employeeId: Int)

    // Get employee with maximum salary
    @Query("SELECT * FROM employee ORDER BY salary DESC LIMIT 1")
    suspend fun getMaxSalaryEmployee(): List<Employee>

    // Get employee with minimum salary
    @Query("SELECT * FROM employee ORDER BY salary ASC LIMIT 1")
    suspend fun getMinSalaryEmployee():  List<Employee>

    @Query("""
    SELECT * FROM employee 
    WHERE salary < (SELECT MAX(salary) FROM employee) 
    ORDER BY salary DESC 
    LIMIT 1
""")
    suspend fun getSecondMaxSalaryEmployee(): List<Employee>

}
