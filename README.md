
# Perify
Perify (Peril Notify) is an Android app that provides information regarding a list of disasters that happen throughout Indonesia.


## Features
- Showing list of disaster in span of last two days.
- Filtering disaster based on Province.
- Filtering disaster based on disaster type.
- Notification alert about water level (Read more in FAQ).
- Dark mode.
- Offline - Online support.
## Architecture
- MVVM + Clean Architecture

## Tech Stack
#### Core
- Kotlin
- Kotlin Coroutines
- Kotlin Flow
- Hilt (DI)
#### Networking
- Retrofit
- GSON
#### Local Persistence
- Shared Preferences
- Room DB

## How to Build

- Clone the repository
- Open with Android Studio
- Make sure you have already created a Google Maps API Key in order to run the maps feature (if not, please make one beforehand from this [link](https://developers.google.com/maps/documentation/android-sdk/get-api-key))
- With the created Google Maps API Key, head to the local.properties on the project file and insert the key in the following manner
```bash
MAPS_API_KEY=YourAPIKey
```
- Sync the project and you are good to go!

## Demo

[Perify.apk](https://drive.google.com/file/d/1Csau5jMVE9E6Fx-Zq_UA8Dc_kYH1Camo/view?usp=drive_link)

[Application Demo / Youtube](https://youtu.be/SajvR25oEws)


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


## Test Cases
#### Unit Test
As for now, the Unit Test is not yet implemented
#### UI Test
Below are the test cases that've been implemented on Perify. For details on UI Test please check this [video](https://www.youtube.com/watch?v=ul3sMuP_0so).
- Load data and show it onto the RecyclerView (PASSED).
- Load disaster data based on Disaster Type (PASSED).
- Load disaster data based on Province (PASSED).
- Load disaster data based on Disaster Type and Province (PASSED).
- Show message when the data is empty (PASSED).
- Navigating to SettingsActivity to enable Dark mode (PASSED).
- Check whether the Dark Mode is active (PASSED).

## Roadmap
- Enhance the UI part
- Implement more testing case (especially Unit Test)
- Add more features


## FAQ
#### Question 1
#### Q: Does the notification alert for water level works?
A: It works but it's still a dummy data, because the endpoint always return an empty object.

#### Question 2
#### Q: Why the maps is not showing when i build the apps?
 A: Please create your own Google Maps API Key then head to the local.properties in the project file. Create a variable called "MAPS_API_KEY" and fill it with your created API Key.


## Feedback
If you have any feedback, please reach out to my email at calvinsaputra217@gmail.com.
