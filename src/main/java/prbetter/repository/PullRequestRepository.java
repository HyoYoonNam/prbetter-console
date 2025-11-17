package prbetter.repository;

import prbetter.domain.GitHubRepositoryName;
import prbetter.domain.PullRequest;

import java.util.List;

public interface PullRequestRepository {
    PullRequest save(GitHubRepositoryName name, PullRequest pullRequest);

    List<PullRequest> save(GitHubRepositoryName name, List<PullRequest> pullRequests);

    PullRequest findByIndex(GitHubRepositoryName name, int index);

    List<PullRequest> findAll(GitHubRepositoryName name);

    int sizeOf(GitHubRepositoryName name);

    boolean has(GitHubRepositoryName name);
}
