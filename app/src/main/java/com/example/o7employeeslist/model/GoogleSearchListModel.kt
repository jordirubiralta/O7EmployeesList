package com.example.o7employeeslist.model

import com.google.gson.annotations.SerializedName

data class GoogleSearchListModel(
    @SerializedName("items") val items: List<GoogleSearchModel>
)
