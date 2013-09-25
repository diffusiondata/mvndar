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

import java.io.IOException;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.plugin.logging.Log;

/**
 * Modify the manifest to add Diffusion headers.
 *
 * @author Philip Aston
 */
class CreateManifestTask implements PackagingTask {

    @Override
    public void perform(final DARMojoContext context) throws IOException {

        final Log log = context.getLog();
        final MavenArchiveConfiguration archiveConfiguration =
                context.getArchiveConfiguration();

        archiveConfiguration.getManifestEntries().put(
                "Diffusion-Version",
                context.getMinimumDiffusionVersion());

        log.debug("Manifest entries: " +
                archiveConfiguration.getManifestEntries());
    }
}
