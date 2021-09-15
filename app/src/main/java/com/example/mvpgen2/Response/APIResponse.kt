package com.example.mvpgen2.Response

data class ListResponse<T>(
        var msg: String,
        var status: Int,
        var data: MutableList<T>,
)

data class SingleResponse<T>(
        var msg: String,
        var status: Int,
        var data: T,
)

