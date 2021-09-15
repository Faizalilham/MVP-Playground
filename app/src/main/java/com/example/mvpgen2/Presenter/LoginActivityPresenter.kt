package com.example.mvpgen2.Presenter

import android.content.Context
import com.example.mvpgen2.Contract.LoginActivityContract
import com.example.mvpgen2.Model.Auth
import com.example.mvpgen2.Response.SingleResponse
import com.example.mvpgen2.WebService.APIService
import com.example.mvpgen2.WebService.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(private var v: LoginActivityContract.LoginActivityView?) : LoginActivityContract.LoginActivityPresenter {
    override fun isLogin(username: String, password: String) {
        v?.showloading()
        APIService.APIEndPoint().SignIn(username, password).enqueue(object : Callback<SingleResponse<Auth>> {
            override fun onResponse(call: Call<SingleResponse<Auth>>, response: Response<SingleResponse<Auth>>) {
                if (username.isNotBlank() && password.isNotBlank()) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            v?.hideloading()
                            v?.token(body.data.token)
                            v?.showToast("Hai ${body.data.name}")
                            v?.succesLogin()
                        }
                    } else {
                        v?.showToast("Login Failed")
                    }
                    v?.hideloading()
                } else {
                    v?.showToast("Column cannot be empety")
                }
            }

            override fun onFailure(call: Call<SingleResponse<Auth>>, t: Throwable) {
                v?.showloading()
                v?.showToast("Unable to connect to server")
            }

        })
    }


    override fun destoryView() {
        v = null
    }
}