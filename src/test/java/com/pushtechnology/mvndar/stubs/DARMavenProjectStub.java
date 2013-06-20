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

package com.pushtechnology.mvndar.stubs;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

import java.io.File;

import org.apache.maven.project.MavenProject;

/**
 * Project stub.
 * 
 * <p>
 * Most behavior is delegated by the parent to the DARModelStub.
 * 
 * @author Philip Aston
 */
public class DARMavenProjectStub extends MavenProject {

    public DARMavenProjectStub(final File pom) throws Exception {
	super(new DARModelStub());

	setExecutionProject(this);

	setFile(pom);

	setArtifact(new DARArtifactStub());

	setDependencyArtifacts(emptySet());
	setArtifacts(emptySet());
	setPluginArtifacts(emptySet());
	setReportArtifacts(emptySet());
	setExtensionArtifacts(emptySet());
	setRemoteArtifactRepositories(emptyList());
	setPluginArtifactRepositories(emptyList());
	setCollectedProjects(emptyList());
	setActiveProfiles(emptyList());
    }
}
