package co.za.theappbrewery.notify.common

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import co.za.theappbrewery.notify.R
import com.squareup.picasso.Picasso

object Utils {
    /**
     * Open another activity
     * @param c
     * @param clazz
     */
    @JvmStatic
    fun openActivity(c: Context, clazz: Class<*>?) {
        val intent = Intent(c, clazz)
        c.startActivity(intent)
    }

    /**
     * Load image from online using Picasso
     * @param imageURL
     * @param fallBackImage
     * @param imageView
     */
    @JvmStatic
    fun loadImageFromNetwork(imageURL: String, fallBackImage: Int,
                             imageView: ImageView
    ) {
        if (imageURL.isNotEmpty()) {
            Picasso.get().load(imageURL).placeholder(R.drawable.load_dots).into(imageView)
        } else {
            Picasso.get().load(fallBackImage).into(imageView)
        }
    }
}