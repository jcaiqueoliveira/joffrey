package medsolution.medsolutionmed

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseUtils {

    val auth = FirebaseAuth.getInstance()
    val userToken = auth.currentUser?.uid
    val userName = auth.currentUser?.displayName ?: "Médico"

    var database = FirebaseDatabase.getInstance()
    var profile = database.getReference("profile_medical")

    val pacient = database.getReference("pacient_list")

    var query = FirebaseDatabase.getInstance()
        .reference
        .child("pacient_list")

    val query_task = FirebaseDatabase.getInstance().reference.child("tarefa")

    val pacient_schedule = database.getReference("schedule_pacient")

    val query_schedule = FirebaseDatabase.getInstance().reference.child("schedule_pacient")

    val paciente_time_line = FirebaseDatabase.getInstance().reference.child("pacient_time_line")

    val firebase_chat = FirebaseDatabase.getInstance().reference.child("chat")
}