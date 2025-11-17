package prbetter;

import prbetter.controller.PullRequestController;
import prbetter.repository.MemoryPullRequestRepository;
import prbetter.service.PullRequestLoadService;
import prbetter.service.PullRequestReadService;
import prbetter.service.PullRequestRecommendService;

import java.io.IOException;
import java.net.http.HttpClient;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        MemoryPullRequestRepository repository = new MemoryPullRequestRepository();
        PullRequestReadService readService = new PullRequestReadService(HttpClient.newHttpClient());
        PullRequestController controller = new PullRequestController(
                repository,
                new PullRequestLoadService(repository, readService),
                new PullRequestRecommendService(repository)
        );

        controller.run();
    }
}
