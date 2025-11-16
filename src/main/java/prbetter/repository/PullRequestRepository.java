package prbetter.repository;

import prbetter.domain.PullRequest;

import java.util.List;
import java.util.Optional;

public interface PullRequestRepository {
    PullRequest save(String mission, PullRequest pullRequest);

    Optional<List<PullRequest>> findAll(String mission);
}
