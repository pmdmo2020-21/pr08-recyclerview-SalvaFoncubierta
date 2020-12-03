package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.observe
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivityViewModel
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivityViewModelFactory
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: UserActivityBinding
    private val viewModel: AddUserViewModel by viewModels {
        AddUserViewModelFactory(Database, this)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddUserActivity::class.java)
        }
    }

    // TODO: Código de la actividad.
    //  ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        setImage()
    }

    private fun setImage() {
        viewModel.img.observe(this) {
            binding.imgHeader.loadUrl(it)
        }
    }

    private fun setUpViews() {
        binding.imgHeader.setOnClickListener { viewModel.changeImg()}
    }

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // FIN NO TOCAR

    private fun onSave() {
        val name = binding.txtName.text.toString()
        val email = binding.txtEmail.text.toString()
        val phone = binding.txtPhone.text.toString()
        val address = binding.txtAddress.text.toString()
        val web = binding.txtWeb.text.toString()
        val img = viewModel.img.value ?: ""
        val user = User(0, name, email, address, phone, web, img)
        viewModel.saveUser(user)
        finish()
    }

}