package medsolution.medsolutionmed

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatSpinner
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_task.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.View.OnFocusChangeListener




class TaskActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var textview_date: TextInputEditText? = null
//    var spinner_turno: AppCompatSpinner?=null
//    var spinner_SetorResponsavel: AppCompatSpinner?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val toolbar_task = toolbar_task

        setSupportActionBar(toolbar_task)

        this.textview_date = this.text_data_procedimento


        val arraySpinnerTurno = arrayOf("Manhã","Tarde","Madrugada")

        val arraySpinnerSetorResponsavel = arrayOf("Enfermagem","Fisioterapia","Psicologia")

        val  adapterTurno= ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, arraySpinnerTurno)

        val adapterSetorResponsavel =ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line
                , arraySpinnerSetorResponsavel)

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        if (textview_date!!.isFocusable) {

        }


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
       textview_date!!.setOnClickListener(object : View.OnClickListener {
           override fun onClick(view: View) {
               DatePickerDialog(this@TaskActivity,
                       dateSetListener,
                       // set DatePickerDialog to point to today's date when it loads up
                       cal.get(Calendar.YEAR),
                       cal.get(Calendar.MONTH),
                       cal.get(Calendar.DAY_OF_MONTH)).show()

           }
       })

        this.spinner_turno!!.adapter = adapterTurno

        this.spinner_setor_responsavel!!.adapter = adapterSetorResponsavel

    }


    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.setText(sdf.format(cal.getTime()))
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Salvar a tarefa.
        return super.onCreateOptionsMenu(menu)
    }
}
