package com.steve.githubinfosearcher.dataModel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

class UserModel {
    var login: String? = null
    var home_url: String? = null
    var avatar_url: String? = null

    companion object  {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, imageURL: String) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(RequestOptions().circleCrop())
                .load(imageURL)
                .into(imageView)
        }
    }
}