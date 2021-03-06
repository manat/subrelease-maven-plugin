package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.model.Dependency;

import java.nio.file.Path;

/**
 * This interface class describes appropriate actions those are required to perform a series of
 * subrelease.
 */
public interface Subrelease {

    /**
     * Executes mvn dependency:resolve, then the result will be stored at the given output path.
     *
     * @param output resolved dependencies are stored here
     * @return true for success; false otherwise
     */
    boolean resolveDependency(Path output);

    /**
     * Executes mvn dependency:unpack of the given dependency value.
     *
     * @param dependency a Dependency to be unpack
     * @return true for success; false otherwise
     */
    boolean unpackArtifact(Dependency dependency);

    /**
     * Executes mvn scm:checkout of the given dependency, using provided connection.
     *
     * @param dependency a Dependency to be checkout
     * @param connection an information indicates scm type and connection url
     * @return true for success; false otherwise
     */
    boolean checkout(Dependency dependency, String connection);

    /**
     * Executes mvn dependency:get to find out if a certain version of the given dependency/artifact
     * exists.
     *
     * @param dependency a Dependency to verify
     * @return true if the given dependency has been released
     */
    boolean exists(Dependency dependency);

    /**
     * Executes mvn dependency:get to find out if a release version of the given dependency/artifact
     * exists.
     *
     * @param dependency a Dependency to verify
     * @return true if the given dependency has been released
     */
    boolean releaseExists(Dependency dependency);

    /**
     * Executes mvn release:prepare. Any standard options of release:prepare are applied.
     *
     * @param options mvn options for release:prepare command
     * @return true for success; false otherwise
     * @see http://maven.apache.org/maven-release/maven-release-plugin/prepare-mojo.html
     */
    boolean release(String... options);

    /**
     * Executes mvn release:perform which basically deploy artifacts to the artifactory. Any
     * standard options of release:perform are applied.
     *
     * @param options mvn options for release:perform command
     * @return true for success; false otherwise
     * @see http://maven.apache.org/maven-release/maven-release-plugin/perform-mojo.html
     */
    boolean perform(String... options);

    /**
     * Executes mvn scm:checkin to commit any changes within the repository, using default commit
     * message as "[subrelease-maven-plugin] Resolved any SNAPSHOT dependencies.".
     * <p>
     * In addition to standard scm:checkin option, it accepts scmCommentPrefix option as a prefix to
     * default commit message. However, scm:checkin option is honored first, means, if message
     * option is supplied, scmCommentPrefix will be ignored.
     *
     * @param options mvn options for scm:checkin command, plus scmCommentPrefix which is an extra
     * @return true for success; false otherwise
     * @see https://maven.apache.org/scm/maven-scm-plugin/checkin-mojo.html
     */
    boolean commit(String... options);
}
