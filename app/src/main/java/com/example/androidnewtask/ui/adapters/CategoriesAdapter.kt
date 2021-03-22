package com.example.androidnewtask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnewtask.R
import com.example.androidnewtask.model.CategoryModel
import com.example.androidnewtask.utils.Constants
import com.squareup.picasso.Picasso

class CategoriesAdapter(
    private var context: Context?
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categoriesList: ArrayList<CategoryModel>? = ArrayList()

    fun setCategoriesList(categoriesList: ArrayList<CategoryModel>?) {
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = categoriesList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryItem = categoriesList?.get(position)
        val imageUrl = Constants.IMAGE_URL + categoryItem?.path

        Picasso.get().load(imageUrl).fit().centerCrop()
            .into(holder.ivCategoryImage)

        holder.tvCategoryName.text = categoryItem?.name

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCategoryImage = itemView.findViewById<ImageView>(R.id.ivCategoryImage)
        val tvCategoryName = itemView.findViewById<TextView>(R.id.tvCategoryName)
    }
}