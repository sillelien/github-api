package com.sillelien.github;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * Most (all?) domain objects in GitHub seems to have these 4 properties.
 */
public abstract class GHObject {
    protected String url;
    protected int id;
    protected String created_at;
    protected String updated_at;

    /*package*/ GHObject() {
    }

    /**
     * When was this resource created?
     */
    public Date getCreatedAt() throws IOException {
        return GitHub.parseDate(created_at);
    }

    private Object createdAtStr(Date id, Class type) {
        return created_at;
    }

    /**
     * API URL of this object.
     */
    public URL getUrl() {
        return GitHub.parseURL(url);
    }

    /**
     * URL of this object for humans, which renders some HTML.
     */
    public abstract URL getHtmlUrl() throws IOException;

    /**
     * When was this resource last updated?
     */
    public Date getUpdatedAt() throws IOException {
        return GitHub.parseDate(updated_at);
    }

    /**
     * Unique ID number of this resource.
     */
    public int getId() {
        return id;
    }

    private Object intToString(int id, Class type) {
        return String.valueOf(id);
    }

    private Object urlToString(URL url, Class type) {
        return url==null ? null : url.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GHObject{");
        sb.append("url='").append(url).append('\'');
        sb.append(", id=").append(id);
        sb.append(", created_at='").append(created_at).append('\'');
        sb.append(", updated_at='").append(updated_at).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
