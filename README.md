
# Perify


Perify (Peril Notify) is an Android app that provides information regarding a list of disasters that happen throughout Indonesia.


## Features
- Showing list of disaster in span of last two days.
- Filtering disaster based on Province.
- Filtering disaster based on disaster type.
- Notification alert about water level (Read more in FAQ).
- Dark mode.
## Architecture
- MVVM

## Tech Stack
#### Core
- Kotlin
- Kotlin Coroutines
#### Networking
- Retrofit
- GSON
## How to Build

- Clone the repository
- Open with Android Studio
- Everything should sync and build automatically
## Demo

[Perify.apk](https://drive.google.com/file/d/1OqnxiXGyu8vumbZeZyE17eNAalXUz2uV/view?usp=sharing)

[Application Demo / Youtube](https://www.youtube.com/)


## API Reference
#### [PetaBencana](https://docs.petabencana.id/routes/laporan-urun-daya)
- Get Disaster Report

```http
  GET /reports
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `disaster` | `string` | **Optional** - Filtering based on type of disaster. |
| `timeperiod` | `int` | **Optional** - Filtering list of disaster by certain period if time. |
| `admin` | `string` | **Optional** - Filtering based on Province. |


## Roadmap

- Implement Clean Architecture
- Enhance the UI part
- Add more features


## FAQ

#### Question 1
#### Q: Does it use clean architecture?
A: Not yet, the code is still messy and there's a lot of space to improve.


#### Question 2
#### Q: Does the notification alert for water level works?
A: It works but it's still a dummy data, because the endpoint always return an empty object.

#### Question 3
#### Q: Does it support online-offline mode?
 A: Not yet, currently it's still in Online mode only. So you need an internet connection to access it.


## Feedback

If you have any feedback, please reach out to my email at calvinsaputra217@gmail.com.

