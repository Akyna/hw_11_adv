package com.amboiko.sax.model;

import java.util.List;

public class Build {
    private List<Plugin> plugins;

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Build{");
        sb.append("plugins=").append(plugins);
        sb.append('}');
        return sb.toString();
    }
}
