package com.example.mvpgen2.Presenter

import android.util.Log
import android.widget.Toast
import com.example.mvpgen2.Contract.MainActivityContract
import com.example.mvpgen2.Model.Auth
import com.example.mvpgen2.Response.SingleResponse
import com.example.mvpgen2.WebService.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(v: MainActivityContract.MainActivityView?) : MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = v

    override fun PostRegist(name: String, username: String, email: String, password: String) {
        view?.showLoading()
        APIService.APIEndPoint().SignUp(name, username, email, password).enqueue(object :
                Callback<SingleResponse<Auth>> {
            override fun onResponse(
                    call: Call<SingleResponse<Auth>>,
                    response: Response<SingleResponse<Auth>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 200) {
                        view?.hideLoading()
                        view?.showToast("Register Succesfully")
                        println("Nama : ${body.data.name}")
                        view?.registSucces()
                    } else {
                        println("Body is null,status : ${body?.status}")
                    }
                } else {
                    view?.showToast("Failed to Register")
                    println("Register Error")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<SingleResponse<Auth>>, t: Throwable) {
                view?.showToast("Unable to connect to server")
                println("Error")
            }
        })

    }

    override fun Destroy() {
        view = null
        Log.d("OnDestroy", "ini OnDestroy")
    }

}