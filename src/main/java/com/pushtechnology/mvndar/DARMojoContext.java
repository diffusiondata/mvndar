/*
 * Copyright 2013 Push Technology
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

import java.io.File;
import java.util.List;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.Archiver;

/**
 * Provide configuration and services to packaging tasks.
 *
 * @author Philip Aston
 */
interface DARMojoContext {

    MavenProject getProject();

    Log getLog();

    MavenArchiveConfiguration getArchiveConfiguration();

    String getMinimumDiffusionVersion();

    Archiver getArchiver();

    File getOutputDirectory();

    String[] getOutputIncludes();

    String[] getOutputExcludes();

    String getExtDirectoryName();

    List<String> getExtTypes();

    List<String> getAcceptedDependencyScopes();

    File getDiffusionDirectory();

    String[] getDiffusionIncludes();

    String[] getDiffusionExcludes();
}
