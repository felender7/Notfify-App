package co.za.theappbrewery.notify.core.adapters.community

import android.content.Context
import android.content.Intent
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.za.theappbrewery.notify.R
import co.za.theappbrewery.notify.activities.community_alerts.CreateActivity
import co.za.theappbrewery.notify.core.db.dbCommunity
import co.za.theappbrewery.notify.core.models.Community
import java.util.ArrayList

class ComAdapter (private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var onItemClick: ((Community) -> Unit)? = null
    private var list: ArrayList<Community>? = ArrayList()
    fun setItems(commu: ArrayList<Community>?) {
        list?.addAll(commu!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_items_community, parent, false)
        return CommunityVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val e: Community? = null
        this.onBindViewHolder(holder, position, e)
    }

    private fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, e: Community?) {

        val vh = holder as CommunityVH
        val commu = e ?: list?.get(position)
        vh.txt_name.text = commu?.name
        vh.tvDescription.text = commu?.description


        // view item in details
        vh.cardView.setOnClickListener{
            val intent = Intent(context,CreateActivity::class.java)
            intent.putExtra("Community", commu)
            context.startActivity(intent)
        }


        // view more options
        vh.txt_option.setOnClickListener {
            val popupMenu =
                PopupMenu(context, vh.txt_option)
            popupMenu.inflate(R.menu.option_menu_community_alert)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        val intent = Intent(context,CreateActivity::class.java)
                        intent.putExtra("EDIT", commu)
                        context.startActivity(intent)
                    }

                    R.id.menu_more ->{
                        val intent = Intent(context,CreateActivity::class.java)
                        intent.putExtra("Employee", commu)
                        context.startActivity(intent)
                    }

                    R.id.menu_remove -> {
                        val commu = Community()
                        commu.remove(commu?.key)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT)
                                    .show()
                                notifyItemRemoved(position)
                                list?.remove(commu)
                            }.addOnFailureListener { er: Exception ->
                                Toast.makeText(
                                    context,
                                    "" + er.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    }
                }
                false
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }
}