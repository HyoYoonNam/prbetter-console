package prbetter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import prbetter.controller.PullRequestController;
import prbetter.service.PullRequestReadService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";
    private static final int HTTP_OK = 200;

    @Test
    void 정상_흐름_테스트() {
        run("kotlin-lotto-8");

        assertThat(output()).contains(
                "이 PR을 리뷰해 보세요!",
                "제목: [로또] 채승우 미션 제출합니다.",
                "링크: https://github.com/woowacourse-precourse/kotlin-lotto-8/pull/20"
        );
    }

    @Test
    void 규칙을_위반하는_리포지토리_이름을_입력하면_에러_메시지를_출력한다() {
        runException("kotlin lotto 8");

        assertThat(output()).contains(ERROR_MESSAGE);
    }

    @Override
    protected void runMain() {
        AppConfig appConfig = new AppConfig();
        AppConfig spyConfig = spy(appConfig);

        try {
            HttpClient mockClient = createMockClientResponse3PullRequestsContainsOnly1ValidTitlePullRequest();
            doReturn(new PullRequestReadService(mockClient)).when(spyConfig).readService();

            PullRequestController controller = spyConfig.controller();

            controller.run();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpClient createMockClientResponse3PullRequestsContainsOnly1ValidTitlePullRequest()
            throws IOException, InterruptedException {
        HttpClient mockClient = mock();
        HttpResponse<String> pageResponse = mock();

        // pageResponse setting
        Map<String, List<String>> linkHeaderExcludedHeaderMap = Map.of(
                "content-type", List.of("application/json; charset=utf-8")
        );
        when(pageResponse.statusCode()).thenReturn(HTTP_OK);
        when(pageResponse.headers()).thenReturn(HttpHeaders.of(linkHeaderExcludedHeaderMap, (k, v) -> true));
        when(pageResponse.body()).thenReturn(
                """
                        [
                            {
                                "html_url": "https://github.com/woowacourse-precourse/kotlin-lotto-8/pull/20",
                                "id": 2970844659,
                                "title": "[로또] 채승우 미션 제출합니다."
                            },
                            {
                                "html_url": "https://github.com/woowacourse-precourse/kotlin-lotto-8/pull/19",
                                "id": 2970748786,
                                "title": "[로또] 윤혁진"
                            },
                            {
                                "html_url": "https://github.com/woowacourse-precourse/kotlin-lotto-8/pull/18",
                                "id": 2970667631,
                                "title": "[로또] 이수민 미션 제출합니다!!"
                            }
                        ]
                        """
        );

        // mockClient setting
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(pageResponse);

        return mockClient;
    }
}
