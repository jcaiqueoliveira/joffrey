package medsolution.medsolutionmed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.activity_lista_pacientes.*
import kotlinx.android.synthetic.main.item_header.view.*

class NewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        val options = FirebaseRecyclerOptions.Builder<String>()
            .setQuery(FirebaseUtils.query, String::class.java)
            .build()

        setupRv(options)
    }

    fun setupRv(options: FirebaseRecyclerOptions<String>) {

        val adapter = object : FirebaseRecyclerAdapter<String, ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_head_new_task, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: ViewHolder,
                position: Int,
                model: String
            ) {
                holder.itemView.task_name.text = model
                holder.itemView.setOnClickListener {

                }
            }
        }
        adapter.startListening()

        pacientList.layoutManager = LinearLayoutManager(this)
        pacientList.setHasFixedSize(true)
        pacientList.adapter = adapter
    }
}
