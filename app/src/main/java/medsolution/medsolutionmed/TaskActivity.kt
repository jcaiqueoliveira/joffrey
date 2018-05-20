package medsolution.medsolutionmed

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task.*
import medsolution.medsolutionmed.model.SchedulePacient
import java.text.SimpleDateFormat
import java.util.*

class TaskActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var textview_date: TextInputEditText? = null

    lateinit var idClient: String
    lateinit var ocurrenceType1: String
//    var spinner_turno: AppCompatSpinner?=null
//    var spinner_SetorResponsavel: AppCompatSpinner?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        setSupportActionBar(toolbar_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nova Tarefa"

        this.textview_date = this.text_data_procedimento

        val arraySpinnerTurno = arrayOf("Manhã", "Tarde", "Madrugada")

        val arraySpinnerSetorResponsavel = arrayOf("Enfermagem", "Fisioterapia", "Psicologia")

        val adapterTurno = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, arraySpinnerTurno
        )

        val adapterSetorResponsavel = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line
            , arraySpinnerSetorResponsavel
        )

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        if (textview_date!!.isFocusable) {

        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textview_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@TaskActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        this.spinner_turno!!.adapter = adapterTurno

        this.spinner_setor_responsavel!!.adapter = adapterSetorResponsavel
        init()
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.setText(sdf.format(cal.getTime()))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu)
        //Salvar item no firebase -> schedule_pacient

        //Action -> Procedimento
        //ocurrenceType -> Tela anterior.

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                finish()
                return true
            }

            R.id.menu_salvar -> {
                if (text_procedimento.text.toString().isNullOrBlank()) {
                    Toast.makeText(this, "Preencha o procedimento corretamente", Toast.LENGTH_LONG)
                        .show()
                }

                val key = FirebaseUtils.pacient.push().key
                SchedulePacient().apply {
                    ocurrenceType = ocurrenceType1
                    idSchedule = key
                    time = spinner_turno.selectedItem.toString()
                    action = text_procedimento.text.toString()
                    FirebaseUtils.query_schedule.child(idClient).child(key).setValue(this)
                    Toast.makeText(this@TaskActivity, "Adicionado", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun init() {
        ocurrenceType1 = intent.extras.getString("STRING_I_NEED")
        idClient = intent.extras.getString("user_id")
    }
}



