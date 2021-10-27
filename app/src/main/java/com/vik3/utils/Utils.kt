package com.vik3.utils

import android.app.Activity
import android.app.Dialog
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.vik3.R

object Utils {

    private var dialogBuilder: Dialog? = null

    @JvmStatic
    fun getTextString(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    @JvmStatic
    fun getTextLength(editText: EditText): Int {
        return editText.text.toString().trim { it <= ' ' }.length
    }


    @JvmStatic
    fun showToast(message: String?, context: Context?) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    @JvmStatic
    fun showToastCenter(message: String?, context: Context?) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public fun showSoftKeyboard(context: Context, editText: EditText?) {
        if (editText == null) return
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    @JvmStatic
    fun hideSoftKeyboard(context: Context, editText: EditText?) {
        if (editText == null) return
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }


    /**
     * static method to SHOW progress dialog for the App.
     *
     *
     * use Utility.hideProgressDialog(mContext)to hide this dialog.
     *
     * @param mContext Context
     */
    @JvmStatic
    fun showProgressDialog(mContext: Activity?) {
        try {
            mContext?.let {
                if (!mContext.isFinishing) {
                    if (dialogBuilder != null && dialogBuilder?.isShowing == true) {
                        dialogBuilder?.dismiss()
                    }
                    dialogBuilder = Dialog(mContext)
//                    val dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null)
                    dialogBuilder?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialogBuilder?.setContentView(R.layout.circular_dialog)
                    /*Glide.with(mContext)
                        .asGif()
                        .load(R.raw.loading)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(dialogBuilder!!.findViewById(R.id.progress))*/
                    dialogBuilder?.setCancelable(false)
                    dialogBuilder?.show()
                }
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun hideProgressDialogBg() {
        try {
            if (dialogBuilder != null && dialogBuilder?.isShowing == true) {
                dialogBuilder?.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun changeBackground(context: Context?, view: View, background: Int) {
        val gradientDrawable = view.background as GradientDrawable
        gradientDrawable.mutate()
        context?.let {
            gradientDrawable.setColor(ContextCompat.getColor(context, background))
        }
    }

    fun showHood(activity: Activity) {
        try {
            if (!activity.isFinishing) {
                showProgressDialog(activity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideHood(activity: Activity) {
        try {
            if (!activity.isFinishing) {
                hideProgressDialogBg()
            }
        } catch (e: Exception) {
        }
    }


}