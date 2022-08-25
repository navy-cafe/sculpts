plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow")
    id("org.checkerframework")
}

group = "cafe.navy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.broccol.ai/releases")
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    compileOnly(libs.cloud.core)
    compileOnly(libs.cloud.paper)
    compileOnly(libs.corn.minecraft.paper) {
        exclude(group="io.papermc.paper", module="paper-api")
    }
    compileOnly(libs.bedrock.paper)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

        reobfJar {
            outputJar.set(rootProject.layout.buildDirectory.file("libs/${project.name}.jar"))
        }
}

//

//tasks {
//    reobfJar {
//        dependsOn(shadowJar)
//    }
//}