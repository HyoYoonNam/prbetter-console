package prbetter.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 이 클래스는 사용자로부터 입력을 받는 것과, 입력을 받기 위한 프롬프트 출력을 담당한다.
 *
 * <p>이 클래스는 인스턴스 생성과 상속이 불가능한 정적 유틸 클래스이다.
 */

public final class InputView {
    private static final String READ_REPOSITORY_NAME_PROMPT = "리포지토리 이름을 입력해 주세요: ";
    private static final String INPUT_IS_EMPTY = "입력이 비었습니다.";

    private InputView() {
    }

    /**
     * 리포지토리 이름을 입력받아 리턴한다.
     *
     * @throws IllegalArgumentException 입력이 비어 있으면 발생한다.
     */
    public static String readRepositoryName() {
        System.out.print(READ_REPOSITORY_NAME_PROMPT);

        String repositoryName = readLine();
        if (repositoryName.isEmpty()) {
            throw new IllegalArgumentException(INPUT_IS_EMPTY);
        }

        return repositoryName;
    }

    private static String readLine() {
        return Console.readLine().strip();
    }
}
