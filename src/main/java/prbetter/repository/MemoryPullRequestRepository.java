package prbetter.repository;

import prbetter.domain.PullRequest;

import java.util.*;

public final class MemoryPullRequestRepository implements PullRequestRepository {
    private static final Map<String, List<PullRequest>> store = new HashMap<>();

    @Override
    public PullRequest save(String repositoryName, PullRequest pullRequest) {
        if (!store.containsKey(repositoryName)) {
            store.put(repositoryName, new ArrayList<>());
        }

        store.get(repositoryName).add(pullRequest);

//        store.

        return pullRequest;
    }

    @Override
    public List<PullRequest> findAll(String repositoryName) {
        return store.getOrDefault(repositoryName, Collections.emptyList());
    }
}
