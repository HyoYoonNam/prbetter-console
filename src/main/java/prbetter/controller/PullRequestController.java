package prbetter.controller;

import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;
import prbetter.service.PullRequestLoadService;
import prbetter.service.PullRequestRecommendService;
import prbetter.view.InputView;
import prbetter.view.OutputView;

import java.io.IOException;

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

    public void run() throws IOException, InterruptedException {
        String repositoryName = getLoadedRepositoryName();

        PullRequest recommended = recommendService.recommendFrom(repositoryName);

        OutputView.printRecommendedPullRequest(recommended.title(), recommended.html_url());
    }

    private String getLoadedRepositoryName() throws IOException, InterruptedException {
        while (true) {
            String repositoryName = InputView.readRepositoryName();

            if (repository.has(repositoryName)) {
                return repositoryName;
            }

            try {
                loadService.load(repositoryName);
                return repositoryName;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
