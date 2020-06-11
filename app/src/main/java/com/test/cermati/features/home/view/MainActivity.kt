package com.test.cermati.features.home.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.test.cermati.features.home.adapter.SearchAdapter
import com.test.cermati.core.ui.BaseActivity
import com.test.cermati.databinding.ActivityMainBinding
import com.test.cermati.features.home.viewmodel.MainViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.test.cermati.R

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val limit = 10
    private var page = 1


    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter { user ->

        }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        subscribeUI()
    }

    private fun initBinding() {
        bindView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val dividerItemDecoration = DividerItemDecoration(binding.listItem.getContext(), linearLayoutManager.getOrientation())

        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel
        binding.listItem.layoutManager = linearLayoutManager
        binding.listItem.adapter = searchAdapter
        binding.listItem.addItemDecoration(dividerItemDecoration)
        binding.listItem.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if(dy>0){
                    val visibleItemCount = linearLayoutManager.childCount
                    val pastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = searchAdapter.itemCount

                    if(!mainViewModel.isLoading.get()){
                        if(visibleItemCount + pastVisibleItem >= total){
                            page++
                            searchIt()
                        }
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.loading.visibility = View.VISIBLE
        binding.searchLayout.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchAdapter.clearData()
                    page = 1
                    searchIt()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    mainViewModel.searchTextField.set(newText)
                    return false
                }

            })
            findViewById<ImageView>(R.id.search_close_btn).setOnClickListener {
                mainViewModel.searchTextField.set("")
                binding.searchLayout.apply {
                    setQuery("", false)
                    mainViewModel.isPromosEmpty.set(false)
                    page = 1
                    searchAdapter.clearData()
                    isIconified = true
                }
            }
        }
    }

    private fun subscribeUI() {
        mainViewModel.searchData.observe(this, Observer { searchData ->
            searchData?.let {
                if(searchAdapter.itemCount>0) {
                    if(page==1) searchAdapter.clearData()
                    searchAdapter.addData(it)
                }else{
                    searchAdapter.loadData(it)
                }
            }
        })

        mainViewModel.errorResponse.observe(this, Observer { error ->
            when(error.code) {
                403 ->{
                    Toast.makeText(this, "Sorry But You Hit The Limit", Toast.LENGTH_SHORT).show()
                }
                500 ->{
                    Toast.makeText(this, "Sorry But You Hit The Limit", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Sorry But You Hit The Limit", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun searchIt(){
        mainViewModel.searchByName(page,limit)
    }
}
