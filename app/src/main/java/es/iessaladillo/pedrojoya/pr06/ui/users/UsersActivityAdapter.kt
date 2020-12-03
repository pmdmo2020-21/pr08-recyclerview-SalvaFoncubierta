package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_activity.*
import kotlinx.android.synthetic.main.users_activity_item.*
import kotlinx.android.synthetic.main.users_activity_item.view.*


class UsersActivityAdapter() : ListAdapter<User, UsersActivityAdapter.ViewHolder>(UserDiffCallback) {

    var onEditListener: ((Int)-> Unit)? = null
    var onDeleteListener: ((Int)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.users_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = currentList[position]
        holder.bind(user)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            btnEditUser.setOnClickListener {
                onEditListener?.invoke(absoluteAdapterPosition)
            }
            btnDeleteUser.setOnClickListener {
                onDeleteListener?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(user: User) {
            user.run{
                containerView.lblNameUser.text = name
                containerView.lblEmailUser.text = email
                containerView.lblPhoneUser.text = phoneNumber
                containerView.imgUser.loadUrl(photoUrl)
            }
        }
    }

    object UserDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

    }

}