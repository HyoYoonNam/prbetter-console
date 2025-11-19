package prbetter.controller;

import prbetter.domain.GitHubRepositoryName;
import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;
import prbetter.service.PullRequestLoadService;
import prbetter.service.PullRequestRecommendService;
import prbetter.view.InputView;
import prbetter.view.OutputView;

/**
 * 이 클래스는 프로그램의 전체 흐름을 제어하는 책임을 가진다.
 *
 * <p>전체 흐름은 'GitHub 리포지토리 이름 입력받기' -> '리포지토리에 있는 Pull request 중 하나 고르기' -> '고른 Pull request를 추천'으로 진행된다.
 *
 * <p>이 클래스는 {@code final}이므로 상속이 불가하다.
 */

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

    /** 프로그램을 시작한다. */
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
