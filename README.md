# Astronomy Picture of the Day App

This Android application displays the Astronomy Picture of the Day (APOD) from NASA. Users can view a list of stunning images of space with the title and date.

## Features

- **Daily picture:** Fetches and displays the latest APOD from NASA's API.
- **Sorting:** Enables users to sort pictures by title or date.
- **Error handling:** Displays user-friendly messages for network errors and other issues.

## Architecture

The app follows modern Android development practices and uses the following architectural components:

- **MVVM (Model-View-ViewModel):** Separates UI logic from business logic and data handling.
- **Repository pattern:** Provides a clean abstraction for data access.
- **Use cases:** Encapsulates business logic for specific actions.
- **Hilt:** Provides dependency injection for easier testing and modularity.
- **Jetpack Compose:** Modern declarative UI toolkit for building dynamic and beautiful UIs.

## Libraries

- **Retrofit:** For making network requests to the NASA API.
- **Coil:** For image loading and caching.
- **Coroutines:** For managing asynchronous operations.
- **Hilt:** For dependency injection.
- **Jetpack Compose:** For building the UI.

## Screenshots

<img width="432" alt="Screenshot 2024-10-02 at 1 44 04 PM" src="https://github.com/user-attachments/assets/2a2d912e-088a-4152-abd9-5f19d0415f48">

<img width="433" alt="Screenshot 2024-10-02 at 1 44 25 PM" src="https://github.com/user-attachments/assets/fc4c8a6e-300c-45b8-9f5b-82005d59a1cc">

<img width="432" alt="Screenshot 2024-10-02 at 1 44 46 PM" src="https://github.com/user-attachments/assets/e45d97ea-2630-46dd-8b65-f9ec7400d7b9">

## Getting Started

- Clone the repository: `git clone https://github.com/MaxHastings/AssignmentAdyen.git`
- Open the project in Android Studio.
- Build and run the app on an emulator or device.

## API Key

This app uses the NASA APOD API. You'll need to obtain an API key from NASA and add `API_KEY` to `local.properties` file.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
