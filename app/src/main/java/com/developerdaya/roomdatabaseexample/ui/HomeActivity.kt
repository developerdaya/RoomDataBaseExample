package com.developerdaya.roomdatabaseexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.developerdaya.roomdatabaseexample.R
import com.developerdaya.roomdatabaseexample.adapter.EmployeeAdapter
import com.developerdaya.roomdatabaseexample.databinding.ActivityHomeBinding
import com.developerdaya.roomdatabaseexample.setupRoom.AppDatabase
import com.developerdaya.roomdatabaseexample.setupRoom.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            db.employeeDao().insert(Employee(name = "Ravi Kumar", profile = "Software Engineer", experience = 5, salary = 60000.0))
            db.employeeDao().insert(Employee(name = "Anjali Sharma", profile = "Project Manager", experience = 8, salary = 75000.0))
            db.employeeDao().insert(Employee(name = "Priya Gupta", profile = "UX Designer", experience = 4, salary = 55000.0))
            db.employeeDao().insert(Employee(name = "Amit Patel", profile = "Data Scientist", experience = 6, salary = 80000.0))
            db.employeeDao().insert(Employee(name = "Ravi Singh", profile = "DevOps Engineer", experience = 7, salary = 72000.0))
            db.employeeDao().insert(Employee(name = "Sita Verma", profile = "Product Manager", experience = 10, salary = 90000.0))
            db.employeeDao().insert(Employee(name = "Rohit Mehta", profile = "Backend Developer", experience = 3, salary = 50000.0))
            db.employeeDao().insert(Employee(name = "Neha Reddy", profile = "Front-end Developer", experience = 2, salary = 45000.0))
            db.employeeDao().insert(Employee(name = "Sunil Yadav", profile = "Business Analyst", experience = 5, salary = 65000.0))
            db.employeeDao().insert(Employee(name = "Kavita Singh", profile = "QA Engineer", experience = 4, salary = 58000.0))

            val employeeList = db.employeeDao().getAllEmployees()
            binding.rvEmployeeList.adapter = EmployeeAdapter(employeeList)

            binding.mAll.setOnClickListener {
                binding.rvEmployeeList.adapter = EmployeeAdapter(employeeList)
            }
            binding.maxSallary.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val maxSalaryEmployee = db.employeeDao().getMaxSalaryEmployee()
                    binding.rvEmployeeList.adapter = EmployeeAdapter(maxSalaryEmployee)
                }
            }
            binding.minSallery.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val minSalaryEmployee = db.employeeDao().getMinSalaryEmployee()
                    binding.rvEmployeeList.adapter = EmployeeAdapter(minSalaryEmployee)
                }
            }
            binding.secondMaxSallery.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val secondMaxSalaryEmployee = db.employeeDao().getSecondMaxSalaryEmployee()
                    binding.rvEmployeeList.adapter = EmployeeAdapter(secondMaxSalaryEmployee)
                }
            }

        }
    }


}
