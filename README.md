# Hiatus Disclaimer
Development is currently on hiatus, as Javafx's WebView does not render WebP images. Other browser solutions, such as Java Chromium Embedded Framework (JCEF) and its fork, CEFFX, have not worked in experimentation. While I wait for a stable CEFFX release on maven, I am considering recreating the project in either Python or C# with a Chromium-based solution. 

# Running the Alpha build
Packaged for Windows 11. Untested for earlier versions of Windows.
- Download latest release from the repo's Releases list.
- Extract all files into an empty folder.
- Run the executable file titled `GMTabs-v*.exe`.

# How to use
- Open app. (see previous section)
- Right click default tab at bottom of the screen.
- Add a new tab by right-clicking an existing tab and selecting `new tab`.
- Paste a URL or select a File and assign a keybinding.
- Select the new tab either by clicking it on the bar or typing the keybinding.

# Build
JDK: OpenJDK 25 <br>
IDE: Intellij Community Edition

### Run
- `mvn clean javafx:run -f pom.xml`
- `mvn clean (-P Telemetry) javafx:run -f pom.xml`
### Package
- `mvn clean javafx:jlink package -P JPackage -f pom.xml`
- `mvn clean javafx:jlink package -P JPackage (-P Telemetry) -f pom.xml`

Output found in `{projectDir}/target/dist`.