package medsolution.medsolutionmed

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_task_medicamento.*
import java.text.SimpleDateFormat
import java.util.*

class TaskMedicationActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var textview_date: TextInputEditText? = null
//    var spinner_turno: AppCompatSpinner?=null
//    var spinner_SetorResponsavel: AppCompatSpinner?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nova Tarefa"

        val toolbar_task = toolbar_task_medicamento

        setSupportActionBar(toolbar_task)

        this.textview_date = this.text_data_procedimento_medicamento

        val arraySpinnerSetorResponsavel = arrayOf("Enfermagem", "Fisioterapia", "Psicologia")

        val adapterSetorResponsavel = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line
            , arraySpinnerSetorResponsavel
        )

        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            text_horario.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }

        text_horario.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textview_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@TaskMedicationActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        this.spinner_setor_responsavel_medicamento!!.adapter = adapterSetorResponsavel
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.setText(sdf.format(cal.getTime()))
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
}
