# Marvel-Heroes
A demo application that demonstrates modern android architecture with MVVM and clean architecture.

## 🏃 How to run the project
1. You should get an API Key. (https://developer.marvel.com/)
2. Save the base url and your keys (public & private key) in your `local.properties`.
```
MARVEL_PUBLIC_KEY=[YOUR PUBLIC KEY]
MARVEL_PRIVATE_KEY=[YOUR PRIVATE KEY]
MARVEL_BASE_URL="https://gateway.marvel.com:443/v1/public/"

PRODUCTION_MARVEL_PUBLIC_KEY=[YOUR PUBLIC KEY]
PRODUCTION_MARVEL_PRIVATE_KEY=[YOUR PRIVATE KEY]
PRODUCTION_MARVEL_BASE_URL="https://gateway.marvel.com:443/v1/public/"
```
3. Build and run the project

## 🏛 Architecture
### Clean Architecture
The code follows Google's recommended architecture, which is a lite version of clean architecture.

<img src="https://github.com/ahmed-tarek-bs/Marvel-Heroes/assets/95565538/979a4390-9355-4d72-97f6-8e89a9aa2356" width="30%" height="30%">

1. **UI Layer** <br/>
The role of the UI layer (or presentation layer) is to display the application data on the screen. Whenever the data changes, either due to user interaction (such as pressing a button) or external input (such as a network response), the UI should update to reflect the changes.

    - `UI elements` that render the data on the screen. 
    -  `State holders` (ViewModel) that hold data, expose it to the UI, and handle logic.

<img src="https://github.com/ahmed-tarek-bs/Marvel-Heroes/assets/95565538/cc6d7d83-ccd2-4e5c-804c-198a3d102ddd" width="30%" height="30%">

2. **Data Layer** <br/>
    - The data layer of an app contains the `business logic`. The business logic is what gives value to your app — it's made of rules that determine how your app creates, stores, and changes data.
    - The data layer is made of `repositories` that each can contain zero to many `data sources`.

<img src="https://github.com/ahmed-tarek-bs/Marvel-Heroes/assets/95565538/08e236a9-67b5-4e2d-ba58-194eea335468" width="30%" height="30%">

3. **Domain Layer** <br/>
    - The domain layer is responsible for encapsulating complex business logic, or simple business logic that is `reused` by multiple ViewModels.
    - It contains useCases, business models, and repositories interfaces.

<img src="https://github.com/ahmed-tarek-bs/Marvel-Heroes/assets/95565538/3b06cd88-e5bf-407d-89ec-fa43225aacd1" width="30%" height="30%">

  - The domain layer includes interfaces for repositories, so UI layer is restricted to accessing only the domain layer.

<img src="https://github.com/ahmed-tarek-bs/Marvel-Heroes/assets/95565538/b911976b-d024-48f7-9bd0-2211906c8536" width="30%" height="30%">

  - The data models that you want to expose from the repositories might be a subset of the information that you get from the different data sources.
  - It's a good practice to separate model classes and have your repositories expose only the data that the other layers of the hierarchy require.
  - Separating model classes is beneficial in the following ways:
    - It saves app memory by reducing the data to only what's needed.
    - It adapts external data types to data types used by your app—for example, your app might use a different data type to represent dates.
    - It provides better separation of concerns—for example, members of a large team could work individually on the network and UI layers of a feature if the model class is defined beforehand.


## ⚒ Tech Stack
- `Clean Architecture` + `MVVM`
- `Coroutines` + `Flow` : Asynchronous
- `Hilt` : Dependency Injection
- `Retrofit` : Network
- `Coil` : Image Library

- `JUnit` + `Mockito` + `Google Truth` : Testing

## 🔄 CI/CD
- Integrated with Bitrise.
- Find in the root folder the bitrise.yml file.
- Create a bitrise project and integrate it with the repo, import the yml file to configure the project.
