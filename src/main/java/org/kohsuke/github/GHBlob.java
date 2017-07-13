package org.kohsuke.github;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Base64;

/**
 * @author Kanstantsin Shautsou
 * @author Kohsuke Kawaguchi
 * @see GHTreeEntry#asBlob()
 * @see GHRepository#getBlob(String)
 * @see <a href="https://developer.github.com/v3/git/blobs/#get-a-blob">Get a blob</a>
 */
public class GHBlob {
    private String content, encoding, url, sha;
    private long size;

    /**
     * API URL of this blob.
     */
    public URL getUrl() {
        return GitHub.parseURL(url);
    }

    public String getSha() {
        return sha;
    }

    /**
     * Number of bytes in this blob.
     */
    public long getSize() {
        return size;
    }

    public String getEncoding() {
        return encoding;
    }

    /**
     * Encoded content. You probably want {@link #read()}
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the actual bytes of the blob.
     */
    public InputStream read() {
        if (encoding.equals("base64")) {
            try {
                return  new ByteArrayInputStream(Base64.getDecoder().decode(content.getBytes("US-ASCII")));
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);    // US-ASCII is mandatory
            }
        }

        throw new UnsupportedOperationException("Unrecognized encoding: "+encoding);
    }
}
