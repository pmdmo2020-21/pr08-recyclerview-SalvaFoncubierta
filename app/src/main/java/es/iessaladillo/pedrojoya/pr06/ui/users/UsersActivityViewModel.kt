package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity

class UsersActivityViewModel (val dataSource: DataSource): ViewModel() {

    private val _users: MutableLiveData<User> = MutableLiveData()
    val users: LiveData<List<User>> = dataSource.getAllUsersOrderedByName()

    fun edit(user: User) {
        val newUser: User = user.copy()
        dataSource.updateUser(newUser)
    }

    fun delete(user: User) {
        dataSource.deleteUser(user)
    }

}
