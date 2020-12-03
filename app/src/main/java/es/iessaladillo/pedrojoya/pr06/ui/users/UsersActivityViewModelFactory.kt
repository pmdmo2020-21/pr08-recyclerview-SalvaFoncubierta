package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr06.data.DataSource


class UsersActivityViewModelFactory(val dataSource: DataSource) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            UsersActivityViewModel(dataSource) as T
}