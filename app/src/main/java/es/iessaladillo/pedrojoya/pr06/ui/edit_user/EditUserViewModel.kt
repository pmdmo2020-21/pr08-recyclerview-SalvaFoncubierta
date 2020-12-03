package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.ui.add_user.HEADERIMAGE
import java.util.*
import kotlin.random.Random.Default.nextInt

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class EditUserViewModel (oldUser: User, val dataSource: DataSource, savedStateHandle: SavedStateHandle): ViewModel(){

    private val _img: MutableLiveData<String> = savedStateHandle.getLiveData(HEADERIMAGE, oldUser.photoUrl)
    val img: LiveData<String> get() = _img

    private val _newUser: MutableLiveData<User> = MutableLiveData(oldUser)
    val newUser: LiveData<User> get() = _newUser

    fun editUser(user: User) {
        dataSource.updateUser(user)
    }

    fun changeImg() {
        _img.value = getRandomPhotoUrl()
    }

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${nextInt(100)}/400/300"

}
