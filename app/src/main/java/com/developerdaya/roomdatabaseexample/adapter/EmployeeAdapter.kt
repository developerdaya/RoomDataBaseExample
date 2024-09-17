package com.developerdaya.roomdatabaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developerdaya.roomdatabaseexample.R
import com.developerdaya.roomdatabaseexample.setupRoom.Employee

class EmployeeAdapter(private val employees: List<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val profileTextView: TextView = itemView.findViewById(R.id.profileTextView)
        val experienceTextView: TextView = itemView.findViewById(R.id.experienceTextView)
        val salaryTextView: TextView = itemView.findViewById(R.id.salaryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_item, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.nameTextView.text = employee.name
        holder.profileTextView.text = employee.profile
        holder.experienceTextView.text = "${employee.experience} years"
        holder.salaryTextView.text = "₹${employee.salary}"
    }

    override fun getItemCount(): Int {
        return employees.size
    }
}
