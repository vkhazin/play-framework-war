# Scala-Play-Restful in a War Container #

# [Consideration for hosting Play Framework](https://docs.google.com/document/d/1VPx4RPppCrYo9asVq96gWmZq6N7Nyr4cRhidQpA7Lvo/edit#heading=h.3w1vsjn6xj3w) #

# Objectives #
* Create new restful service using Scala and Play Framework.
* Package service as war package.
* Deploy newly created war package to Tomcat.
* Connect to the service deployed to Tomcat.

# Acknowledgements #
All credits go to contributors of https://github.com/play2war/play2-war-plugin and personally to Yann Simon for pointing out my mistake.
 
# What you gonna need before we start #
(These steps are for Mac OS)

* Install Oracle JDK 8 - not OpenJDK! Latter did not work for me, but I am not too bright, sigh...
* Install Brew to streamline installation of other components from command window:
```
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
* Install Play Framework from terminal window using brew:  
```
brew install typesafe-activator
```
* Download [Tomcat](http://apache.mirror.iweb.ca/tomcat/tomcat-8/v8.0.30/bin/apache-tomcat-8.0.30.tar.gz)
* Unpack tar file into a folder in my case ~/Applications/apache-tomcat-8.0.30
* Launch activator to confirm everything is in order with Play Framework, from command window run:  
```
activator
```
* Wait until web browser ui for activator is launched
* Launch Tomcat to confirm it is able to run on local machine from command window run:
```
~/Applications/apache-tomcat-8.0.30/bin/catalina.sh start
```
* Point your browser to http://localhost:8080 to confirm Tomcat is running

# Let's build restful end-point #
* Clone git repository using command window:
```
https://github.com/vkhazin/play-framework-war.git
```
* Using activator browser ui 'Open existing app' in the right top corner 
locate the folder where you have cloned the git repository into and 'choose' it, 
the 'choose' button is barely visible on the ui :-)
* Using activator browser ui run the cloned project to confirm it compiles and runs without errors
* After the project is running you should be able to access using http://localhost:9000
* Output should be json:
```
{
    name: "play-framework-war",
    author: "Vlad Khazin",
    email: "vladimir.khazin@icssolutions.ca"
}
```

# Let's package it as a war #
* ./project/plugins.sbt should look like:
```
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.6")

//Play2War
addSbtPlugin("com.github.play2war" % "play2-war-plugin" % "1.4-beta1")
```
* ./build.sbt should look like:
```
//Play2War
import com.github.play2war.plugin._

name := """play-framework-war"""

version := "1.0-SNAPSHOT"

//Play2War
Play2WarPlugin.play2WarSettings

//Play2War
Play2WarKeys.servletVersion := "3.1"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

fork in run := true
```
* Let's build the war, using command window from the folder where you have cloned the repository run:
```
activator war
```
* Rename ./target/play-framework-war-1.0-SNAPSHOT.war to play-framework.war for shorter version

# Let's deploy the war pacakge to Tomcat #
* Grant ourselves access to the manager gui of Tomcat, in the folder where you have unpackaged Tomcat locate ./conf/tomcat-users.xml
* Open it using text editor and ensure following line is in-between 
```
<tomcat-users>
```
and 
```
</tomcat-users>
```
tags:
```
<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
    <user username="tomcat" password="secret" roles="manager-gui,admin-gui"/>
</tomcat-users>
```
* Needless to say above configuration should never be in place anywhere else other than of your own laptop during development
* Navigate to http://localhost:8080/manager/html/list and enter tomcat/secret in credentials pop-up message
* Scroll down and in the 'WAR file to deploy' section select file created in the previous paragraph, and select 'Deploy' button
* After a short while your new application should be displayed on the list of applications and it should be Running: true, otherwise the deployment has failed.
* Navigate to 
```
http://localhost:8080/play-framework/
```
and the same json output should be displayed:
```
{
    name: "play-framework-war",
    author: "Vlad Khazin",
    email: "vladimir.khazin@icssolutions.ca"
}
```

# Conclusions #
* With quite a few of configuration troubles you can host play framework service as war package in Tomcat.
* Not sure whether it will undermine the objective of async nature of play framework - refer to the first paragraph of the read-me.
* I would personally rather host play framework as a docker container. See example [here](https://github.com/vkhazin/play-framework-docker).