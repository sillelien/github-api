package org.kohsuke.github;

import org.junit.Test;
import org.kohsuke.github.GHRepository.Contributor;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Kohsuke Kawaguchi
 */
public class RepositoryTest extends AbstractGitHubApiTestBase {
    @Test
    public void subscription() throws Exception {
        GHRepository r = gitHub.getOrganization("dollar-github-api-test-org").getRepository("test-sub");
        assertNull(r.getSubscription());

        GHSubscription s = r.subscribe(true, false);
        assertEquals(s.getRepository(), r);

        s.delete();

        assertNull(r.getSubscription());
    }

    @Test
    public void listContributors() throws IOException {
        GHRepository r = gitHub.getOrganization("junit-team").getRepository("junit4");
        int i=0;
        boolean matched = false;

        for (Contributor c : r.listContributors()) {
            System.out.println(c.getName());
            assertTrue(c.getContributions()>0);
            if (c.getLogin().equals("stefanbirkner"))
                matched = true;
            if (i++ > 5)
                break;
        }

        assertTrue(matched);
    }

    @Test
    public void getPermission() throws Exception {
        kohsuke();
        GHRepository r = gitHub.getRepository("dollar-github-api-test-org/test-repo");
        assertEquals(GHPermissionType.ADMIN, r.getPermission("neilellis"));
        assertEquals(GHPermissionType.READ, r.getPermission("dude"));
        r = gitHub.getOrganization("apache").getRepository("groovy");
        try {
            r.getPermission("jglick");
            fail();
        } catch (HttpException x) {
            x.printStackTrace(); // good
            assertEquals(403, x.getResponseCode());
        }

        if (false) {
            // can't easily test this; there's no private repository visible to the test user
            r = gitHub.getOrganization("cloudbees").getRepository("private-repo-not-writable-by-me");
            try {
                r.getPermission("jglick");
                fail();
            } catch (FileNotFoundException x) {
                x.printStackTrace(); // good
            }
        }
    }

    private GHRepository getRepository() throws IOException {
        return gitHub.getOrganization("dollar-github-api-test-org").getRepository("test-repo");
    }

    @Test
    public void listLanguages() throws IOException {
        GHRepository r = gitHub.getRepository("sillelien/github-api");
        String mainLanguage = r.getLanguage();
        assertTrue(r.listLanguages().containsKey(mainLanguage));
    }

    @Test // Issue #261
    public void listEmptyContributors() throws IOException {
        GitHub gh = GitHub.connect();
        for (Contributor c : gh.getRepository("dollar-github-api-test-org/empty").listContributors()) {
            System.out.println(c);
        }
    }
}
