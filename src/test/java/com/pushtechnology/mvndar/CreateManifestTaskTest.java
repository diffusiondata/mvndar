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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unit tests for {@link CreateManifest}.
 *
 * @author Philip Aston
 */
public class CreateManifestTaskTest {

    @Mock
    private Log log;

    @Mock
    private DARMojoContext context;

    private final MavenArchiveConfiguration archiveConfiguration = new MavenArchiveConfiguration();

    @Before
    public void setUp() {
	initMocks(this);

	when(context.getLog()).thenReturn(log);
	when(context.getArchiveConfiguration())
		.thenReturn(archiveConfiguration);
    }

    @Test
    public void test() throws Exception {
	when(context.getMinimumDiffusionVersion()).thenReturn("some version");

	final CreateManifestTask packageManifest = new CreateManifestTask();
	packageManifest.perform(context);

	final Map<String, String> manifestEntries =
		archiveConfiguration.getManifestEntries();
	assertEquals(1, manifestEntries.size());
	assertEquals(
		Pair.of("Diffusion-Version", "some version"),
		manifestEntries.entrySet().iterator().next());
    }
}
