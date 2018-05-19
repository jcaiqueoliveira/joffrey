package medsolution.medsolutionmed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.item_list_schedule.view.*
import medsolution.medsolutionmed.model.Pacient1
import medsolution.medsolutionmed.model.SchedulePacient

class HistoryActivity : AppCompatActivity() {

    lateinit var idClient: String

    companion object {
        val key = "HISTORY"
        fun startActivity(context: Context, pacient: Pacient1): Intent {
            val i = Intent(context, HistoryActivity::class.java)
            return i.putExtra(key, pacient)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Histórico"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init {
            idClient = it.id
            setupRv(options())
        }
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

    fun options() = FirebaseRecyclerOptions.Builder<SchedulePacient>()
        .setQuery(
            FirebaseDatabase.getInstance().reference.child("pacient_time_line").child(
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
                holder.itemView.radio.isChecked = true
            }
        }
        adapter.startListening()

        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)
        historyRecyclerView.adapter = adapter
    }

    fun init(func: (pacient: Pacient1) -> Unit) {
        val pacient = intent.getSerializableExtra(HistoryActivity.key) as Pacient1
        func.invoke(pacient)
    }
}
