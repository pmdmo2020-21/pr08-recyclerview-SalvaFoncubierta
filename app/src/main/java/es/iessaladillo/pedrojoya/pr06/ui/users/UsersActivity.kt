package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity
import kotlinx.android.synthetic.main.users_activity.*

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: UsersActivityBinding
    private val listAdapter: UsersActivityAdapter = UsersActivityAdapter().also {
        it.onEditListener = { position -> editUser(position)}
        it.onDeleteListener = { position -> deleteUser(position)}
    }

    private val viewModel: UsersActivityViewModel by viewModels {
        UsersActivityViewModelFactory(Database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        observeUsers()
    }

    private fun setUpViews() {
        setUpRecyclerView()
        binding.imgEmpty.setOnClickListener {navigateToAddUserActivity()}
        binding.lblEmptyView.setOnClickListener {navigateToAddUserActivity()}
    }

    private fun setUpRecyclerView() {
        lstUsers.run {
            setHasFixedSize(true)
            adapter = listAdapter
            layoutManager = GridLayoutManager(this@UsersActivity, resources.getInteger(R.integer.users_grid_columns))
        }
    }

    private fun updateList(newList: List<User>) {
        listAdapter.submitList(newList)
        binding.lblEmptyView.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
        binding.imgEmpty.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun observeUsers() {
        viewModel.users.observe(this) {
            updateList(it)
        }
    }

    private fun editUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        navigateToEditUserActivity(user)
    }

    private fun deleteUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        viewModel.delete(user)
    }


    private fun navigateToAddUserActivity() {
        val intent: Intent = AddUserActivity.newIntent(this)
        startActivity(intent)
    }

    private fun navigateToEditUserActivity(user: User) {
        val intent: Intent = EditUserActivity.newIntent(this, user)
        startActivity(intent)
    }

    // TODO: Código de la actividad.
    //  Ten en cuenta que el recyclerview se muestra en forma de grid,
    //  donde el número de columnas está gestionado
    //  por R.integer.users_grid_columns
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // FIN NO TOCAR

    fun onAddUser() {
        navigateToAddUserActivity()
    }


}