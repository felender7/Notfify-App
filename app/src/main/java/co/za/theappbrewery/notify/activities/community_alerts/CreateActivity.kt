package co.za.theappbrewery.notify.activities.community_alerts

import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import co.za.theappbrewery.notify.R
import co.za.theappbrewery.notify.activities.base.BaseActivity
import co.za.theappbrewery.notify.common.Utils
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import java.io.File


class CreateActivity : BaseActivity(){

    //image
    private var resumedAfterImagePicker = false
    private var chosenFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Notify)
        setContentView(R.layout.activity_create)

        handleEvents()

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "Create Alert".also { actionbar!!.title = it }

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val btn = findViewById<ExtendedFloatingActionButton>(R.id.extended_fab_save_com_alert)

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


        btn.setOnClickListener {
            val selectedCatValue: String = dropDownAutoCompletetxtCat.editableText.toString()
            Toast.makeText(applicationContext, selectedCatValue, Toast.LENGTH_SHORT).show()

            val selectedSUBCatValue: String = dropDownAutoCompletetxtSubCat.editableText.toString()
            Toast.makeText(applicationContext, selectedSUBCatValue, Toast.LENGTH_SHORT).show()

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

    /**
     * Capture or select image
     */
    private fun captureImage() {
        val i = Intent(this, ImageSelectActivity::class.java)
        i.putExtra(ImageSelectActivity.FLAG_COMPRESS, false) //default is true
        i.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
        i.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true
        startActivityForResult(i, 1213)
    }

    /**
     * After capturing or selecting image, we will get the image path
     * and use it to instantiate a file object
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1213) {
                val filePath =
                    data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
                chosenFile = File(filePath)
                var  topImageImg = findViewById<ImageView>(R.id.topImageImg)
                Picasso.get().load(chosenFile!!).error(R.drawable.image_not_found).into(topImageImg)
            }
        }
        resumedAfterImagePicker = true
    }

    /**
     * We use a library known as Dexter to check for permissions at
     * runtime.If we haven't been granted then we present the user with
     * a dialog to take him to settings page to grant us permission
     */
    private fun checkPermissionsThenPickImage() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    show("Good...READ EXTERNAL PERMISSION GRANTED")
                    captureImage()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    show("WHOOPS! PERMISSION DENIED: Please grant permission first")
                    if (response.isPermanentlyDenied) {
                        showSettingDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    Log.i("DEXTER PERMISSION", "Permission Rationale Should be shown")
                    token.continuePermissionRequest()
                }
            }).check()
    }

    /**
     * Lets handle events and Show our single choice dialogs
     */
    private fun handleEvents() {

        val  pickedImage = findViewById<CardView>(R.id.pickedImage)
        pickedImage.setOnClickListener { checkPermissionsThenPickImage() }

    }

}