package prbetter.service;

import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;

import java.util.Random;

public final class PullRequestRecommendService {
    private static final String PULL_REQUEST_NO_EXISTS = "추천할 PR이 존재하지 않습니다.";
    private static final Random random = new Random();

    private final PullRequestRepository repository;

    public PullRequestRecommendService(PullRequestRepository repository) {
        this.repository = repository;
    }

    public PullRequest recommendFrom(String repositoryName) {
        int size = repository.sizeOf(repositoryName);
        if (size == 0) {
            throw new IllegalArgumentException(PULL_REQUEST_NO_EXISTS);
        }

        int randomIndex = random.nextInt(size);

        return repository.findByIndex(repositoryName, randomIndex);
    }
}
