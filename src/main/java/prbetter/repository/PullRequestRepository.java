package prbetter.repository;

import prbetter.domain.PullRequest;

import java.util.List;

public interface PullRequestRepository {
    PullRequest save(String repositoryName, PullRequest pullRequest);

    List<PullRequest> findAll(String repositoryName);
}
