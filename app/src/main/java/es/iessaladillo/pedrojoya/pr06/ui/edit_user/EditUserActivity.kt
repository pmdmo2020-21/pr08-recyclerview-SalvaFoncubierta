package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserViewModel
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserViewModelFactory
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

class EditUserActivity : AppCompatActivity() {

    companion object {
        const val USER: String = "USER"
        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, EditUserActivity::class.java)
                    .putExtra(USER, user)
        }
    }

    private val viewModel: EditUserViewModel by viewModels {
        EditUserViewModelFactory(user, Database, this)
    }

    private lateinit var user: User
    private lateinit var binding: UserActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiveData()
        setUpViews()
        addUserInfo()
    }

    private fun receiveData() {
        user = intent.getParcelableExtra(USER)!!
    }

    private fun setUpViews() {
        binding.imgHeader.setOnClickListener { viewModel.img }
    }

    private fun addUserInfo() {
        viewModel.newUser.observe(this)  {
            takeUserData(it)
        }
    }

    private fun takeUserData(user: User) {
        user.run {
            binding.txtName.text = Editable.Factory.getInstance().newEditable(user.name)
            binding.txtEmail.text = Editable.Factory.getInstance().newEditable(user.email)
            binding.txtPhone.text = Editable.Factory.getInstance().newEditable(user.phoneNumber)
            binding.txtAddress.text = Editable.Factory.getInstance().newEditable(user.address)
            binding.txtWeb.text = Editable.Factory.getInstance().newEditable(user.web)
            binding.imgHeader.loadUrl(photoUrl)
        }
    }

    // TODO: Código de la actividad.
    //  Ten en cuenta que la actividad debe recibir a través del intent
    //  con el que es llamado el objeto User corresondiente
    //  ...

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
        val user = User(viewModel.newUser.value!!.id, name, email, address, phone, web, img)
        viewModel.editUser(user)
        finish()
    }

}