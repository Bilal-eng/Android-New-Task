package com.example.androidnewtask.model.response

import com.example.androidnewtask.model.CommonContentModel
import com.example.androidnewtask.model.ProductsModel
import com.example.androidnewtask.model.TopSellerModel

data class HomeResponse(
    val commonContent: CommonContentModel,
    val products: ProductsModel,
    val top_seller: TopSellerModel
)
