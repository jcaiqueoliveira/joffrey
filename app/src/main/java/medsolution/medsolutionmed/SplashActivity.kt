package medsolution.medsolutionmed

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*
import medsolution.gone

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkLogin()
        }, 1000)
    }

    fun checkLogin() {
        /* quando current user é nulo estamos deslogados */
        val mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        progressBar.gone()
        finish()
    }
}