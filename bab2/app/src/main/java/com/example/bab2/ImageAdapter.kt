package com.example.bab2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context) : BaseAdapter() {

    private val images = intArrayOf(
        android.R.drawable.ic_menu_camera,
        android.R.drawable.ic_menu_gallery,
        android.R.drawable.ic_menu_call,
        android.R.drawable.ic_menu_compass,
        android.R.drawable.ic_menu_day,
        android.R.drawable.ic_menu_manage
    )

    override fun getCount(): Int = images.size

    override fun getItem(position: Int): Any = images[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView = convertView as? ImageView ?: ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(220, 220)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        imageView.setImageResource(images[position])
        return imageView
    }
}
