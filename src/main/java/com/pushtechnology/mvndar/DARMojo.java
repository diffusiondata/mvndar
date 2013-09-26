/*
 * Copyright (C) 2013 Push Technology Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pushtechnology.mvndar;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.List;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.jar.JarArchiver;

/**
 * Goal that creates a Diffusion™ Archive (DAR) file.
 *
 * @author Philip Aston
 */
@Mojo(
    name = "dar",
    defaultPhase = LifecyclePhase.PACKAGE,
    threadSafe = true,
    requiresDependencyResolution = ResolutionScope.TEST)
public class DARMojo extends AbstractMojo {

    /**
     * The project output directory. The contents will be packaged in the DAR
     * {@code ext} directory.
     *
     * @see #outputIncludes
     * @see #outputExcludes
     */
    @Parameter(defaultValue = "${project.build.outputDirectory}")
    private File outputDirectory;

    /**
     * Directory containing additional resources to add to the DAR file.
     * Typically contains at least {@code etc/Publishers.xml}.
     *
     * @see #diffusionIncludes
     * @see #diffusionExcludes
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/diffusion")
    private File diffusionResourceDirectory;

    /**
     * The output directory for the generated DAR.
     */
    @Parameter(defaultValue = "${project.build.directory}", required = true)
    private String buildDirectory;

    /**
     * The name of the DAR file to generate.
     */
    @Parameter(alias = "darName",
        defaultValue = "${project.build.finalName}",
        required = true)
    private String finalName;

    /**
     * The minimum version number of Diffusion on which the DAR can be deployed.
     */
    // Maybe calculate default from the manifest of an API jar found in the
    // dependencies?
    @Parameter(defaultValue = "4.5.0")
    private String minimumDiffusionVersion;

    /**
     * List of fileset patterns specifying files from the
     * {@link #diffusionResourceDirectory} to include in the DAR.
     */
    @Parameter
    private String[] diffusionIncludes;

    /**
     * List of fileset patterns specifying files from the
     * {@link #diffusionResourceDirectory} to exclude from the DAR.
     */
    @Parameter
    private String[] diffusionExcludes;

    /**
     * List of fileset patterns specifying files from the project output
     * directory to include in the DAR {@code ext} directory.
     */
    @Parameter
    private String[] outputIncludes;

    /**
     * List of fileset patterns specifying files from the project output
     * directory to exclude from the DAR {@code ext} directory.
     */
    @Parameter
    private String[] outputExcludes;

    /**
     * Classifier to add to the artifact generated. If provided, the artifact
     * will be an attachment instead.
     */
    @Parameter
    private String classifier;

    /**
     * The archive configuration to use. See the <a
     * href="http://maven.apache.org/shared/maven-archiver/index.html">Maven
     * Archiver Reference</a>.
     */
    @Parameter
    private final MavenArchiveConfiguration archiver =
        new MavenArchiveConfiguration();

    @Component(role = Archiver.class, hint = "jar")
    private JarArchiver jarArchiver;

    @Component
    private MavenProjectHelper projectHelper;

    @Component
    private MavenProject project;

    @Component
    private MavenSession session;

    // Should be a parameter.
    private static final List<String> EXT_DEPENDENCY_TYPES = asList("jar");

    // Possibly should be a parameter.
    private static final List<String> ACCEPTED_DEPENDENCY_SCOPES = asList(
        Artifact.SCOPE_COMPILE,
        Artifact.SCOPE_RUNTIME,
        Artifact.SCOPE_SYSTEM);

    private final PackagingTask[] packagingTasks =
        new PackagingTask[] {
            new AddDependenciesTask(),
            new AddDiffusionResourcesTask(),
            new AddProjectOutputTask(),
            new CreateManifestTask(), };

    @Override
    public void execute() throws MojoExecutionException {

        try {
            final File darFile =
                getDarFile(buildDirectory, finalName, classifier);

            getLog().info("Assembling DAR [" + project.getArtifactId() + "]");

            final MavenArchiver mvnArchiver = new MavenArchiver();
            mvnArchiver.setArchiver(jarArchiver);
            mvnArchiver.setOutputFile(darFile);

            final DARMojoContext context =
                new DARMojoContextImpl(mvnArchiver.getArchiver());

            for (final PackagingTask t : packagingTasks) {
                t.perform(context);
            }

            mvnArchiver.createArchive(session, project, archiver);

            if (classifier != null) {
                projectHelper.attachArtifact(
                    project, "dar", classifier, darFile);
            }
            else {
                project.getArtifact().setFile(darFile);
            }
        }
        // CHECKSTYLE.OFF: IllegalCatch
        catch (final Exception e) {
            throw new MojoExecutionException("Error assembling DAR", e);
        }
        // CHECKSTYLE.ON: IllegalCatch
    }

    private static File getDarFile(
        final String basedir, final String finalName, final String classifier) {

        final String c;

        if (classifier == null) {
            c = "";
        }
        else if (!classifier.trim().isEmpty() && !classifier.startsWith("-")) {
            c = "-" + classifier;
        }
        else {
            c = classifier;
        }

        return new File(basedir, finalName + c + ".dar");
    }

    private final class DARMojoContextImpl implements DARMojoContext {

        private final JarArchiver jarArchiver;

        public DARMojoContextImpl(final JarArchiver jarArchiver) {
            this.jarArchiver = jarArchiver;
        }

        @Override
        public MavenProject getProject() {
            return project;
        }

        @Override
        public Log getLog() {
            return DARMojo.this.getLog();
        }

        @Override
        public MavenArchiveConfiguration getArchiveConfiguration() {
            return archiver;
        }

        @Override
        public String getMinimumDiffusionVersion() {
            return minimumDiffusionVersion;
        }

        @Override
        public Archiver getArchiver() {
            return jarArchiver;
        }

        @Override
        public File getOutputDirectory() {
            return outputDirectory;
        }

        @Override
        public String[] getOutputIncludes() {
            return outputIncludes;
        }

        @Override
        public String[] getOutputExcludes() {
            return outputExcludes;
        }

        @Override
        public String getPrefixDirectoryName() {
            return project.getArtifactId();
        }

        @Override
        public String getExtDirectoryName() {
            return "ext";
        }

        @Override
        public List<String> getExtTypes() {
            return EXT_DEPENDENCY_TYPES;
        }

        @Override
        public List<String> getAcceptedDependencyScopes() {
            return ACCEPTED_DEPENDENCY_SCOPES;
        }

        @Override
        public File getDiffusionResourceDirectory() {
            return diffusionResourceDirectory;
        }

        @Override
        public String[] getDiffusionIncludes() {
            return diffusionIncludes;
        }

        @Override
        public String[] getDiffusionExcludes() {
            return diffusionExcludes;
        }
    }

    static <T> T or(final T one, final T two) {
        if (one != null) {
            return one;
        }

        return two;
    }
}
