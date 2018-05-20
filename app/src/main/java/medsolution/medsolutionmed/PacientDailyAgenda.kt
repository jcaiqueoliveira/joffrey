package medsolution.medsolutionmed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_pacient_daily_agenda.*
import kotlinx.android.synthetic.main.item_list_schedule.view.*
import medsolution.gone
import medsolution.medsolutionmed.model.Pacient1
import medsolution.medsolutionmed.model.SchedulePacient
import medsolution.visible

class PacientDailyAgenda : AppCompatActivity() {

    lateinit var idClient: String
    lateinit var paciente23: Pacient1

    companion object {
        val key = "PACIENT"
        fun startActivity(context: Context, pacient: Pacient1): Intent {
            val i = Intent(context, PacientDailyAgenda::class.java)
            return i.putExtra(key, pacient)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacient_daily_agenda)
        setSupportActionBar(toolbarDaily)
        supportActionBar?.title = "Paciente"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init { pacient ->
            idClient = pacient.id
            paciente23 = pacient
            imgLetter.letter = pacient.name
            namePacient1.text = pacient.name
            bed.text = pacient.bed
            diagnostic.text = pacient.diagnost
            gender.text = pacient.gender
            date.text = pacient.date
            // toFeedASection()
            setupRv(options())

            historyListener(pacient)
            listenerSchedule(pacient)

            newTask.setOnClickListener {
                val intent = NewTaskActivity.startActivity(this, pacient)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_chat -> {
                val intent = Intent(this@PacientDailyAgenda, ChatActivity::class.java)
                intent.putExtra("data_pacient", paciente23)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun init(func: (pacient: Pacient1) -> Unit) {
        val pacient = intent.getSerializableExtra(key) as Pacient1
        func.invoke(pacient)
    }

    fun toFeedASection() {

        for (i in 1..3) {
            val id = FirebaseUtils.pacient_schedule.push().key
            SchedulePacient().apply {
                time = if (i <= 2) "Manhã" else "Tarde"
                ocurrenceType = if (i == 3) "Medicamento" else "Atendimento"
                idSchedule = id
                action = if (i == 1) "Coleta" else if (i == 2) "Tomografia" else "Morfina"
                FirebaseUtils.pacient_schedule.child(idClient).child(id).setValue(this)
            }
        }
    }

    fun options() = FirebaseRecyclerOptions.Builder<SchedulePacient>()
        .setQuery(
            FirebaseDatabase.getInstance().reference.child("schedule_pacient").child(
                idClient
            ), SchedulePacient::class.java
        )
        .build()

    fun setupRv(options: FirebaseRecyclerOptions<SchedulePacient>) {

        val adapter = object : FirebaseRecyclerAdapter<SchedulePacient, ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_schedule, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: ViewHolder,
                position: Int,
                model: SchedulePacient
            ) {
                holder.itemView.ocurrenceType.text = model.ocurrenceType
                holder.itemView.action.text = model.action
                holder.itemView.timeValue.text = model.time
                holder.itemView.rootL.setOnClickListener {
                    FirebaseDatabase.getInstance().reference.child("schedule_pacient")
                        .child(idClient).child(model.idSchedule).setValue(null)
                    moveScheduleToPacientHistory(model)
                }
            }
        }
        adapter.startListening()

        schedule.layoutManager = LinearLayoutManager(this)
        schedule.setHasFixedSize(true)
        schedule.adapter = adapter
    }

    fun moveScheduleToPacientHistory(schedule: SchedulePacient) {

        FirebaseUtils.paciente_time_line.child(idClient).child(schedule.idSchedule)
            .setValue(schedule)
    }

    fun historyListener(paciente: Pacient1) {
        FirebaseUtils.paciente_time_line.child(paciente.id).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val count = dataSnapshot.childrenCount.toInt()
                if (count > 0) {
                    historicSchedule.isEnabled = true
                    historicSchedule.text = "Histórico da agenda"
                    historicSchedule.setOnClickListener { _ ->
                        startActivity(
                            HistoryActivity.startActivity(
                                this@PacientDailyAgenda,
                                paciente
                            )
                        )
                    }
                } else {
                    historicSchedule.isEnabled = false
                    historicSchedule.text = "Paciente sem histórico"
                }
            }
        })
    }

    fun listenerSchedule(pacient: Pacient1) {
        FirebaseUtils.pacient_schedule.child(pacient.id).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val count = dataSnapshot.childrenCount.toInt()
                if (count > 0) {
                    emptyState.gone()
                    schedule.visible()
                } else {
                    emptyState.visible()
                    schedule.gone()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pacient, menu)
        //Salvar item no firebase -> schedule_pacient

        //Action -> Procedimento
        //ocurrenceType -> Tela anterior.
        return true
    }
}
