package com.steve.githubinfosearcher.view

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.steve.githubinfosearcher.R
import com.steve.githubinfosearcher.adapter.UserListAdapter
import com.steve.githubinfosearcher.databinding.ActivityMainBinding
import com.steve.githubinfosearcher.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    internal var activityMainBinding: ActivityMainBinding?= null
    var mainViewModel: MainViewModel? = null
    private var userListAdapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // keep screen portait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userListAdapter = UserListAdapter()

        // bind RecyclerView
        val recyclerView = activityMainBinding?.userListRecyclerView
        if (recyclerView != null) {
            recyclerView.setLayoutManager(LinearLayoutManager(this))
            recyclerView.setHasFixedSize(true)
            recyclerView.setAdapter(userListAdapter)
        }

        mainViewModel = MainViewModel(application)
        showSearchUser("")

        activityMainBinding?.keywordSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && !query.isEmpty())
                    showSearchUser(query)
                return false
            }
        })
    }

    fun showSearchUser(query: String) {
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
        mainViewModel!!.searchUser(query).observe(this,
            Observer { userModelList ->
                userListAdapter!!.submitList(userModelList)
            })
    }
}
