package org.kohsuke.github;

/**
 * SSH public key.
 *
 * @author Kohsuke Kawaguchi
 */
public class GHKey {
    /*package almost final*/ GitHub root;

    protected String url, key, title;
    protected boolean verified;
    protected int id;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Something like "https://api.github.com/user/keys/73593"
     */
    public String getUrl() {
        return url;
    }

    public GitHub getRoot() {
        return root;
    }
    
    public boolean isVerified() {
        return verified;
    }

    /*package*/ GHKey wrap(GitHub root) {
        this.root = root;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GHKey{");
        sb.append("root=").append(root);
        sb.append(", url='").append(url).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", verified=").append(verified);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
