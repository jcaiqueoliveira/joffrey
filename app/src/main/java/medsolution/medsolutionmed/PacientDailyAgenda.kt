package medsolution.medsolutionmed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_pacient_daily_agenda.*
import kotlinx.android.synthetic.main.item_list_schedule.view.*
import medsolution.medsolutionmed.model.Pacient1
import medsolution.medsolutionmed.model.SchedulePacient

class PacientDailyAgenda : AppCompatActivity() {

    lateinit var idClient: String

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
        init {
            idClient = it.id
            imgLetter.letter = it.name
            namePacient1.text = it.name
            bed.text = it.bed
            diagnostic.text = it.diagnost
            gender.text = it.gender
            date.text = it.date
            //toFeedASection()
            setupRv(options())
        }
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
}