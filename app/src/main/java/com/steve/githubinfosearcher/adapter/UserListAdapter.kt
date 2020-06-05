package com.steve.githubinfosearcher.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.steve.githubinfosearcher.AppAplication
import com.steve.githubinfosearcher.R
import com.steve.githubinfosearcher.dataModel.UserModel
import com.steve.githubinfosearcher.databinding.ViewUserItemBinding


class UserListAdapter : PagedListAdapter<UserModel, UserListAdapter.UserViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        val userModelListItemBinding = DataBindingUtil.inflate<ViewUserItemBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.view_user_item, viewGroup, false
        )
        return UserViewHolder(userModelListItemBinding)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, i: Int) {
        val currentUserModel = getItem(i) //must call getItem, or the loadAfter event will not fire
        userViewHolder.userModelListItemBinding.userModel = currentUserModel
        userViewHolder.itemView.setOnClickListener(View.OnClickListener {
            val homeUrl = currentUserModel?.home_url
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(homeUrl))
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppAplication.applicationContext().startActivity(browserIntent)
        })
    }

    inner class UserViewHolder(var userModelListItemBinding: ViewUserItemBinding) :
        RecyclerView.ViewHolder(userModelListItemBinding.root)

    class DiffUtilCallBack : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.login == newItem.login
        }
    }
}