package plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.io.File

class Apply {

    @Test
    fun successfulApply() {

        GradleRunner.create()
            .withProjectDir(File("dirForIntegrationTest"))
            .withPluginClasspath()
            .withArguments("test")
            .forwardOutput()
            .build()
    }

    @Test
    fun successfulApplyWithOutPluginClasspath() {

        GradleRunner.create()
            .withProjectDir(File("dirForIntegrationTestWithLocalPush"))
            //.withPluginClasspath()
            .withArguments("test")
            .forwardOutput()
            .build()
    }

}