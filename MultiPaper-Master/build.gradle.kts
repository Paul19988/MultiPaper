version = "${properties["masterVersion"]}"

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(project(":MultiPaper-MasterMessagingProtocol"))
    implementation("org.jetbrains:annotations:22.0.0")
    implementation("org.json:json:20220320")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("io.netty:netty-all:4.1.86.Final")
    implementation("se.llbit:jo-nbt:1.3.0")
    compileOnly("net.md-5:bungeecord-api:1.20-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "puregero.multipaper.server.MultiPaperServer",
            "Minecraft-Version" to "${properties["mcVersion"]}",
            "Master-Version" to "${properties["masterVersion"]}"
        )
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    relocate("io.netty", "puregero.multipaper.master.libs.netty")
    relocate("org.yaml.snakeyaml", "puregero.multipaper.master.libs.snakeyaml")
    relocate("se.llbit.nbt", "puregero.multipaper.master.libs.nbt")
}

publishing {
    publications.create<MavenPublication>("maven") {
        artifact(tasks.jar)
    }
}