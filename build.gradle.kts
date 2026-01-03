import java.util.regex.Pattern

fun getPluginName(): String {
    val pluginYml = file("src/main/resources/plugin.yml")
    val nameRegex = Regex("""^name:\s*(.+)$""", RegexOption.MULTILINE)

    val content = pluginYml.readText()
    return nameRegex.find(content)
        ?.groupValues
        ?.get(1)
        ?.trim()
        ?: error("plugin.yml에서 name을 찾을 수 없음")
}

plugins {
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

val serverPluginsDir = file("server/plugins")

tasks.register<Copy>("buildPlugin") {
    dependsOn(tasks.shadowJar)

    val pluginName = getPluginName()

    from(tasks.shadowJar.get().archiveFile)
    into(serverPluginsDir)

    rename {
        "$pluginName.jar"
    }
}
