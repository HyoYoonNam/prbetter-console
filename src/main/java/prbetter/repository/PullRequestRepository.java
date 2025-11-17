package prbetter.repository;

import prbetter.domain.PullRequest;

import java.util.List;

public interface PullRequestRepository {
    PullRequest save(String repositoryName, PullRequest pullRequest);

    List<PullRequest> save(String repositoryName, List<PullRequest> pullRequests);

    PullRequest findByIndex(String repositoryName, int index);

    List<PullRequest> findAll(String repositoryName);

    int sizeOf(String repositoryName);

    boolean has(String repositoryName);
}
