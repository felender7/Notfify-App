package co.za.theappbrewery.notify.common

import android.content.Context
import android.content.Intent

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
}