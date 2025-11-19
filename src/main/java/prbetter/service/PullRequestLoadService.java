package prbetter.service;

import prbetter.domain.GitHubRepositoryName;
import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;

public final class PullRequestLoadService {
    private final PullRequestRepository pullRequestRepository;
    private final PullRequestReadService readService;

    public PullRequestLoadService(PullRequestRepository pullRequestRepository,
                                  PullRequestReadService readService) {
        this.pullRequestRepository = pullRequestRepository;
        this.readService = readService;
    }

    public void load(GitHubRepositoryName name) {
        readService.readAllPages(name).stream()
                .filter(PullRequest::isValidTitle)
                .forEach(pr -> pullRequestRepository.save(name, pr));
    }
}
