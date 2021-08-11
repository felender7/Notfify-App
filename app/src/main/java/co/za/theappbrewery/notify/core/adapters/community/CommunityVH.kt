package co.za.theappbrewery.notify.core.adapters.community

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import co.za.theappbrewery.notify.R

class CommunityVH (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txt_name: TextView
    var tvDescription:TextView
    var txt_option:TextView
    lateinit var cardView: CardView

    //Item Layout
    init {

        txt_name = itemView.findViewById(R.id.tvName)
        tvDescription = itemView.findViewById(R.id.tvDescription)
        txt_option = itemView.findViewById(R.id.txt_option)
        cardView = itemView.findViewById(R.id.cardView)

    }
}