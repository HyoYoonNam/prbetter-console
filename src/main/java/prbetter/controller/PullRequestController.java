package prbetter.controller;

import prbetter.domain.GitHubRepositoryName;
import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;
import prbetter.service.PullRequestLoadService;
import prbetter.service.PullRequestRecommendService;
import prbetter.view.InputView;
import prbetter.view.OutputView;

public final class PullRequestController {
    private final PullRequestRepository repository;
    private final PullRequestLoadService loadService;
    private final PullRequestRecommendService recommendService;

    public PullRequestController(PullRequestRepository repository,
                                 PullRequestLoadService loadService,
                                 PullRequestRecommendService recommendService) {
        this.repository = repository;
        this.loadService = loadService;
        this.recommendService = recommendService;
    }

    public void run() {
        GitHubRepositoryName repositoryName = getLoadedRepositoryName();

        PullRequest recommended = recommendService.recommendFrom(repositoryName);

        OutputView.printRecommendedPullRequest(recommended.title(), recommended.html_url());
    }

    private GitHubRepositoryName getLoadedRepositoryName() {
        while (true) {
            try {
                String input = InputView.readRepositoryName();
                GitHubRepositoryName repositoryName = new GitHubRepositoryName(input);

                if (repository.has(repositoryName)) {
                    return repositoryName;
                }

                loadService.load(repositoryName);

                return repositoryName;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
