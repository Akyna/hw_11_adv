package com.amboiko.dom.model;

import java.util.List;

public class Archive {
    private List<Manifest> manifests;

    public void setManifests(List<Manifest> manifests) {
        this.manifests = manifests;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Archive{");
        sb.append("manifests=").append(manifests);
        sb.append("}");
        return sb.toString();
    }
}
