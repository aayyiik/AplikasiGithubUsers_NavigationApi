package com.submission.picodiploma.aplikasigithubusers_navigationapi.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter.UsersAdapter
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.ActivityMainBinding
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult
import com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel.MainViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding


    private val adapter by lazy{

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)

        }
        UsersAdapter { user->
            Intent(this, DetailUserActivity::class.java).apply {
                putExtra("username",user.login)
                startActivity(this)


            }
        }

    }

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        binding.searchListUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                if(q!= null && q.isNotEmpty()){
                    if(adapter.itemCount == 0){
                        Toast.makeText(this@MainActivity, "Maaf username tidak ditemukan...", Toast.LENGTH_LONG)
                            .show()
                    }else{
                        viewModel.procesUser(q.toString())
                    }
                }else{
                    viewModel.procesUser()
                }
                return false
            }


            var usernameSearch:Job? = null

            override fun onQueryTextChange(newText: String?): Boolean {
                usernameSearch?.cancel()
                if (newText != null && newText.isNotEmpty())
                {
                    usernameSearch = lifecycleScope.launch(Dispatchers.Main){
                        delay(500)
                        viewModel.procesUser(newText.toString())
                    }

                    }else{
                    viewModel.procesUser()
                }
                return false
            }

        })


        viewModel.myResultUser.observe(this){
            when(it){
                is MyResult.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
                is MyResult.Error -> {
                    Toast.makeText(this, it.exception.message.toString(),
                    Toast.LENGTH_SHORT).show()
                }
                is MyResult.Success<*> -> {
                    adapter.setData(it.list as MutableList<ItemsItem>)
                }
            }
        }
        viewModel.procesUser()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}