package com.quizeu.appitaly

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.IOException

import android.util.Log
import org.json.JSONException

class FootballFactsActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {
    private val newsList = mutableListOf<NewsItem>() // Создаем список новостей
    private val TAG = "FootballFactsActivity" // Тег для логов

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_football_facts)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val backButton: Button = findViewById(R.id.backButton)

        Log.d(TAG, "Activity created") // Лог о создании активности

        // Загрузка данных из JSON-файла
        val jsonFileString = getJsonDataFromAsset("event.json")

        Log.d(TAG, "JSON data loaded: $jsonFileString") // Лог после загрузки данных

        // Парсинг JSON
        try {
            val newsArray = JSONArray(jsonFileString)
            for (i in 0 until newsArray.length()) {
                val newsObject = newsArray.getJSONObject(i)
                val headline = newsObject.getString("headline")
                val source = newsObject.getString("source")
                val date = newsObject.getString("date")
                val imageUrl = newsObject.getString("image_url")
                val description = newsObject.getString("description")

                val newsItem = NewsItem(headline, source, date, imageUrl, description)
                newsList.add(newsItem)
            }
        } catch (e: JSONException) {
            Log.e(TAG, "Error parsing JSON: ${e.message}") // Лог при ошибке парсинга
        }

        // Настройка RecyclerView для отображения списка новостей
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NewsAdapter(newsList, this) // Передаем текущий контекст в адаптер
        recyclerView.adapter = adapter // Устанавливаем адаптер

        // Показать кнопку "Назад" в конце списка
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) { // Достигнут конец списка
                    backButton.visibility = View.VISIBLE
                    Log.d(TAG, "End of the list reached") // Лог достижения конца списка
                }
            }
        })

        backButton.setOnClickListener {
            // Обработка нажатия кнопки "Назад"
            // Например, закрыть активность
            finish()
            Log.d(TAG, "Back button clicked") // Лог нажатия на кнопку "Назад"
        }
    }

    override fun onItemClick(newsItem: NewsItem) {
        // Здесь открывается фрагмент с описанием новости

    }

    // Функция для чтения JSON-файла из assets
    private fun getJsonDataFromAsset(fileName: String): String {
        val json: String
        try {
            val inputStream = assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}
