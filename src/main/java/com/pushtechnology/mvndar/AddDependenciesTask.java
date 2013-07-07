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
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.logging.Log;

/**
 * Package the project dependencies in the {@code ext} directory.
 * 
 * @author Philip Aston
 */
class AddDependenciesTask implements PackagingTask {

    @Override
    public void perform(final DARMojoContext context) throws IOException {

	@SuppressWarnings("unchecked")
	final Set<Artifact> dependencies =
		context.getProject().getDependencyArtifacts();

	final Log log = context.getLog();

	for (final Artifact a : dependencies) {

	    if (a.isOptional()) {
		log.debug("Ignoring optional dependency: " + a);
		continue;
	    }

	    if (!context.getAcceptedDependencyScopes().contains(a.getScope())) {
		log.debug("Ignoring dependency (scope): " + a);
		continue;
	    }

	    if ("com.pushtechnology".equals(a.getGroupId()) &&
		    "diffusion-api".equals(a.getArtifactId())) {
		continue;
	    }

	    if (context.getExtTypes().contains(a.getType())) {
		final File f = a.getFile().getCanonicalFile();

		final File target =
			FileUtils.getFile(
				context.getPrefixDirectoryName(),
				context.getExtDirectoryName(),
				f.getName());

		context.getArchiver().addFile(f, target.toString());

		log.debug(" Dependency " + a + " has been copied to " + target);
	    }
	    else {
		log.debug("Ignoring dependency: " + a);
	    }
	}
    }
}