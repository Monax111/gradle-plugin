package plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import java.io.File

class Apply {

    @Test
    fun successfulApply() {

        val result = GradleRunner.create()
            .withProjectDir(File("dirForIntegrationTest"))
            .withPluginClasspath()
            .withEnvironment(mapOf("CI_JOB_ID" to ""))
            .withArguments("test")
            .forwardOutput()
            .build()
    }

}