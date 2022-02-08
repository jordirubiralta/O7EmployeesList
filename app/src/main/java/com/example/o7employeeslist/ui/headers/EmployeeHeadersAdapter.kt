package com.example.o7employeeslist.ui.headers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.o7employeeslist.databinding.RowHeaderBinding
import com.example.o7employeeslist.model.GoogleSearchModel

class EmployeeHeadersAdapter(private val headersList: List<GoogleSearchModel>) :
    RecyclerView.Adapter<EmployeeHeadersAdapter.HeaderHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderHolder {
        val itemBinding =
            RowHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HeaderHolder, position: Int) {
        val model: GoogleSearchModel = headersList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = headersList.size

    class HeaderHolder(private val itemBinding: RowHeaderBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: GoogleSearchModel) {
            itemBinding.tvHeader.text = model.title
        }
    }
}
