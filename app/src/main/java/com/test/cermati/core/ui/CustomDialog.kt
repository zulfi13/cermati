package com.test.cermati.core.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.view.WindowManager

object CustomDialog {

    private val dm = DisplayMetrics()
    private var dlg: Dialog? = null

    fun dismiss() {
        dlg?.dismiss()
        dlg = null
    }

    fun showPopUp(ctx: Context, image: Int, title: String = "",
                  body: String = "", button: String = "BACK",
                  isDefaultFont: Boolean = true,
                  dismissListener: DialogInterface.OnDismissListener = DialogInterface.OnDismissListener {  },
                  isCancelAble: Boolean = false){
        if (dlg?.isShowing == true){
            return
        }

//        val binding : DlgPopUpBinding =  DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dlg_pop_up, null, false)
//        binding.apply {
//            model = DialogPopUp(image, title, body, button)
//            if (title.isEmpty()) dlgTitle.visibility = View.GONE else {
//                val typeface =
//                    if (isDefaultFont) ResourcesCompat.getFont(ctx, R.font.montserrat_bold)
//                    else ResourcesCompat.getFont(ctx, R.font.cabin_regular)
//                dlgTitle.typeface = typeface
//            }
//            outsideContainer.setOnClickListener { if(isCancelAble) dismiss() }
//            dlgSubTitle.visibility = if (body.isEmpty()) View.GONE else View.VISIBLE
//            dlgBtn.setOnClickListener { dismiss() }
//        }
//
//        createDialog(ctx, binding.root, true)

        dlg?.apply {
            setOnDismissListener(dismissListener)
        }
        dlg?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dlg?.show()
    }
}