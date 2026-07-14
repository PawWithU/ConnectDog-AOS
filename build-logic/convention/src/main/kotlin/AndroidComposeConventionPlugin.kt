import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.kusitms.connectdog.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val commonExtension: CommonExtension<*, *, *, *, *, *> =
                extensions.findByType<ApplicationExtension>()
                    ?: extensions.findByType<LibraryExtension>()
                    ?: error("연결된 android application/library 플러그인을 찾을 수 없습니다.")

            commonExtension.buildFeatures.compose = true

            dependencies {
                add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
                add("implementation", libs.findBundle("compose").get())
            }
        }
    }
}
