# Run: 
`mvn clean {optional: -P Telemetry} javafx:run -f pom.xml`
# Deploy:
`clean package {optional: -P Telemetry} javafx:jlink jpackage:jpackage@win -f pom.xml`