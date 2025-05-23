# Weather App

https://enoughdrama.gitbook.io/documentation/prilozhenie-pogoda

## Описание
Weather App - это простое и удобное Android-приложение для просмотра прогноза погоды в различных городах мира. Приложение позволяет получать актуальные данные о погоде в режиме реального времени с использованием API OpenWeatherMap.

![image](https://github.com/user-attachments/assets/a9193466-022d-4b41-b067-ceef6ea859fc)

## Основные возможности
- Просмотр текущей температуры в выбранном городе
- Детальная информация о погодных условиях (ясно, облачно, дождь и т.д.)
- Данные о влажности воздуха
- Интуитивно понятный пользовательский интерфейс
- Поддержка широкого спектра устройств Android (версия 5.0 и выше)

## Технологии и библиотеки
- **Java** - основной язык программирования
- **Retrofit** - библиотека для работы с сетевыми запросами
- **OpenWeatherMap API** - сервис данных о погоде
- **AndroidX** - библиотека поддержки Android
- **Material Design Components** - компоненты пользовательского интерфейса

## Требования для запуска
- Android Studio 4.0 или новее
- Android SDK 21 (Android 5.0) или новее
- JDK 8 или новее
- Подключение к интернету для получения данных о погоде

## Руководство по использованию
1. Запустите приложение
2. Введите название города в поле ввода
3. Нажмите кнопку "Узнать погоду"
4. Просмотрите информацию о текущей погоде в выбранном городе

## Работа с API
Приложение использует бесплатное API от OpenWeatherMap для получения данных о погоде. Документация по API доступна по адресу: [https://openweathermap.org/api](https://openweathermap.org/api)

Для использования собственного API ключа:
1. Зарегистрируйтесь на сайте [OpenWeatherMap](https://openweathermap.org/)
2. Получите бесплатный API ключ
3. Замените значение константы `API_KEY` в файле `MainActivity.java`

## Возможные проблемы и их решения

### Ошибка "Город не найден"
- Проверьте правильность написания названия города
- Попробуйте указать название города на английском языке
- Проверьте подключение к интернету
