package com.ankit.todotask.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ankit.todotask.R
import com.ankit.todotask.databinding.ItemProductsBinding
import com.ankit.todotask.models.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ProductAdapter : ListAdapter<Product, ProductAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(item: Product?) {
            binding.apply {
                txtDesc.text = item?.description
                txtTitle.text = item?.title
                txtPrice.text = "₹${item?.price}"
                txtDiscount.text = "${item?.discountPercentage}% off"
                txtDiscount.setTextColor(ContextCompat.getColor(txtDiscount.context, R.color.green))
                rating.rating = item?.rating?.toFloat() ?: 0f
                if (item?.isComplete == true) {
                    imgDone.visibility = View.GONE
                } else {
                    imgDone.visibility = View.VISIBLE
                }
                Glide.with(imgThumbnail).load(item?.thumbnail)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            progress.visibility = View.GONE
                            return false
                        }

                    }).into(imgThumbnail)

            }
        }

        init {
            binding.imgDone.setOnClickListener {
                val position = adapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                val item = getItem(position)
                onDone?.invoke(item)
            }
            binding.imgDelete.setOnClickListener {
                val position = adapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                val item = getItem(position)
                onDelete?.invoke(item)
            }
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                val item = getItem(position)
                onEdit?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }

    var onDone: ((Product) -> Unit)? = null
    var onDelete: ((Product) -> Unit)? = null
    var onEdit: ((Product) -> Unit)? = null
}

class Diff : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.isComplete == newItem.isComplete
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}