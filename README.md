# subrelease-maven-plugin
This plugin provides an enhancement over the usual maven-release-plugin by releasing any snapshot dependencies altogether.

In a perfect world, maven-release-plugin would easily help you release your awesome project. Consider the following release phrases designed by [maven-release-plugin](https://maven.apache.org/maven-release/maven-release-plugin/examples/prepare-release.html)

> 
    Preparing a release goes through the following release phases:
    * Check that there are no uncommitted changes in the sources
    * Check that there are no SNAPSHOT dependencies
    * Change the version in the POMs from x-SNAPSHOT to a new version (you will be prompted for the versions to use)
    * Transform the SCM information in the POM to include the final destination of the tag
    * Run the project tests against the modified POMs to confirm everything is in working order
    * Commit the modified POMs
    * Tag the code in the SCM with a version name (this will be prompted for)
    * Bump the version in the POMs to a new value y-SNAPSHOT (these values will also be prompted for)
    * Commit the modified POMs
    

But in reality, you may actually have to live in a cold weird place, where release phases above may not applicable, especially the second bullet above. Before maven-release-plugin perform a release, it mandates you to solve this problem, which is tiresome if you have many of them. 

You're also right that this problem may be resolved by going as multi-module, so that it would release all of those dependencies for you. But didn't I just say about a cold weird place?

*subrelease-maven-plugin* is built to get rid of the limitation of the second bullet above, by automatically release all of those SNAPSHOT dependencies for you. Therefore, we could rewritten the release phases as:

* Check for SNAPSHOT dependencies
* Release those SNAPSHOT dependencies
* Update POM to use released version instead of SNAPSHOT
* Goes through the perfect release phases of maven-release-plugin

## Usage

### Install

There are two options at the moment: 

1. Clone this project, then executes `mvn install` to install this plugin into your local repository
2. Download [0.1.0.zip](https://github.com/manat/subrelease-maven-plugin/releases/download/v0.1.0/0.1.0.zip) plugin, then unzip it to your local repository

### Command

`mvn com.github.manat:subrelease-maven-plugin:[version]:[goal]`

For example:

```bash
mvn com.github.manat:subrelease-maven-plugin:0.1.0:prepare
```

## Goals

There are 2 available goals at the moment:
* **prepare** to release any SNAPSHOT dependencies, then updating current pom.xml to be ready for releasing
* **release** to basically executes release:prepare of standard maven-release-plugin

### prepare

It performs the following steps:

1. Resolves a list of snapshot dependencies. If there is none, then the prepare process is finished
2. For each of dependency
  1. Verifies if this dependency has already been released
  2. Otherwise, performs a release
3. Once all of the snapshot dependencies are released, the project's pom will be updated to use released version instead of SNAPSHOT for those belong to #2
4. Commit change of pom 


Please note that **prepare** goal can be run repeatedly until #3 is satified.

### release

This basically performs [release:prepare](http://maven.apache.org/maven-release/maven-release-plugin/prepare-mojo.html) of standard maven-release-plugin.

## Contribution

There is A LOT that you could help. 

1. Use the [issue tracker](https://github.com/manat/subrelease-maven-plugin/issues) to contribute ideas, or reporting bugs
2. If you have code to contribute
  1. Push your changes to a topic branch in your fork of the repository.
  2. Submit a pull request to the repository
  3. If things go well, then everyone is happy
