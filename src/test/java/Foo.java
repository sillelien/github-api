import com.sillelien.github.GHRepository.Contributor;
import com.sillelien.github.GHUser;
import com.sillelien.github.GitHub;

/**
 * @author Kohsuke Kawaguchi
 */
public class Foo {
    public static void main(String[] args) throws Exception {
        GitHub gh = GitHub.connect();
        for (Contributor c : gh.getRepository("kohsuke/yo").listContributors()) {
            System.out.println(c);
        }
    }

    private static void testRateLimit() throws Exception {
        GitHub g = GitHub.connectAnonymously();
        for (GHUser u : g.getOrganization("sillelien").listMembers()) {
            u.getFollowersCount();
        }
    }
}
