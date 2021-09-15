package com.example.mvpgen2.Contract

import android.content.Context

interface LoginActivityContract {
    interface LoginActivityView {
        fun doRegister()
        fun showToast(message: String)
        fun succesLogin()
        fun userLogin()
        fun showloading()
        fun hideloading()
        fun token(token: String)
        fun checkToken()
    }

    interface LoginActivityPresenter {
        fun isLogin(username: String, password: String)
        fun destoryView()
    }
}