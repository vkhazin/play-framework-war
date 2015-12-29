import com.github.play2war.plugin._

name := """play-framework-war"""

version := "1.0-SNAPSHOT"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.1"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

fork in run := true