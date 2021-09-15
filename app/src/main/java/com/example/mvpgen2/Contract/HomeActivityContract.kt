package com.example.mvpgen2.Contract

import android.content.Context
import android.view.LayoutInflater
import com.example.mvpgen2.Model.Barang

interface HomeActivityContract {
    interface HomeActivityView {
        fun showRecycler(barang: MutableList<Barang>)
        fun showToast(message: String)
        fun intentActivity(id: Int)
        fun showAlertAddItem()
        fun showAlertUpdate(id: Int, name: String, code: Int)
        fun recyclerRefresh(barang: MutableList<Barang>)
        fun floatingAnimation()
        fun alertLogout()
        fun Logout()
    }

    interface HomeActivityPresenter {
        fun getData()
        fun deleteData(id: Int)
        fun deleteAlert(context: Context, id: Int)
        fun destroy()
        fun addItemfromAlert(name: String, code: Int)
        fun updateItemfromAlert(id: Int, name: String, code: Int)
    }

}