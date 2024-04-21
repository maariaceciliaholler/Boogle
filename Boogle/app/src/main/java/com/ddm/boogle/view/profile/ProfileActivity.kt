package com.ddm.boogle.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ddm.boogle.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance())
                .commitNow()
        }
    }
}