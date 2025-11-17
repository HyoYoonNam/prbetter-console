package prbetter.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prbetter.domain.PullRequest;

import java.util.List;

class MemoryPullRequestRepositoryTest {
    MemoryPullRequestRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryPullRequestRepository();
    }

    @Test
    void 저장과_조회_정상_흐름() {
        // given
        String repositoryName = "kotlin-lotto-8";
        PullRequest pullRequest = new PullRequest("[로또] 남효윤 미션 제출합니다.", "https://example.com");

        // when
        repository.save(repositoryName, pullRequest);

        // then
        List<PullRequest> foundPullRequests = repository.findAll(repositoryName);
        assertThat(foundPullRequests)
                .as("PullRequest 객체 1개가 정상적으로 저장되었는지 검증")
                .hasSize(1)
                .as("조회된 PullRequest 객체가 저장한 것과 같은지 검증")
                .containsExactly(pullRequest);
    }

    @Test
    void 저장된_적이_없는_리포지토리_조회시_빈_리스트를_리턴() {
        List<PullRequest> foundPullRequests = repository.findAll("java-lotto-8");

        assertThat(foundPullRequests).isEmpty();
    }
}
