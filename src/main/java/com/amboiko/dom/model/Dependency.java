package com.amboiko.dom.model;

public class Dependency {
    private String groupId;
    private String artifactId;
    private String version;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n\tDependency{");
        sb.append("groupId='").append(groupId).append("', ");
        sb.append("artifactId='").append(artifactId).append("', ");
        sb.append("version='").append(version).append("'");
        sb.append("}");
        return sb.toString();
    }
}
