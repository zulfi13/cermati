package com.test.cermati.core.ui

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.test.cermati.R

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    private val requestNeedToAddAgain = 11
    private val requestFlPRegistration = 16

    protected lateinit var binding: B

    protected fun bindView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    private var toast: Toast? = null

    protected fun bindViewFullscreen(layoutId: Int) {
        // set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomDialog.dismiss()
    }

}