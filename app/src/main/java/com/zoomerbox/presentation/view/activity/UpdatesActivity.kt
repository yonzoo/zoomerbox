package com.zoomerbox.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zoomerbox.R

/**
 * Экран с информацией о последнем обновлении
 */
class UpdatesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updates)
    }
}