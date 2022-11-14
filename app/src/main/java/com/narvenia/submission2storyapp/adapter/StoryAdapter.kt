package com.narvenia.submission2storyapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narvenia.submission2storyapp.adapter.StoryAdapter.ListViewHolder
import com.narvenia.submission2storyapp.data.remote.response.ListStoryItem
import com.narvenia.submission2storyapp.databinding.ItemRowUserBinding
import com.narvenia.submission2storyapp.ui.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, ListViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.apply{
                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .circleCrop()
                    .into(ivAvatar)
                tvName.text = item.name
                tvDescription.text = item.description
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivAvatar, "avatar"),
                            Pair(tvName, "name"),
                            Pair(tvDescription, "description"),
                        )
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, item)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListStoryItem> =
            object : DiffUtil.ItemCallback<ListStoryItem>() {
                override fun areItemsTheSame(oldStory: ListStoryItem, newStory: ListStoryItem): Boolean {
                    return oldStory.name == newStory.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldStory: ListStoryItem, newStory: ListStoryItem): Boolean {
                    return oldStory == newStory
                }
            }
    }
}