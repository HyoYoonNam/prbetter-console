package prbetter.view;

public final class OutputView {
    private static final String ERROR_PREFIX = "[ERROR] ";

    private OutputView() {
    }

    public static void printRecommendedPullRequest(String title, String htmlUrl) {
        System.out.println("이 PR을 리뷰해 보세요!");
        System.out.println("제목: " + title);
        System.out.println("링크: " + htmlUrl);
    }

    public static void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }
}
