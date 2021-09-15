package com.example.mvpgen2.Contract

import com.example.mvpgen2.Model.Barang

interface DetailActivityContract {
    interface DetailView {
        fun showDetailData(barang: List<Barang>)
        fun intentData(): Int
        fun showToast(message: String)
    }

    interface DetailPresenter {
        fun getDetailbyId()
        fun Destroy()
    }
}