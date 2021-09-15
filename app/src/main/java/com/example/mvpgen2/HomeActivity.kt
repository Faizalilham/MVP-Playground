package com.example.mvpgen2

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpgen2.Adapter.HomeActivityAdapter
import com.example.mvpgen2.Contract.HomeActivityContract
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.Presenter.DetailActivityPresenter
import com.example.mvpgen2.Presenter.HomeActivityPresenter
import com.example.mvpgen2.WebService.Constant
import com.example.mvpgen2.databinding.ActivityHomeBinding
import com.example.mvpgen2.databinding.AddLayoutBinding
import com.example.mvpgen2.databinding.UpdateLayoutBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeActivityContract.HomeActivityView {

    // Declare lateinit object from class

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeActivityAdapter: HomeActivityAdapter
    private lateinit var addItemLayoutBinding: AddLayoutBinding
    private lateinit var updateLayoutBinding: UpdateLayoutBinding

    //Declare Animation of Floating Button
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private var clicked = false


    private var presenter: HomeActivityContract.HomeActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomeActivityPresenter(this)
    }

    override fun recyclerRefresh(item: MutableList<Barang>) {
        binding.SwipeRefresh.setOnRefreshListener {
            homeActivityAdapter.reloadRecycler(item)
            binding.SwipeRefresh.isRefreshing = false
        }
    }

    //SHOW ANIMATION FOR FLOATING BUTTON
    override fun floatingAnimation() {
        binding.FloatingButton.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            setClickable(clicked)
            clicked = !clicked
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.FloatingButtonAdd.visibility = View.VISIBLE
            binding.FloatingButtonLogout.visibility = View.VISIBLE
        } else {
            binding.FloatingButtonAdd.visibility = View.INVISIBLE
            binding.FloatingButtonLogout.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.FloatingButtonAdd.startAnimation(fromBottom)
            binding.FloatingButtonLogout.startAnimation(fromBottom)
            binding.FloatingButton.startAnimation(rotateOpen)
        } else {
            binding.FloatingButtonAdd.startAnimation(toBottom)
            binding.FloatingButtonLogout.startAnimation(toBottom)
            binding.FloatingButton.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.FloatingButtonAdd.isClickable = true
            binding.FloatingButtonLogout.isClickable = true
        } else {
            binding.FloatingButtonAdd.isClickable = false
            binding.FloatingButtonLogout.isClickable = false
        }
    }


    // SHOW RECYCLER TO VIEW
    override fun showRecycler(barang: MutableList<Barang>) {
        binding.Rcy.apply {
            homeActivityAdapter = HomeActivityAdapter(barang, object : HomeActivityAdapter.OnClick {
                override fun listBarangOnclick(barang: Barang) {
                    intentActivity(barang.id)
                }

                override fun deleteOnclick(barang: Barang) {
                    presenter?.deleteAlert(this@HomeActivity, barang.id)
                }

                override fun updateOnClick(barang: Barang) {
                    showAlertUpdate(barang.id, barang.nama, barang.kode)
                }
            })
            adapter = homeActivityAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    // SHOW TOAST
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // INTENT ID FOR SHOW DETAIL ITEM
    override fun intentActivity(id: Int) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra("id", id)
        })
    }


    // ALERT FOR ADD ITEM
    override fun showAlertAddItem() {
        binding.FloatingButtonAdd.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            addItemLayoutBinding = AddLayoutBinding.inflate(layoutInflater)
            alert.apply {
                setView((addItemLayoutBinding.root))
            }
            val alerDialog = alert.show()
            addItemLayoutBinding.buttonSave.setOnClickListener {
                alerDialog.dismiss()
                val name = addItemLayoutBinding.ETITemName.text.toString()
                val code = addItemLayoutBinding.ETKodeName.text.toString().toInt()
                if (name.isNotBlank() && code >= 0) {
                    presenter?.addItemfromAlert(name, code)
                } else {
                    showToast("Column cannot be empety")
                }
            }
            addItemLayoutBinding.buttonCancel.setOnClickListener {
                alerDialog.dismiss()
            }
        }
    }


    //ALERT FOR UPDATE ITEM
    override fun showAlertUpdate(id: Int, name: String, code: Int) {
        val alert = AlertDialog.Builder(this)
        updateLayoutBinding = UpdateLayoutBinding.inflate(layoutInflater)
        alert.apply {
            alert.setView(updateLayoutBinding.root)
        }
        updateLayoutBinding.ETITemName.setText(name)
        updateLayoutBinding.ETKodeName.setText(code.toString())
        val alertShow = alert.show()

        updateLayoutBinding.buttonSave.setOnClickListener {
            alertShow.dismiss()
            val nama = updateLayoutBinding.ETITemName.text.toString()
            val kode = updateLayoutBinding.ETKodeName.text.toString().toInt()
            presenter?.updateItemfromAlert(id, nama, kode)
        }
        updateLayoutBinding.buttonCancel.setOnClickListener {
            alertShow.dismiss()
        }
    }

    override fun alertLogout() {
        val alert = android.app.AlertDialog.Builder(this)
        alert.apply {
            setTitle("Warning !")
            setMessage("Are you sure want to logout ? ")
            setIcon(R.drawable.ic_warning)
            setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java).also {
                    Constant.delToken(this@HomeActivity)
                    finish()
                })
            }
            setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }
        }
        alert.show()
    }

    override fun Logout() {
        binding.FloatingButtonLogout.setOnClickListener {
            alertLogout()
        }
    }


    override fun onResume() {
        super.onResume()
        presenter?.getData()
        showAlertAddItem()
        floatingAnimation()
        Logout()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}