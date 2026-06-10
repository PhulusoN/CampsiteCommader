package com.example.campsitecommader

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.campsitecommader.ui.theme.CampsiteCommaderTheme
import kotlinx.coroutines.delay
////nemavhola phuluso ST10367073
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampsiteCommaderTheme {
                LaunchedEffect(Unit) {
                   // delay(3000) // 3-second splash screen delay
                    val intent = Intent(this@MainActivity, Main::class.java)
                    startActivity(intent)
                    finish() // Close MainActivity so it's not in the back stack
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Campsite Commander")
                }
            }
        }
    }
}
