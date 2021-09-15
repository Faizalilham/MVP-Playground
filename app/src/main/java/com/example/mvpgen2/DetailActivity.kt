package com.example.mvpgen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mvpgen2.Contract.DetailActivityContract
import com.example.mvpgen2.Model.Barang
import com.example.mvpgen2.Presenter.DetailActivityPresenter
import com.example.mvpgen2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), DetailActivityContract.DetailView {

    private lateinit var binding: ActivityDetailBinding
    private var presenter: DetailActivityContract.DetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DetailActivityPresenter(this)
        presenter?.getDetailbyId()

    }

    override fun showDetailData(barang: List<Barang>) {
        binding.TvNama.text = barang[0].nama
        binding.TvKode.text = barang[0].kode.toString()
        binding.TvCreatedAt.text = barang[0].createdAt
        binding.TvUpdatedAt.text = barang[0].updatedAt
    }

    override fun intentData(): Int {
        return intent.getIntExtra("id", 0)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.Destroy()
    }

}