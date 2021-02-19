package com.slngl.myrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.slngl.myrecipes.R
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager,
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.singleFoodImageView)
        fun setData(url: String) {
            itemView.apply {
                glide.load(url).into(imageView)
            }
            setOnItemClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
//            onItemClicked?.let {
//                //Unit nullable verdiğimizde
//                it(url)
//            }
        }
    }

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageRecyclerAdapter.ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ImageRecyclerAdapter.ImageViewHolder,
        position: Int
    ) {
        val url = images.get(position)
        holder.setData(url)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}