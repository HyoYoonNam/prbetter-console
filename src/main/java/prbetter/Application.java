package prbetter;

import prbetter.controller.PullRequestController;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        AppConfig appConfig = new AppConfig();
        PullRequestController controller = new PullRequestController(
                appConfig.repository(),
                appConfig.loadService(),
                appConfig.recommendService());

        controller.run();
    }
}
