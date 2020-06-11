package com.test.cermati.features.entrance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.test.cermati.R
import com.test.cermati.core.ui.BaseActivity
import com.test.cermati.databinding.ActivitySplashBinding
import com.test.cermati.features.home.view.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewFullscreen(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 2000)
    }
}