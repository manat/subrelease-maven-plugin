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

