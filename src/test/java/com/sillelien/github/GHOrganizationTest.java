package com.sillelien.github;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

public class GHOrganizationTest extends AbstractGitHubApiTestBase {

    public static final String GITHUB_API_TEST = "github-api-test";
    private GHOrganization org;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        org = gitHub.getOrganization("dollar-github-api-test-org");
        GHRepository repository = org.getRepository(GITHUB_API_TEST);
        if(repository != null) {
            repository.delete();
            Thread.sleep(1000);
        }
    }


    @Test
    public void testCreateRepository() throws IOException {
        GHRepository repository = org.createRepository(GITHUB_API_TEST,
            "a test repository used to test kohsuke's github-api", "http://github-api.kohsuke.org/", "Core Developers", true);
        assertNotNull(repository);
    }

    @Test
    public void testCreateRepositoryWithAutoInitialization() throws IOException {
        GHRepository repository = org.createRepository(GITHUB_API_TEST)
                .description("a test repository used to test kohsuke's github-api")
                .homepage("http://github-api.kohsuke.org/")
                .team(org.getTeamByName("Core Developers"))
                .autoInit(true).create();
        assertNotNull(repository);
        assertNotNull(repository.getReadme());
    }

    @After
    public void cleanUp() throws Exception {
        GHRepository repository = org.getRepository(GITHUB_API_TEST);
        repository.delete();
        Thread.sleep(1000);
    }
}
