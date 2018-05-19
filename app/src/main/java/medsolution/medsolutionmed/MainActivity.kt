package medsolution.medsolutionmed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lista_pacientes.*
import kotlinx.android.synthetic.main.lista_pacientes_celula.view.*
import medsolution.medsolutionmed.model.Pacient1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pacientes)
        supportActionBar?.title = FirebaseUtils.userName

        FirebaseUtils.profile.addListenerForSingleValueEvent(name())

        val options = FirebaseRecyclerOptions.Builder<Pacient1>()
            .setQuery(FirebaseUtils.query, Pacient1::class.java)
            .build()
        setupRv(options)

//        for (i in 1..10) {
//            val key = FirebaseUtils.pacient.push().key
//            Pacient1().apply {
//                name = "Paciente $i"
//                bed = "Leito $i"
//                diagnost = "febre"
//                id = key
//                FirebaseUtils.pacient.child(key).setValue(this)
//            }
//        }
    }

    fun name() = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val title = dataSnapshot.getValue<String>(String::class.java)
            supportActionBar?.title = title
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    fun setupRv(options: FirebaseRecyclerOptions<Pacient1>) {

        val adapter = object : FirebaseRecyclerAdapter<Pacient1, ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                if (viewType == 0) {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_header, parent, false)
                    return ViewHolder(view)
                } else {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.lista_pacientes_celula, parent, false)
                    return ViewHolder(view)
                }
            }

            override fun getItemViewType(position: Int): Int {
                return if (position == 0) 0 else 1
            }

            override fun onBindViewHolder(
                holder: ViewHolder,
                position: Int,
                model: Pacient1
            ) {
                holder.itemView.image_paciente.letter = model.name
                holder.itemView.namePacient.text = model.name
                holder.itemView.leito.text = model.bed
                holder.itemView.diagnostic.text = model.diagnost

                if (position.rem(2) == 0) {
                    holder.itemView.image_paciente.shapeColor =
                        holder.itemView.context.resources.getColor(R.color.colorPrimary)
                } else {
                    holder.itemView.image_paciente.shapeColor =
                        holder.itemView.context.resources.getColor(android.R.color.black)
                }

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
