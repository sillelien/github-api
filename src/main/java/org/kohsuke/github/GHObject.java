package org.kohsuke.github;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.IOException;
import java.lang.reflect.Field;
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

    /**
     * String representation to assist debugging and inspection. The output format of this string
     * is not a committed part of the API and is subject to change.
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, TOSTRING_STYLE, null, null, false, false) {
            @Override
            protected boolean accept(Field field) {
                return super.accept(field) && !field.isAnnotationPresent(SkipFromToString.class);
            }
        }.toString();
    }

    private static final ToStringStyle TOSTRING_STYLE = new ToStringStyle() {
        {
            this.setUseShortClassName(true);
        }

        @Override
        public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
            // skip unimportant properties. '_' is a heuristics as important properties tend to have short names
            if (fieldName.contains("_"))
                return;
            // avoid recursing other GHObject
            if (value instanceof GHObject)
                return;
            // likewise no point in showing root
            if (value instanceof GitHub)
                return;

            super.append(buffer,fieldName,value,fullDetail);
        }
    };
}
