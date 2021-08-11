package co.za.theappbrewery.notify.activities.community_alerts

import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.cardview.widget.CardView
import co.za.theappbrewery.notify.R
import co.za.theappbrewery.notify.activities.base.BaseActivity
import co.za.theappbrewery.notify.common.Utils
import co.za.theappbrewery.notify.core.db.Community
import co.za.theappbrewery.notify.core.models.UploadComImageUrl
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import java.io.File


class CreateActivity : BaseActivity(){


    private var UserId:Int =  1
    private var date_created_at = System.currentTimeMillis()

    //Database reference
    private var mDatabaseRef: DatabaseReference? = null
    private var mStorageRef: StorageReference? = null

    //image
    private var PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: ImageView
    private var mImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Notify)
        setContentView(R.layout.activity_create)

        // Get database reference for community alerts
        mStorageRef = FirebaseStorage.getInstance().getReference("community_alerts_uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("community_alerts_uploads")

        val edit_name:EditText = findViewById(R.id.titleTxt)
        val edit_description:EditText=  findViewById(R.id.descriptionTxt)
        val edit_location:EditText = findViewById(R.id.locationTxt)
        val edit_date_created_at = System.currentTimeMillis()
        val pickedImage = findViewById<ImageView>(R.id.pickedImage)
        val btnAdd : ExtendedFloatingActionButton = findViewById(R.id.fabAddCommunity)
        val community = Community()
        val com_edit = intent.getSerializableExtra("EDIT") as Community?

        //Choose  image
        pickedImage.setOnClickListener {
            openFileChooser()
        }




        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "Create Alert".also { actionbar!!.title = it }

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)




        // Select Category
        val categories = resources.getStringArray(R.array.categories_for_create_community_alert)
        val dropDownAutoCompletetxtCat = findViewById<AutoCompleteTextView>(R.id.categoryDropDown)
        val arrayAdapterCategory = ArrayAdapter(this, R.layout.catagory_community_dropdown_item, categories)
        dropDownAutoCompletetxtCat.setAdapter(arrayAdapterCategory)

        // Select  Category
        val subCategories = resources.getStringArray(R.array.sub_categories_for_create_community_alert)
        val dropDownAutoCompletetxtSubCat = findViewById<AutoCompleteTextView>(R.id.subcategoryDropDown)
        val arrayAdapterSubCategory = ArrayAdapter(this, R.layout.sub_catagory_community_dropdown_item, subCategories)
        dropDownAutoCompletetxtSubCat.setAdapter(arrayAdapterSubCategory)


        btnAdd.setOnClickListener {

            //progress bar
            val progressBarDailog = ProgressDialog(this)
            progressBarDailog.setMessage("Saving Data...")
            progressBarDailog.setCancelable(false)

            val selectedCatValue: String = dropDownAutoCompletetxtCat.editableText.toString()
            val selectedSUBCatValue: String = dropDownAutoCompletetxtSubCat.editableText.toString()


            // Save information to database
            val fileReference = mStorageRef!!.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri!!))

            fileReference.putFile(mImageUri!!).addOnSuccessListener {
                fileReference.downloadUrl.addOnCompleteListener() { taskSnapshot ->
                    val imageUrl = taskSnapshot.result.toString()
                    println("url =$imageUrl")
                    val upload = UploadComImageUrl(imageUrl)
                    val uploadId = mDatabaseRef!!.push().key
                    if (uploadId != null) {
                        mDatabaseRef!!.child(uploadId).setValue(upload)}
                    if (progressBarDailog.isShowing) progressBarDailog.dismiss()

                    val com = Community()
                    when (com_edit) {
                        null -> {
                            community.add(com)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { er: Exception ->
                                    Toast.makeText(this, "" + er.message, Toast.LENGTH_SHORT).show()
                                }
                        }
                        else -> {

                            val hashMap: HashMap<String, Any> = HashMap()
                            hashMap["name"] = edit_name.text.toString()


                            community.update(com_edit.key, hashMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show()
                                    finish()
                                }.addOnFailureListener { er: Exception ->
                                    Toast.makeText(
                                        this,
                                        "" + er.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }


                }.addOnFailureListener {
                    if (progressBarDailog.isShowing) progressBarDailog.dismiss()

                }
            }

        }


    }



    //back button function
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // inflate menu options
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.new_item_menu, menu)
        return true
    }

    // list all Item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.viewAllMenuItem) {
            Utils.openActivity(
                this,
                ListActivity::class.java
            )
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    // function to open file from the phone
    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // activity results for the open file
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            Picasso.get().load(mImageUri).into(imageView)
        }
    }


    /**
     * Get file extentions
     */
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

}