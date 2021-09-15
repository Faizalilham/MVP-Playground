package com.example.mvpgen2.Presenter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mvpgen2.Adapter.HomeActivityAdapter
import com.example.mvpgen2.Contract.HomeActivityContract
import com.example.mvpgen2.DetailActivity
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.R
import com.example.mvpgen2.Response.ListResponse
import com.example.mvpgen2.Response.SingleResponse
import com.example.mvpgen2.WebService.APIService
import com.example.mvpgen2.databinding.UpdateLayoutBinding
import kotlinx.android.synthetic.main.add_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivityPresenter(private var view: HomeActivityContract.HomeActivityView?) : HomeActivityContract.HomeActivityPresenter {

    // GET DATA FROM DATABSE USE RETROFIT
    override fun getData() {
        APIService.APIEndPoint().getData().enqueue(object : Callback<ListResponse<Barang>> {
            override fun onResponse(call: Call<ListResponse<Barang>>, response: Response<ListResponse<Barang>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        view?.showRecycler(body.data)
                        view?.recyclerRefresh(body.data)
                    } else {
                        view?.showToast("body is null")
                    }
                } else {
                    view?.showToast("Check your network connection")
                }
            }

            override fun onFailure(call: Call<ListResponse<Barang>>, t: Throwable) {
                view?.showToast("Unable connect to sever")
            }
        })
    }


    // DELETE DATA FROM DATABASE USE RETROFIT
    override fun deleteData(id: Int) {
        APIService.APIEndPoint().deletebyId(id).enqueue(object : Callback<SingleResponse<Barang>> {
            override fun onResponse(call: Call<SingleResponse<Barang>>, response: Response<SingleResponse<Barang>>) {
                if (response.isSuccessful) {
                    view?.showToast("The data has been deleted")
                } else {
                    view?.showToast("check your network connection before delete data")
                }
            }

            override fun onFailure(call: Call<SingleResponse<Barang>>, t: Throwable) {
                view?.showToast("Unable connect to server")
            }
        })
    }

    override fun deleteAlert(context: Context, id: Int) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setTitle("Warning !")
            setMessage("Are you sure want to delete this item ?")
            setIcon(R.drawable.ic_warning)
            setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int -> deleteData(id) }
            setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        }
        alert.show()
    }


    // ADD DATA TO  DATABASE USE RETROFIT
    override fun addItemfromAlert(name: String, code: Int) {
        APIService.APIEndPoint().addItem(name, code).enqueue(object : Callback<SingleResponse<Barang>> {
            override fun onResponse(call: Call<SingleResponse<Barang>>, response: Response<SingleResponse<Barang>>) {
                if (response.isSuccessful) {
                    view?.showToast("Item has been added")
                } else {
                    view?.showToast("Check your network connection")
                }
            }

            override fun onFailure(call: Call<SingleResponse<Barang>>, t: Throwable) {
                view?.showToast("Unable connect to server")
            }
        })
    }

    // UPDATE DATA FROM DATABSASE USE RETROFIT
    override fun updateItemfromAlert(id: Int, name: String, code: Int) {
        APIService.APIEndPoint().updateItem(id, name, code).enqueue(object : Callback<SingleResponse<Barang>> {
            override fun onResponse(call: Call<SingleResponse<Barang>>, response: Response<SingleResponse<Barang>>) {
                if (response.isSuccessful) {
                    view?.showToast("Item has been updated")
                } else {
                    view?.showToast("Check your network connection")
                }
            }

            override fun onFailure(call: Call<SingleResponse<Barang>>, t: Throwable) {
                view?.showToast("Unable to connect to server")
            }
        })
    }


    // DESTROY VIEW
    override fun destroy() {
        view = null
    }
}