package prbetter.view;

import camp.nextstep.edu.missionutils.Console;

public final class InputView {
    private static final String READ_REPOSITORY_NAME_PROMPT = "리포지토리 이름을 입력해 주세요: ";
    private static final String INPUT_IS_EMPTY = "입력이 비었습니다.";

    private InputView() {
    }

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
