package com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.ItemUserRowBinding

class UsersAdapter(
    private  val list: MutableList<ItemsItem> = mutableListOf(),
    private val btnChoose: (ItemsItem) -> Unit
    ):
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>(){

    //ngeset data dari API Adapter
    fun setData(list: MutableList<ItemsItem>){
        this.list.clear()

        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class UserViewHolder( private val v: ItemUserRowBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind (item: ItemsItem){
            v.imagePhoto.load(item.avatarUrl)
            v.textUsername.text = item.login
            v.tvUrl.text = item.url

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            btnChoose(item)
        }

    }
    override fun getItemCount(): Int = list.size

}