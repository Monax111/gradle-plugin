package plugin

import org.barfuin.gradle.taskinfo.GradleTaskInfoPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("Apply plugin")
        target.pluginManager.apply(GradleTaskInfoPlugin::class.java)
    }
}