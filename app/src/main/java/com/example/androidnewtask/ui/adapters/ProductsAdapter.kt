package com.example.androidnewtask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnewtask.R
import com.example.androidnewtask.model.ProductDataModel
import com.example.androidnewtask.utils.Constants
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private var context: Context?
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var productList: ArrayList<ProductDataModel>? = ArrayList()

    fun setProductList(productList: ArrayList<ProductDataModel>?) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = productList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = productList?.get(position)
        val imageUrl = Constants.IMAGE_URL + productItem?.image_path

        Picasso.get().load(imageUrl).fit().centerCrop()
            .into(holder.ivProductImage)

        holder.tvProductTitle.text = productItem?.products_name
        holder.tvProductPrice.text = Constants.CURRENCY + productItem?.products_price


        holder.ivEmptyFavIcon.setOnClickListener {
            holder.ivEmptyFavIcon.visibility = GONE
            holder.ivFilledFavIcon.visibility = VISIBLE
        }

        holder.ivFilledFavIcon.setOnClickListener {
            holder.ivFilledFavIcon.visibility = GONE
            holder.ivEmptyFavIcon.visibility = VISIBLE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage = itemView.findViewById<ImageView>(R.id.ivProductImage)
        val ivFilledFavIcon = itemView.findViewById<ImageView>(R.id.ivFilledFavIcon)
        val ivEmptyFavIcon = itemView.findViewById<ImageView>(R.id.ivEmptyFavIcon)
        val tvProductTitle = itemView.findViewById<TextView>(R.id.tvProductTitle)
        val tvProductPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)
    }
}