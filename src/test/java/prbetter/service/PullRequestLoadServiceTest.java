package prbetter.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import prbetter.domain.PullRequest;
import prbetter.repository.PullRequestRepository;

import java.io.IOException;
import java.util.List;

class PullRequestLoadServiceTest {
    @Test
    void PullRequest들을_리포지토리에_로드한다() throws IOException, InterruptedException {
        // given
        PullRequestReadService mockReadService = mock();
        when(mockReadService.readAllPages(anyString())).thenReturn(createPullRequests());
        PullRequestRepository mockRepository = mock();

        PullRequestLoadService lodeService = new PullRequestLoadService(mockRepository, mockReadService);

        String repositoryName = "java-lotto-8";

        // when
        lodeService.load(repositoryName);

        // then
        verify(mockRepository, times(2)).save(eq(repositoryName), any(PullRequest.class));
    }

    private static List<PullRequest> createPullRequests() {
        String sampleHtmlUrl = "https://example.com";
        return List.of(
                // Valid pull requests
                new PullRequest("[로또] 남효윤 미션 제출합니다.", sampleHtmlUrl),
                new PullRequest("[로또] 우테코 미션 제출합니다.", sampleHtmlUrl),
                // Invalid pull requests
                new PullRequest("남효윤 미션 제출합니다.", sampleHtmlUrl),        // [<미션명>] 없음
                new PullRequest("[로또] 남효윤 미션 제출합니다!!", sampleHtmlUrl)  // '.'으로 끝나지 않음
        );
    }
}
