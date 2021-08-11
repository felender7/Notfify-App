package co.za.theappbrewery.notify.activities.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import co.za.theappbrewery.notify.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity : AppCompatActivity() {

    //state widgets
    private var stateSectionLayout: LinearLayout? = null
    private var statePB: ProgressBar? = null
    private var stateTitleTV: TextView? = null
    private var stateMessageTV: TextView? = null
    private var stateCloseBtn: AppCompatImageButton? = null

    protected fun show(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    protected fun showSettingDialog() {
        Log.i("PERMISSION", "Showing Permmission Setting Dialog")
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("Assign Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GO TO SETTING"
        ) { dialog: DialogInterface, which: Int ->
            dialog.cancel()
            openSetting()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }




}
//end