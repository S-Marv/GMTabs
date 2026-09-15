### Run
- `mvn clean javafx:run -f pom.xml`
- `mvn clean (-P Telemetry) javafx:run -f pom.xml`
### Package
- `mvn clean javafx:jlink package -P JPackage -f pom.xml`
- `mvn clean javafx:jlink package -P JPackage (-P Telemetry) -f pom.xml`

Output found in `{projectDir}/target/dist`.