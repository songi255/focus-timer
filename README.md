# Focus Timer Desktop App

JavaFX Visual timer application for desktop.

## Demo

![demo video](demo.gif)

**Features:**
- always on top
- full screen
- overlay with opacity
- pomodoro mode
- save settings

## How to use

**If the application has crashed, please delete the entire `%appdata%\FocusTimer` folder and then restart the application.**

#### Timer Disk

- **Click/Drag/Scroll** to modify time.

#### Time Text

The time text above represents the main timer, while the time text below indicates the Pomodoro timer.
- **Click** to change main/pomodoro mode.
- **Scroll** on Minute or Second text to modify time.

#### Finish Time Indicator

Box with black color beside time text represents finish time.

#### pomodoro mode

When pomodoro timer is set to value over 0, pomodoro mode is turned on.

In pomodoro mode, main timer and pomodoro timer is alternately used.


#### Overlay Mode

When you start the timer, the app is gone to overlay mode.
- **Click** to return to normal mode.
- **Drag** to change overlay position. This position is saved.
- **Scroll** to modify opacity of overlay window.

## How to run

#### Just for run
```shell
.\mvnw clean javafx:run
```

#### Build app image
``` shell
.\mvnw clean javafx:jlink
```

At the `target` folder...
- `app.zip` is zipped `target` folder.
- Run `target/app/bin/app` or `target/app/bin/app.bat`

#### Build packaged installer (like .exe)

```shell
\mvnw clean javafx:jlink jpackage:jpackage
```

You can find `exe` file at `target/dist` folder.

If you want to get installer for other OS or modify installer settings, change `pom.xml` file's `jpackage-maven-plugin` section.

## Notice

- It supports windows, mac, linux but tested only with windows.
- Feel free to open an issue for bug reports or suggestions.
- Since I've refactored the prototype code, the source code might be somewhat inconsistent. If you're planning to analyze the code, please take that into account.
- This app saves settings at `%appdata%\FocusTimer`.