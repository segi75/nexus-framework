package com.nexusframework.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile

class NexusJavaConventionsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 1. Java 플러그인 적용
        project.getPlugins().apply(JavaPlugin.class)

        // 2. 자바 버전 강제 (Java 21)
        project.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension.class).tap {
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_21
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_21
        }

        // 3. 인코딩 강제 (UTF-8)
        project.tasks.withType(JavaCompile).configureEach {
            options.encoding = "UTF-8"
        }
        
        // 4. 리포지토리 설정 (Maven Central)
        project.repositories.mavenCentral()

        println("✅ [NEXUS] Java 21 Convention Applied to: " + project.name)
    }
}