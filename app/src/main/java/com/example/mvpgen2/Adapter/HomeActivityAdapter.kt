package com.example.mvpgen2.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.databinding.ListbarangBinding

class HomeActivityAdapter(private var barang: MutableList<Barang>, val listener: HomeActivityAdapter.OnClick) : RecyclerView.Adapter<HomeActivityAdapter.UserViewHolder>() {

    inner class UserViewHolder(var binding: ListbarangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ListbarangBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            TV.text = barang[position].nama
            TV.setOnClickListener {
                listener.listBarangOnclick(barang[position])
            }
            ButtonDelete.setOnClickListener {
                listener.deleteOnclick(barang[position])
            }
            ButtonUpdate.setOnClickListener {
                listener.updateOnClick(barang[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return barang.size
    }

    interface OnClick {
        fun listBarangOnclick(barang: Barang)
        fun deleteOnclick(barang: Barang)
        fun updateOnClick(barang: Barang)
    }

    fun reloadRecycler(data: MutableList<Barang>) {
        barang.clear()
        barang.addAll(data)
        notifyDataSetChanged()
    }

}