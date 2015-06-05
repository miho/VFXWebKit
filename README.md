# VFXWebKit
Experimental WebKit Integration for JavaFX

## TODO
- Mouse/Keyboard integration
- performance improvements (dirty rect etc.)
- Windows support
- ...

![](/resources/img/screenshot-02.png)
![](/resources/img/screenshot-01.png)

## Build Instructions

### Reqirements (OS X)
- Xcode 6.x
- Qt 5.x (tested with Qt 5.4.2)
- JDK 8 (tested with JDK 1.8u45)

### Setup Build properties

Specify the Qt executable path in the `build.properties` file.

**Example**

    # QT5 bin directory (contains qmake etc.)
    QT5PATH=/usr/local/opt/qt5/bin/

### IDE

Open the `VFXWebKit` [Gradle](http://www.gradle.org/) project in your favourite IDE (tested with NetBeans 8.0.2) and build it
by calling the `assemble` task.

### Command Line

Navigate to the [Gradle](http://www.gradle.org/) project (e.g., `path/to/VFXWebKit`) and enter the following command

#### Bash (Linux/OS X/Cygwin/other Unix-like OS)

    ./gradlew assemble
    
## Run The Demo

To run the demo call the `run` task.
