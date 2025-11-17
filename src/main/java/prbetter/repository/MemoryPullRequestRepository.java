package prbetter.repository;

import prbetter.domain.PullRequest;

import java.util.*;

public final class MemoryPullRequestRepository implements PullRequestRepository {
    private static final List<PullRequest> EMPTY_LIST = Collections.emptyList();
    private static final String REPOSITORY_NO_EXISTS = "저장되지 않은 리포지토리입니다.";
    private static final String INDEX_OUT_OF_BOUNDS = "리포지토리의 최대 인덱스(%d)를 벗어났습니다.";

    private static final Random random = new Random();

    private final Map<String, List<PullRequest>> store = new HashMap<>();

    @Override
    public PullRequest save(String repositoryName, PullRequest pullRequest) {
        if (!store.containsKey(repositoryName)) {
            store.put(repositoryName, new ArrayList<>());
        }

        store.get(repositoryName).add(pullRequest);

        return pullRequest;
    }

    @Override
    public List<PullRequest> save(String repositoryName, List<PullRequest> pullRequests) {
        if (!store.containsKey(repositoryName)) {
            store.put(repositoryName, new ArrayList<>());
        }

        store.get(repositoryName).addAll(pullRequests);

        return pullRequests;
    }

    @Override
    public PullRequest findByIndex(String repositoryName, int index) {
        List<PullRequest> pullRequests = store.get(repositoryName);

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
    public List<PullRequest> findAll(String repositoryName) {
        return List.copyOf(store.getOrDefault(repositoryName, EMPTY_LIST));
    }

    @Override
    public int sizeOf(String repositoryName) {
        return store.getOrDefault(repositoryName, EMPTY_LIST).size();
    }
}
