package com.sillelien.github;

import java.io.IOException;

public class GHDeployKey {

    protected String url, key, title;
    protected boolean verified;
    protected int id;
    private GHRepository owner;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isVerified() {
        return verified;
    }

    public GHDeployKey wrap(GHRepository repo) {
        this.owner = repo;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GHDeployKey{");
        sb.append("url='").append(url).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", verified=").append(verified);
        sb.append(", id=").append(id);
        sb.append(", owner=").append(owner);
        sb.append('}');
        return sb.toString();
    }

    public void delete() throws IOException {
        new Requester(owner.root).method("DELETE").to(String.format("/repos/%s/%s/keys/%d", owner.getOwnerName(), owner.getName(), id));
    }
}
