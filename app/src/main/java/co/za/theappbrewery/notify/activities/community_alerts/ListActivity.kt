package co.za.theappbrewery.notify.activities.community_alerts


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.za.theappbrewery.notify.R
import co.za.theappbrewery.notify.common.Utils.openActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Notify)
        setContentView(R.layout.activity_list)

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "Community Alerts".also { actionbar!!.title = it }

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val btnAddCom = findViewById<ExtendedFloatingActionButton>(R.id.extended_fab_save_com_alert)
        btnAddCom.setOnClickListener{
            openActivity(
                this,
                CreateActivity::class.java
            )
            finish()
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
        menuInflater.inflate(R.menu.listings_page_menu, menu)
        val searchItem = menu.findItem(R.id.action_search_community_alert)
        return true

    }

    // add new Item

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_community_alert) {
            openActivity(
                this,
                CreateActivity::class.java
            )
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}