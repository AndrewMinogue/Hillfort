package assignment.hillfort.models.firebase

import android.content.Context
import android.graphics.Bitmap
import assignment.hillfort.helpers.readImageFromPath
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.HillfortStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference
    var totalUsers: Long = 0
    var totalHillforts: Long = 0

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { p -> p.id == id }
        return foundHillfort
    }

    override fun create(hillfort: HillfortModel) {
        val key = db.child("users").child(userId).child("hillforts").push().key
        key?.let {
            hillfort.fbId = key
            hillforts.add(hillfort)
            db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
        }
    }

    override fun sortedByFavourite(): List<HillfortModel>? {
        return hillforts.filter { it.favourite }
//        return hillforts.sortedWith(compareBy { it.favourite }).asReversed()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.location = hillfort.location
            foundHillfort.visited = hillfort.visited
            foundHillfort.favourite = hillfort.favourite
        }

        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
        if ((hillfort.image.length) > 0 && (hillfort.image[0] != 'h')) {
            updateImage(hillfort)
        }
        if ((hillfort.image1.length) > 0 && (hillfort.image1[0] != 'h')) {
            updateImage1(hillfort)
        }
        if ((hillfort.image2.length) > 0 && (hillfort.image2[0] != 'h')) {
            updateImage2(hillfort)
        }
        if ((hillfort.image3.length) > 0 && (hillfort.image3[0] != 'h')) {
            updateImage3(hillfort)
        }

    }

    override fun delete(hillfort: HillfortModel) {
        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
        hillforts.remove(hillfort)
    }

    override fun clear() {
        hillforts.clear()
    }

    fun fetchHillforts(hillfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java)
                }
                for(count in dataSnapshot.children) {
                    totalHillforts += count.child("hillforts").childrenCount
                }
                hillfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        hillforts.clear()
        db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(hillfort: HillfortModel) {
        if (hillfort.image != "") {
            val fileName = File(hillfort.image)
            val imageName = fileName.getName()
            st = FirebaseStorage.getInstance().reference

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }

    fun totalHillforts():Long {
        return totalHillforts
    }


    fun updateImage1(hillfort: HillfortModel) {
        if (hillfort.image1 != "") {
            val fileName = File(hillfort.image1)
            val imageName = fileName.getName()
            st = FirebaseStorage.getInstance().reference

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image1)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }
    fun updateImage2(hillfort: HillfortModel) {
        if (hillfort.image2 != "") {
            val fileName = File(hillfort.image2)
            val imageName = fileName.getName()
            st = FirebaseStorage.getInstance().reference

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image2)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image2 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }
    fun updateImage3(hillfort: HillfortModel) {
        if (hillfort.image3 != "") {
            val fileName = File(hillfort.image3)
            val imageName = fileName.getName()
            st = FirebaseStorage.getInstance().reference

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, hillfort.image3)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        hillfort.image3 = it.toString()
                        db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
                    }
                }
            }
        }
    }
}