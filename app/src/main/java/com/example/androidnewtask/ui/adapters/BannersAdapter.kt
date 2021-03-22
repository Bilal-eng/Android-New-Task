package com.example.androidnewtask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.androidnewtask.R
import com.example.androidnewtask.model.HomeBannerModel
import com.example.androidnewtask.utils.Constants
import com.squareup.picasso.Picasso

class BannersAdapter(
    val context: Context?,
    private val listOfBanner: List<HomeBannerModel>?
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(context).inflate(R.layout.banner_item, container, false)


        val banner = listOfBanner?.get(position)

        val sliderImage = view.findViewById<ImageView>(R.id.ivBannerItem)

        val imageUrl = Constants.IMAGE_URL + banner?.path

        val mPicasso = Picasso.get()
        mPicasso
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(sliderImage)
        container.addView(view, 0)

        return view
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        if (listOfBanner != null)
            return listOfBanner.size
        return 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}