package com.example.o7employeeslist.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.o7employeeslist.databinding.RowEmployeeBinding
import com.example.o7employeeslist.model.EmployeeModel

class EmployeeListAdapter(
    var list: MutableList<EmployeeModel> = mutableListOf(),
    val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        val itemBinding =
            RowEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        val model: EmployeeModel = list[position]
        holder.bind(model, onItemClick)
    }

    override fun getItemCount(): Int = list.size

    class EmployeeHolder(private val itemBinding: RowEmployeeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: EmployeeModel, onItemClickListener: (String) -> Unit) {
            itemBinding.tvName.text = model.name
            itemBinding.tvBirthday.text =
                EmployeeModel.timestampToStringDate(model.birthday)
            itemBinding.tvSalary.text = "${model.salary}$"
            itemBinding.tvGender.text = if (model.gender == "M") "Male" else "Female"
            itemBinding.root.setOnClickListener { onItemClickListener(model.name) }
        }
    }

    fun update(updateList: List<EmployeeModel>) {
        list = updateList.toMutableList()
        notifyDataSetChanged()
    }
}
