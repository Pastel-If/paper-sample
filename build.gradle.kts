plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

    compileJava {
        options.release.set(21)
    }

    processResources {
        from("src/main/resources")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE // 중복 파일 오류 방지
    }

    jar {
        destinationDirectory.set(file("server/plugins"))
        archiveFileName.set("MyPlugin.jar")
    }
}
