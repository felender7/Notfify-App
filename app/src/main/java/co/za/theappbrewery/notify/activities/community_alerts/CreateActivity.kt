package co.za.theappbrewery.notify.activities.community_alerts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import co.za.theappbrewery.notify.R
import co.za.theappbrewery.notify.common.Utils
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class CreateActivity : AppCompatActivity() {

   private var  current_p:Int? = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Notify)
        setContentView(R.layout.activity_create)

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



}