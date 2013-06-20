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

import org.apache.maven.plugin.testing.stubs.ArtifactStub;

/**
 * Artifact stub.
 * 
 * @author Philip Aston
 */
public class DARArtifactStub extends ArtifactStub {

    private String groupId;

    private String artifactId;

    private String classifier;

    @Override
    public String getType() {
	return "dar";
    }

    @Override
    public String getArtifactId()
    {
	if (artifactId == null)
	{
	    return "simple";
	}
	else
	{
	    return artifactId;
	}
    }

    @Override
    public void setArtifactId(final String _artifactId)
    {
	artifactId = _artifactId;
    }

    @Override
    public String getGroupId()
    {
	if (groupId == null)
	{
	    return "wartests";
	}
	else
	{
	    return groupId;
	}
    }

    @Override
    public void setGroupId(final String groupId)
    {
	this.groupId = groupId;
    }

    @Override
    public String getClassifier()
    {
	return classifier;
    }

    public void setClassifier(final String classifier)
    {
	this.classifier = classifier;
    }

    @Override
    public boolean hasClassifier()
    {
	return classifier != null;
    }

    @Override
    public String getVersion() {
	return "0.0-Test";
    }
}
