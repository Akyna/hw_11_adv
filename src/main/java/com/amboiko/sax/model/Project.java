package com.amboiko.sax.model;

import java.util.List;

public class Project {
    private String modelVersion;
    private String groupId;
    private String artifactId;
    private String version;
    private List<Property> properties;
    private List<Dependency> dependencies;
    private Build build;

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{\n");
        sb.append("  modelVersion='").append(modelVersion).append("',\n");
        sb.append("  groupId='").append(groupId).append("',\n");
        sb.append("  artifactId='").append(artifactId).append("',\n");
        sb.append("  version='").append(version).append("',\n");
        sb.append("  properties=").append(properties).append("\n");
        sb.append("  dependencies=").append(dependencies).append("\n");
        sb.append("  build=").append(build).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
