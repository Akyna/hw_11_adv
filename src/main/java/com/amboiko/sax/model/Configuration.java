package com.amboiko.sax.model;

public class Configuration {
    private Archive archive;

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Configuration{");
        sb.append("archive=").append(archive);
        sb.append("}");
        return sb.toString();
    }
}
