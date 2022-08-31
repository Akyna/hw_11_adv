package com.amboiko.sax.model;

public class Plugin {
    private String groupId;
    private String artifactId;
    private Configuration configuration;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plugin{");
        sb.append("groupId='").append(groupId).append('\'');
        sb.append(", artifactId='").append(artifactId).append('\'');
        sb.append(", configuration=").append(configuration);
        sb.append("}");
        return sb.toString();
    }
}
