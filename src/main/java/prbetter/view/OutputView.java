package prbetter.view;

/**
 * 이 클래스는 프로그램의 출력을 담당한다.
 *
 * <p>이 클래스는 인스턴스 생성과 상속이 불가능한 정적 유틸 클래스이다.
 */

public final class OutputView {
    private static final String ERROR_PREFIX = "[ERROR] ";

    private OutputView() {
    }

    /** 추천할 Pull request에 대한 제목과 url 링크를 입력받아 출력한다. */
    public static void printRecommendedPullRequest(String title, String htmlUrl) {
        System.out.println("이 PR을 리뷰해 보세요!");
        System.out.println("제목: " + title);
        System.out.println("링크: " + htmlUrl);
    }

    /** 입력받은 에러 메시지에 정해진 prefix를 붙여 출력한다. */
    public static void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }
}
