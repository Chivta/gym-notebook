plugins {
    id("application")
}

application {
    mainClass = "GymNotebook.Main"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
}


tasks.test {
    useJUnitPlatform()
    testLogging {
        events ("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
