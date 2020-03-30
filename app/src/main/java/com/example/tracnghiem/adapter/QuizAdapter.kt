package com.example.tracnghiem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tracnghiem.BR

class QuizAdapter(private val viewModel: ViewModel, private val layoutId: Int) :
    RecyclerView.Adapter<QuizAdapter.QuizVH>() {
    private var size = 0

    fun removeItem(position: Int) {
        size -= 1
        notifyDataSetChanged()
    }
    fun  loadData(size : Int){
        this.size = size
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutId, parent, false
        )
        return QuizVH(binding)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: QuizVH, position: Int) {
        holder.bindData(viewModel, position)
    }

    inner class QuizVH(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(viewModel: ViewModel, position: Int) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }

}