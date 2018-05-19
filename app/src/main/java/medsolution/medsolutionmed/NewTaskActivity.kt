package medsolution.medsolutionmed

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
import medsolution.medsolutionmed.model.Task

class NewTaskActivity : AppCompatActivity() {

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
                    if (model.name.toLowerCase() == "medicamento") {
                        startActivity(
                            Intent(
                                this@NewTaskActivity,
                                TaskMedicationActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        startActivity(
                            Intent(
                                this@NewTaskActivity,
                                TaskActivity::class.java
                            )
                        )
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
}
