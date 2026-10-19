pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StyleDrop"
include(":app")

gradle.beforeProject {
    layout.buildDirectory.set(file("C:/GradleBuilds/StyleDrop/${name}"))
}
