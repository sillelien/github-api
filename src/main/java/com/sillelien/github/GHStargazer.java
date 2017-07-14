package com.sillelien.github;


import java.util.Date;

/**
 * A stargazer at a repository on GitHub.
 *
 * @author noctarius
 */
public class GHStargazer {

    private GHRepository repository;
    private String starred_at;
    private GHUser user;

    /**
     * Gets the repository that is stargazed
     *
     * @return the starred repository
     */
    public GHRepository getRepository() {
        return repository;
    }

    /**
     * Gets the date when the repository was starred, however old stars before
     * August 2012, will all show the date the API was changed to support starred_at.
     *
     * @return the date the stargazer was added
     */
    public Date getStarredAt() {
        return GitHub.parseDate(starred_at);
    }

    /**
     * Gets the user that starred the repository
     *
     * @return the stargazer user
     */
    public GHUser getUser() {
        return user;
    }

    void wrapUp(GHRepository repository) {
        this.repository = repository;
        user.wrapUp(repository.root);
    }
}