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
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.item_head_new_task.view.*
import medsolution.medsolutionmed.model.Pacient1
import medsolution.medsolutionmed.model.Task

class NewTaskActivity : AppCompatActivity() {

    lateinit var idClient: String

    companion object {
        val key = "NEW_HISTORY"
        fun startActivity(context: Context, pacient: Pacient1): Intent {
            val i = Intent(context, NewTaskActivity::class.java)
            return i.putExtra(key, pacient)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setSupportActionBar(toolbar_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nova Tarefa"

        val options = FirebaseRecyclerOptions.Builder<Task>()
            .setQuery(FirebaseUtils.query_task, Task::class.java)
            .build()
        setupRv(options)
        init {
            idClient = it.id
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

    fun setupRv(options: FirebaseRecyclerOptions<Task>) {

        val adapter = object : FirebaseRecyclerAdapter<Task, ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_head_new_task, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: ViewHolder,
                position: Int,
                model: Task
            ) {
                holder.itemView.task.text = model.name
                holder.itemView.root3.setOnClickListener {
                    if (model.name.toLowerCase() == "medicação") {
                        val i = Intent(this@NewTaskActivity, TaskMedicationActivity::class.java)
                        i.putExtra("STRING_I_NEED", model.name)
                        i.putExtra("user_id", idClient)
                        startActivity(i)
                        finish()
                    } else {
                        val i = Intent(this@NewTaskActivity, TaskActivity::class.java)
                        i.putExtra("STRING_I_NEED", model.name)
                        i.putExtra("user_id", idClient)
                        startActivity(i)
                        finish()
                    }
                }
            }
        }
        adapter.startListening()

        listTask.layoutManager = LinearLayoutManager(this)
        listTask.setHasFixedSize(true)
        listTask.adapter = adapter
    }

    fun init(func: (pacient: Pacient1) -> Unit) {
        val pacient = intent.getSerializableExtra(NewTaskActivity.key) as Pacient1
        func.invoke(pacient)
    }
}
