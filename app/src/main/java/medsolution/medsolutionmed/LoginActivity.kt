package medsolution.medsolutionmed

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login2.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        listeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
    }

    fun listeners() {
        login.setOnClickListener {
            val snack = Snackbar.make(it, "Aguarde", Snackbar.LENGTH_INDEFINITE)
            snack.show()
            login.isEnabled = false
            validateFields(it) { email1, password1 ->
                FirebaseUtils.auth.signInWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this, { task ->
                        if (task.isSuccessful) {
                            snack.dismiss()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = FLAG_ACTIVITY_NO_HISTORY
                            startActivity(intent)
                            finish()
                        } else {
                            login.isEnabled = true
                            Toast.makeText(
                                this@LoginActivity,
                                "Erro ao realizar login, verifique seus dados e tente novamente",
                                Toast.LENGTH_LONG
                            ).show()
                            snack.dismiss()
                        }
                    })
            }
        }
        signUp.setOnClickListener {
            startActivityForResult(Intent(this@LoginActivity, RegisterActivity::class.java), 1)
        }
    }

    fun validateFields(view: View, proceed: (email: String, senha: String) -> Unit) {
        if (email.editText?.text.toString().isNullOrBlank()) {
            Snackbar.make(view, "Email inválido", Snackbar.LENGTH_LONG).show()
            return
        }

        if (password.editText?.text.toString().isNullOrBlank()) {
            Snackbar.make(view, "Senha inválida", Snackbar.LENGTH_LONG).show()
            return
        }

        proceed(email.editText?.text.toString(), password.editText?.text.toString())
    }
}

