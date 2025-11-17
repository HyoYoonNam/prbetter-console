package prbetter.repository;

import prbetter.domain.GitHubRepositoryName;
import prbetter.domain.PullRequest;

import java.util.*;

public final class MemoryPullRequestRepository implements PullRequestRepository {
    private static final List<PullRequest> EMPTY_LIST = Collections.emptyList();
    private static final String REPOSITORY_NO_EXISTS = "저장되지 않은 리포지토리입니다.";
    private static final String INDEX_OUT_OF_BOUNDS = "리포지토리의 최대 인덱스(%d)를 벗어났습니다.";

    private final Map<GitHubRepositoryName, List<PullRequest>> store = new HashMap<>();

    @Override
    public PullRequest save(GitHubRepositoryName name, PullRequest pullRequest) {
        if (!store.containsKey(name)) {
            store.put(name, new ArrayList<>());
        }

        store.get(name).add(pullRequest);

        return pullRequest;
    }

    @Override
    public List<PullRequest> save(GitHubRepositoryName name, List<PullRequest> pullRequests) {
        if (!store.containsKey(name)) {
            store.put(name, new ArrayList<>());
        }

        store.get(name).addAll(pullRequests);

        return pullRequests;
    }

    @Override
    public PullRequest findByIndex(GitHubRepositoryName name, int index) {
        List<PullRequest> pullRequests = store.get(name);

        if (pullRequests == null) {
            throw new IllegalArgumentException(REPOSITORY_NO_EXISTS);
        }

        int maxIndex = pullRequests.size() - 1;
        if (index < 0 || index > maxIndex) {
            throw new IllegalArgumentException(String.format(INDEX_OUT_OF_BOUNDS, maxIndex));
        }

        return pullRequests.get(index);
    }

    @Override
    public List<PullRequest> findAll(GitHubRepositoryName name) {
        return List.copyOf(store.getOrDefault(name, EMPTY_LIST));
    }

    @Override
    public int sizeOf(GitHubRepositoryName name) {
        return store.getOrDefault(name, EMPTY_LIST).size();
    }

    @Override
    public boolean has(GitHubRepositoryName name) {
        return store.containsKey(name);
    }
}
