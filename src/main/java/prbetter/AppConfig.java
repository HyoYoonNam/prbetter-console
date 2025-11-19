package prbetter;

import prbetter.controller.PullRequestController;
import prbetter.repository.MemoryPullRequestRepository;
import prbetter.repository.PullRequestRepository;
import prbetter.service.PullRequestLoadService;
import prbetter.service.PullRequestReadService;
import prbetter.service.PullRequestRecommendService;

import java.net.http.HttpClient;

/**
 * 이 클래스는 애플리케이션 전체의 의존성 주입과 객체 생성에 대한 책임을 가진다.
 */

public class AppConfig {
    private final MemoryPullRequestRepository repository = new MemoryPullRequestRepository();

    public PullRequestController controller() {
        return new PullRequestController(repository(), loadService(), recommendService());
    }

    public PullRequestRecommendService recommendService() {
        return new PullRequestRecommendService(repository);
    }

    public PullRequestLoadService loadService() {
        return new PullRequestLoadService(repository, readService());
    }

    public PullRequestReadService readService() {
        return new PullRequestReadService(HttpClient.newHttpClient());
    }

    public PullRequestRepository repository() {
        return repository;
    }
}
