package es.iessaladillo.pedrojoya.pr06.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre

object Database: DataSource {

    private val listUsers: MutableList<User> = mutableListOf()
    private val listUsersLiveData: MutableLiveData<List<User>> = MutableLiveData(listUsers.toList())

    override fun getAllUsersOrderedByName(): LiveData<List<User>> {
        listUsersLiveData.value = listUsers.sortedBy { it.name }
        return listUsersLiveData
    }

    override fun insertUser(user: User) {
        listUsers.add(user)
        updateUserLiveData()
    }

    override fun updateUser(user: User) {
        val position = listUsers.indexOfFirst { it.id == user.id}
        if(position>=0) {
            listUsers[position] = user
            updateUserLiveData()
        }
    }

    override fun deleteUser(user: User) {
        val position = listUsers.indexOfFirst { it.id == user.id}
        if(position>=0) {
            listUsers.removeAt(position)
            updateUserLiveData()
        }
    }

    private fun updateUserLiveData() {
        listUsersLiveData.value = listUsers.toList()
    }

}
