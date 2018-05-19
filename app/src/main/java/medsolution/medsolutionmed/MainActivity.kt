package medsolution.medsolutionmed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_pacientes.*
import medsolution.medsolutionmed.model.Pacient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pacientes)

        val recycler_lista_pacientes = recycler_lista_pacientes
        recycler_lista_pacientes.adapter = ListaPacientesAdapter(listaPacientes(), this)
    }

    private fun listaPacientes(): List<Pacient>{
        return listOf(
                Pacient("Gabriel Menezes da Silva","LeitoG305"),
                Pacient("Tiago de Mello Britto","LeitoB405")
        )
    }

}
