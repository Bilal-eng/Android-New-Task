package com.example.androidnewtask.model.response

import com.example.androidnewtask.model.CommonContentModel
import com.example.androidnewtask.model.ProductDataModel
import com.example.androidnewtask.model.ProductsModel

data class HomeResponse(
    val commonContent: CommonContentModel,
    val products: ProductsModel,
    val top_seller: ArrayList<ProductDataModel>
)
