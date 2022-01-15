package ua.com.koval.andrey.cryptoapp.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinInfo (
    @SerializedName("Id")
    @Expose
     val id: String,

    @SerializedName("Name")
    @Expose
     val name: String,

    @SerializedName("FullName")
    @Expose
     val fullName: String,

    @SerializedName("ImageUrl")
    @Expose
     val imageUrl: String
)