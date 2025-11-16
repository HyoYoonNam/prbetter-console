package prbetter.domain;

import java.util.regex.Pattern;

public record PullRequest(String title, String html_url) {
    /*
     * '[<미션명>] <이름> 미션 제출합니다.' 패턴과 매칭되는 정규 표현식
     * '[로또] 남효윤 미션 제출합니다.'
     * '[자동차 경주] 남효윤 미션 제출합니다.'
     * '[문자열 덧셈 계산기] 남효윤 미션 제출합니다.'
     */
    private static final String TITLE_FORMAT_REGEX = "^\\[" +  // '['로 시작해야 됨
            "[^\\s\\]]+" +                      // 공백이나 '['가 아닌 문자가 1번 이상 반복: [로또
            "(?:\\s[^\\s\\]]+)*" +              // '(공백 하나 + 공백이나 '['가 아닌 문자 하나 이상)'이 0회 이상 반복: [자동차 경주, [문자열 덧셈 계산기
            "] " +                              // ']' 닫고, 공백 하나
            "\\S+ " +                           // 이름 있고, 공백 하나
            "미션 제출합니다\\.$";                  // '미션 제출합니다.'로 끝나야 됨
    private static final Pattern TITLE_PATTERN = Pattern.compile(TITLE_FORMAT_REGEX);

    // TODO: Pattern 캐싱 전/후 성능 비교 해보기
    public boolean isValidTitle() {
        return TITLE_PATTERN.matcher(title).matches();
    }
}
