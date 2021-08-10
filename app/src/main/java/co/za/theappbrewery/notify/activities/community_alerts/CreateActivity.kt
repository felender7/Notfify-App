package co.za.theappbrewery.notify.activities.community_alerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.theappbrewery.notify.R

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setTheme(R.style.Theme_Notify)

        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "Create Alert".also { actionbar!!.title = it }

        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    //back button function
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}