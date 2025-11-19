package prbetter;

import prbetter.controller.PullRequestController;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        PullRequestController controller = new PullRequestController(
                appConfig.repository(),
                appConfig.loadService(),
                appConfig.recommendService());

        controller.run();
    }
}
