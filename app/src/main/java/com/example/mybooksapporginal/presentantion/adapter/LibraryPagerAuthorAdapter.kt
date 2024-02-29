package com.example.mybooksapporginal.presentantion.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.CategoryData
import com.example.mybooksapporginal.databinding.ItemCategoryBooksBinding


class LibraryPagerAuthorAdapter :
    ListAdapter<CategoryData, LibraryPagerAuthorAdapter.LibraryAuthorViewHolder>(LibraryDiffUtil) {
    private lateinit var clickAll: (CategoryData) -> Unit
    private lateinit var clickBookItem: (BookData) -> Unit


    object LibraryDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean =
            oldItem.categoryDocId == newItem.categoryDocId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean =
            oldItem == newItem
    }

    inner class LibraryAuthorViewHolder(private val binding: ItemCategoryBooksBinding) :
        ViewHolder(binding.root) {

        init {
            binding.all.setOnClickListener {
                clickAll.invoke(getItem(adapterPosition))
            }
        }

        fun bind(data: CategoryData) {
             val adapter = LibraryPagerInnerAdapter()
            binding.category.text = data.bookList[0].categoryName
            binding.rvListInner.adapter = adapter
            adapter.submitList(data.bookList)
            binding.rvListInner.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            adapter.setOnClickBookItem {
                clickBookItem.invoke(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAuthorViewHolder =
        LibraryAuthorViewHolder(
            ItemCategoryBooksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LibraryAuthorViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun setOnClickBookItem(block: (BookData) -> Unit) {
        clickBookItem = block
    }

    fun setOnClickAll(block: (CategoryData) -> Unit) {
        clickAll = block
    }

}