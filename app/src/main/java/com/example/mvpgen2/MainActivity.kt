package com.example.mvpgen2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mvpgen2.Contract.MainActivityContract
import com.example.mvpgen2.Presenter.MainActivityPresenter
import com.example.mvpgen2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView {

    private lateinit var binding: ActivityMainBinding
    private var presenter: MainActivityContract.MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("OnCreate", "ini OnCreate")
        presenter = MainActivityPresenter(this)
        hideLoading()
        showLoading()
        doLogin()
    }

    override fun Regist() {
        binding.ButtonRegister.setOnClickListener {
            val name = binding.ETName.text.toString()
            val username = binding.ETUsername.text.toString()
            val email = binding.ETEmail.text.toString()
            val password = binding.ETPassword.text.toString()
            if (name.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                if (password.length >= 8) {
                    presenter?.PostRegist(name, username, email, password)
                } else {
                    android.widget.Toast.makeText(this, "Password must be more than 8 characters", android.widget.Toast.LENGTH_SHORT).show()
                }
            } else {
                android.widget.Toast.makeText(this, "Column cannot be empety", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun registSucces() {
        startActivity(Intent(this, LoginActivity::class.java).also {
            finish()
        })
    }

    override fun doLogin() {
        binding.ButtonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).also {
                finish()
            })
        }
    }

    override fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        binding.ProgresBar.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showLoading() {
        binding.ProgresBar.apply {
            isIndeterminate = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.Destroy()
        Log.d("OnDestroy", "ini OnDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("OnRestart", "ini OnRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "ini OnResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("OnStop", "ini OnStop")
    }
}