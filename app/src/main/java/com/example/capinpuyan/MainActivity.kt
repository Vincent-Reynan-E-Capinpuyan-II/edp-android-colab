package com.example.capinpuyan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.capinpuyan.ui.LiceoAccountApp
import com.example.capinpuyan.ui.theme.CapinpuyanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapinpuyanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LiceoAccountApp()
                }
            }
        }
    }
}