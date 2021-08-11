package co.za.theappbrewery.notify.core.db

import co.za.theappbrewery.notify.core.models.Community
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.HashMap


//db queries for community
class dbCommunity{

    private val databaseReference: DatabaseReference

    // Add Community alert
    fun add(community:dbCommunity?): Task<Void> {
        return databaseReference.push().setValue(community)
    }

    //Update Community alert
    fun update(key: String?, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReference.child(key!!).updateChildren(hashMap!!)
    }

    // Delete Community alert
    fun remove(key: String?): Task<Void> {
        return databaseReference.child(key!!).removeValue()
    }

    operator fun get(key: String?): Query {
        return if (key == null) {
            databaseReference.orderByKey().limitToFirst(8)
        } else databaseReference.orderByKey().startAfter(key).limitToFirst(8)
    }


    // Get data from the table
    fun get(): Query {
        return databaseReference
    }

    init {
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference(Community::class.java.simpleName)
    }
}