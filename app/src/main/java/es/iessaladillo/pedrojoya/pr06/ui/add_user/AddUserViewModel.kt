package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import java.util.*
import androidx.lifecycle.SavedStateHandle
import es.iessaladillo.pedrojoya.pr06.data.model.User
import kotlin.random.Random.Default.nextInt

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

const val HEADERIMAGE = "HEADERIMAGE"

class AddUserViewModel(val dataSource: DataSource,savedStateHandle: SavedStateHandle): ViewModel() {

    private val _img: MutableLiveData<String> = savedStateHandle.getLiveData(HEADERIMAGE, getRandomPhotoUrl())
    val img: LiveData<String> get() = _img

    private var sumId: Long = 1

    fun changeImg() {
        _img.value = getRandomPhotoUrl()
    }

    fun saveUser(user: User) {
        val userCopy: User = user.copy(id = sumId++)
        dataSource.insertUser(userCopy)
    }

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${nextInt(100)}/400/300"

}
