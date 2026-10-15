# Traffic Signs — Android app

An Android app for people learning road signs and traffic rules. The interface is in **Russian and Kyrgyz** (switches with the phone's language).

Sign recognition from photos is handled by the Telegram bot in [`../bot`](../bot) (PyTorch CNN, 98.97% test accuracy on GTSRB). The app opens it from the side menu.

## Features

- **Signs:** a catalogue of road signs by category, with a picture and a description for each sign
- **Traffic rules (ПДД / ЖКЭ):** the rules, split into sections
- **Fines:** a list of traffic fines
- **Study materials**
- **Sign recognition:** opens the Telegram bot, which recognizes a sign from a photo
- **Google Maps** shortcut, sharing the app, settings, an About screen

## Tech

Java · Android SDK (min SDK 24, target SDK 34) · AndroidX, Material Components · Navigation Drawer

## Build

Open the `android-app` folder in Android Studio and run the `app` configuration on a device or emulator (Android 7.0+). Android Studio creates `local.properties` with the SDK path automatically.
