package com.example.dummyuserlisting.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyuserlisting.R
import com.example.dummyuserlisting.data.entities.UserProps
import kotlinx.android.synthetic.main.user_list.view.*


class UserListAdapter : ListAdapter<UserProps, UserListAdapter.UserViewHolder>(UserListDataComparator()) {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.user_list, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bindTo(user)
        }
    }

     inner class UserViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        fun bindTo(item: UserProps) {

            view.tvAccept.setOnClickListener {
                itemView.tvAccept.text = "Accepted"
                itemView.tvDecline.visibility = View.GONE
                onItemClickListener?.onAcceptClick(getItem(absoluteAdapterPosition).email)
            }
            view.tvDecline.setOnClickListener{
                itemView.tvAccept.visibility = View.GONE
                itemView.tvDecline.text = "Declined"
                onItemClickListener?.onDeclineClick(getItem(absoluteAdapterPosition).email)
            }
            view.tvName.text = (item.email)
            if(item.accept.not() && item.reject.not()){
                view.tvAccept.visibility = View.VISIBLE
                view.tvDecline.visibility = View.VISIBLE
            }else{
                when {
                    item.accept -> {
                        itemView.tvAccept.text = "Accepted"
                        view.tvAccept.visibility = View.VISIBLE
                        view.tvDecline.visibility = View.GONE
                    }
                    item.reject -> {
                        itemView.tvDecline.text = "Declined"
                        view.tvDecline.visibility = View.VISIBLE
                        view.tvAccept.visibility = View.GONE
                    }
                }
            }
        }
     }

    class UserListDataComparator : DiffUtil.ItemCallback<UserProps>() {
        override fun areItemsTheSame(
            oldItem: UserProps,
            newItem: UserProps
        ): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(
            oldItem: UserProps,
            newItem: UserProps
        ): Boolean {
            return (oldItem.email == newItem.email)
        }
    }

    interface OnItemClickListener{
        fun onAcceptClick(id:String)
        fun onDeclineClick(id:String)
    }
}
