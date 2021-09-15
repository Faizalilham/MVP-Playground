package com.example.mvpgen2.Presenter

import com.example.mvpgen2.Contract.DetailActivityContract
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.Response.ListResponse
import com.example.mvpgen2.WebService.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityPresenter(private var view: DetailActivityContract.DetailView?) : DetailActivityContract.DetailPresenter {

    override fun getDetailbyId() {
        val id = view?.intentData()
        if (id != null) {
            APIService.APIEndPoint().getbyId(id).enqueue(object : Callback<ListResponse<Barang>> {
                override fun onResponse(
                        call: Call<ListResponse<Barang>>,
                        response: Response<ListResponse<Barang>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            view?.showDetailData(body.data)
                        } else {
                            view?.showToast("body is null")
                        }
                    } else {
                        view?.showToast("Check your network connection")
                    }
                }

                override fun onFailure(call: Call<ListResponse<Barang>>, t: Throwable) {
                    view?.showToast("Unable connect to server")
                }
            })
        }
    }

    override fun Destroy() {
        view = null
    }
}