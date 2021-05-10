package plugin

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.kotest.matchers.file.shouldExist
import org.barfuin.gradle.taskinfo.GradleTaskInfoPlugin
import org.barfuin.gradle.taskinfo.TaskInfoDto

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import java.io.File

val Mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

class Apply {

    @Test
    fun successfulApply() {

        val task = "jar"

        val runner = GradleRunner.create()
            .withProjectDir(File("dirForIntegrationTest"))
            .withPluginClasspath()
            .forwardOutput()

        runner.withArguments(task, GradleTaskInfoPlugin.TASKINFO_JSON_TASK_NAME).build()

        val resultJsonFile = runner.projectDir.resolve("build").resolve("taskinfo").resolve("taskinfo-$task.json")

        resultJsonFile.shouldExist()

        val graph = Mapper.readValue(resultJsonFile.readText(), TaskInfoDto::class.java)

        graph.dependencies
    }

}