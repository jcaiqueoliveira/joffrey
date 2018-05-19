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
}