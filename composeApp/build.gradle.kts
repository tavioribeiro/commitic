import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqldelight)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

kotlin {
    jvm()
    
    /*@OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }*/
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-compose:1.1.0")

            implementation(libs.sqldelight.coroutines.extensions)


            implementation("io.ktor:ktor-client-core:3.3.0")
            implementation("io.ktor:ktor-client-content-negotiation:3.3.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.0")



            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation(libs.sqldelight.sqlite.driver)

            implementation("io.ktor:ktor-client-cio:3.3.0")
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.tavioribeiro.commitic.MainKt"
        jvmArgs += listOf("-Dsun.awt.wm.class=commitic")

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "commitic"
            packageVersion = "1.0.0"
            description = "Commit-AI: Automatize suas mensagens de commit."
            copyright = "Desenvolvido por @tavioribeiro."
            vendor = "tavioribeiro"


            includeAllModules = true

            linux {
                shortcut = true
                debMaintainer = "tavioribeiro <seu@email.com>"
                appCategory = "Development"
                iconFile.set(project.file("src/commonMain/composeResources/drawable/logo3.png"))
            }



            windows {
                menu = true
                shortcut = true


                upgradeUuid = "54284915-2388-4509-9864-379089432415"


                iconFile.set(project.file("src/commonMain/composeResources/drawable/logo3.ico"))


                perUserInstall = true
            }

            macOS {
                bundleID = "org.tavioribeiro.commitic"
                iconFile.set(project.file("src/commonMain/composeResources/drawable/logo3.icns"))
            }
        }
    }
}


sqldelight {
    databases {
        create("CommiticDatabase") {
            packageName.set("org.tavioribeiro.commitic.db")
        }
    }
}




