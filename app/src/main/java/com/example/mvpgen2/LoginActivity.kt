package com.example.mvpgen2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mvpgen2.Contract.LoginActivityContract
import com.example.mvpgen2.Presenter.LoginActivityPresenter
import com.example.mvpgen2.WebService.Constant
import com.example.mvpgen2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginActivityContract.LoginActivityView {
    private lateinit var binding: ActivityLoginBinding
    private var presenter: LoginActivityContract.LoginActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LoginActivityPresenter(this)
        userLogin()
        showloading()
        hideloading()
        doRegister()
    }

    override fun userLogin() {
        binding.ButtonLogin.setOnClickListener {
            val username = binding.ETUsername.text.toString()
            val password = binding.ETPassword.text.toString()
            presenter?.isLogin(username, password)
        }
    }

    override fun showloading() {
        binding.ProgresBar.apply {
            isIndeterminate = true
        }
    }

    override fun hideloading() {
        binding.ProgresBar.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun token(token: String) {
        Constant.setToken(this, token)
    }

    override fun checkToken() {
        val login = this.let {
            Constant.getToken(it)
        }
        if (!login.equals("UNDEFINED")) {
            startActivity(Intent(this, HomeActivity::class.java).also {
                finish()
            })
        }
    }

    override fun doRegister() {
        binding.ButtonRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                finish()
            })
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun succesLogin() {
        startActivity(Intent(this, HomeActivity::class.java).apply {
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        checkToken()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destoryView()
    }
}