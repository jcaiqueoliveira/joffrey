package medsolution.medsolutionmed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_layout_medical.view.*
import medsolution.gone
import medsolution.medsolutionmed.model.Chat
import medsolution.medsolutionmed.model.Pacient1
import medsolution.visible

class ChatActivity : AppCompatActivity() {

    lateinit var pacient: Pacient1
    lateinit var myID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myID = FirebaseUtils.auth.currentUser?.uid ?: "0"

        init {
            pacient = it
            FirebaseUtils.firebase_chat.child(it.id)
            //toFeedASection()
            setupRv(options())
        }
    }

    fun init(func: (pacient: Pacient1) -> Unit) {
        val pacient = intent.extras.getSerializable("data_pacient") as Pacient1
        func(pacient)
    }

    fun options() = FirebaseRecyclerOptions.Builder<Chat>()
        .setQuery(
            FirebaseDatabase.getInstance().reference.child("chat").child(myID).child(pacient.id),
            Chat::class.java
        )
        .build()

    fun setupRv(options: FirebaseRecyclerOptions<Chat>) {

        val adapter = object : FirebaseRecyclerAdapter<Chat, ViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_medical, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: ViewHolder,
                position: Int,
                model: Chat
            ) {
                if (model.id == myID) {
                    holder.itemView.textMedical.visible()
                    holder.itemView.textMedical.text = model.message
                    holder.itemView.textPatience.gone()
                } else {
                    holder.itemView.textMedical.gone()
                    holder.itemView.textPatience.visible()
                    holder.itemView.textPatience.text = model.message
                }
            }
        }
        adapter.startListening()

        chatList.layoutManager = LinearLayoutManager(this)
        chatList.setHasFixedSize(true)
        chatList.adapter = adapter
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

    fun toFeedASection() {

        for (i in 1..1) {

            val id2 = FirebaseUtils.pacient_schedule.push().key
            Chat().apply {
                this.id = pacient.id
                this.message = "Olá doutor eu estou bem"
                FirebaseDatabase.getInstance().reference.child("chat").child(myID).child(pacient.id)
                    .child(id2)
                    .setValue(this)
            }

            val id = FirebaseUtils.pacient_schedule.push().key
            Chat().apply {
                this.id = myID
                this.message = "Olá meu querido paciente como vai"
                FirebaseDatabase.getInstance().reference.child("chat").child(myID).child(pacient.id)
                    .child(id)
                    .setValue(this)
            }
        }
    }
}
