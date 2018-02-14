mvndar
======

A Maven plugin to create Diffusion DAR files.

[Documentation](http://pushtechnology.github.io/mvndar/index.html)

# Changes

## [Next release]
 - Java 7 is now a minimum requirement for running the plugin.

## 1.3
 - [fix] Cope with reloctaed dependencies [#2](/../../issues/2)
 - [fix] Fix Windows regression introduced in 1.2 [#3](/../../issues/3)

## 1.2

 - Support and documentation for use of a ```data``` folder within the DAR has been removed. This functionality can be replicated by using the ```diffusionIncludes``` configuration property.

## 1.1

 - The plugin's GroupId has changed to `com.pushtechnology.tools`.