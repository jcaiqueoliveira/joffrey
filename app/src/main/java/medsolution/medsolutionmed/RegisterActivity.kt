package medsolution.medsolutionmed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun listeners() {
        register.setOnClickListener {
            val snack = Snackbar.make(it, "Aguarde", Snackbar.LENGTH_INDEFINITE)
            snack.show()
            register.isEnabled = false
            validateFields(it) { email1, password1 ->
                FirebaseUtils.auth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this, { task ->
                        if (task.isSuccessful) {
                            saveName(name.editText!!.text.toString())
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                            startActivity(intent)
                            finish()
                        } else {
                            register.isEnabled = true
                            Toast.makeText(
                                this@RegisterActivity,
                                "Erro ao realizar login, verifique seus dados e tente novamente",
                                Toast.LENGTH_LONG
                            ).show()
                            snack.dismiss()
                        }
                    })
            }
        }
    }

    fun validateFields(view: View, proceed: (email: String, senha: String) -> Unit) {
        if (email.editText?.text.toString().isNullOrBlank()) {
            Snackbar.make(view, "Email inválido", Snackbar.LENGTH_LONG).show()
            register.isEnabled = true
            return
        }

        if (password.editText?.text.toString().isNullOrBlank()) {
            register.isEnabled = true
            Snackbar.make(view, "Senha inválida", Snackbar.LENGTH_LONG).show()
            return
        }

        if (name.editText?.text.toString().isNullOrBlank()) {
            register.isEnabled = true
            Snackbar.make(view, "Senha inválida", Snackbar.LENGTH_LONG).show()
            return
        }

        proceed(email.editText?.text.toString(), password.editText?.text.toString())
    }

    fun saveName(name: String) {
        FirebaseUtils.profile.setValue(name)
        setResult(Activity.RESULT_OK)
        finish()
    }
}
