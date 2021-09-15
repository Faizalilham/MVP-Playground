package com.example.mvpgen2.Contract

interface MainActivityContract {

    interface MainActivityView {
        fun Regist()
        fun registSucces()
        fun doLogin()
        fun showToast(message: String)
        fun hideLoading()
        fun showLoading()
    }

    interface MainActivityPresenter {
        fun PostRegist(name: String, username: String, email: String, password: String)
        fun Destroy()
    }


}