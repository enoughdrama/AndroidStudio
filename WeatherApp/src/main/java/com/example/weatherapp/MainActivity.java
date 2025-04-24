package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.weatherapp.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    private EditText cityEditText;
    private Button searchButton;
    private TextView temperatureTextView;
    private TextView weatherDescriptionTextView;
    private TextView humidityTextView;
    private WeatherApiService weatherApiService;
    // Демонстрационный API ключ - заменить на свой из openweathermap.org
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        searchButton = findViewById(R.id.searchButton);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        weatherDescriptionTextView = findViewById(R.id.weatherDescriptionTextView);
        humidityTextView = findViewById(R.id.humidityTextView);

        // Проверяем доступность сети
        if (NetworkUtils.isNetworkAvailable(this)) {
            setupRetrofit();
        } else {
            Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_LONG).show();
        }
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApiService = retrofit.create(WeatherApiService.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEditText.getText().toString().trim();
                if (!city.isEmpty()) {
                    getWeatherData(city);
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите город", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeatherData(String city) {
        Call<WeatherResponse> call = weatherApiService.getWeatherData(city, API_KEY, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    
                    double temperature = weatherResponse.getMain().getTemp();
                    String weatherDescription = weatherResponse.getWeather().get(0).getDescription();
                    int humidity = weatherResponse.getMain().getHumidity();

                    temperatureTextView.setText(temperature + " °C");
                    weatherDescriptionTextView.setText(weatherDescription);
                    humidityTextView.setText("Влажность: " + humidity + "%");
                } else {
                    Toast.makeText(MainActivity.this, "Город не найден", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}