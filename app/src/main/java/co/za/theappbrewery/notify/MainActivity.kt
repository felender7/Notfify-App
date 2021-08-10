package co.za.theappbrewery.notify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import co.za.theappbrewery.notify.activities.community_alerts.ListActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Notify)
        setContentView(R.layout.activity_main)

        //Get components
        val addCommunityCard = findViewById<CardView>(R.id.addCommunity)
        val addEmergencyCard = findViewById<CardView>(R.id.addEmergency)
        val addPromotionCard = findViewById<CardView>(R.id.addPromotions)
        val addMissingCard = findViewById<CardView>(R.id.addMissing)



        //actionbar
        val actionbar = supportActionBar

        //set actionbar title
        "DashBoard".also { actionbar!!.title = it }

        //bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false


        addCommunityCard.setOnClickListener {
            Intent(this,ListActivity::class.java).also {
                startActivity(it)
            }
        }

    }


}