package prbetter;

import prbetter.controller.PullRequestController;

/**
 * 이 클래스는 {@code main} 메서드를 가지는 애플리케이션 진입점이다.
 */

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
