package prbetter.service;

import prbetter.domain.PullRequest;
import prbetter.mapper.JsonPullRequestMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public final class PullRequestReadService {
    private static final String API_URI_PREFIX = "https://api.github.com/repos/woowacourse-precourse/";
    private static final String API_URI_POSTFIX = "/pulls";
    private static final int HTTP_PAGE_NOT_FOUND = 404;
    private static final int HTTP_OK = 200;
    private static final String WOOWACOURSE_PRECOURSE_REPOSITORIES = "https://github.com/orgs/woowacourse-precourse/repositories";

    private final HttpClient client;

    public PullRequestReadService(HttpClient httpClient) {
        this.client = httpClient;
    }

    public List<PullRequest> readAllPages(String repositoryName) throws IOException, InterruptedException {
        List<PullRequest> result = new ArrayList<>();
        int currentPage = 0;

        while (true) {
            HttpResponse<String> httpResponse = read(repositoryName, ++currentPage);

            List<PullRequest> pullRequests = JsonPullRequestMapper.mapFromArray(httpResponse.body());
            result.addAll(pullRequests);

            if (isLastPage(httpResponse)) {
                break;
            }
        }

        return result;
    }

    private HttpResponse<String> read(String repositoryName, int page) throws IOException, InterruptedException {
        HttpRequest httpRequest = getRequest(repositoryName, page);
        return getResponse(httpRequest);
    }

    private HttpRequest getRequest(String repositoryName, int page) {
        URI apiUri = URI.create(API_URI_PREFIX + repositoryName + API_URI_POSTFIX + "?page=" + page);
        return HttpRequest.newBuilder()
                .GET()
                .uri(apiUri)
                .header("Accept", "application/vnd.github.json")
                .build();
    }

    private HttpResponse<String> getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HTTP_PAGE_NOT_FOUND) {
            throw new IllegalArgumentException("존재하지 않는 리포지토리입니다. See: " + WOOWACOURSE_PRECOURSE_REPOSITORIES);
        }

        if (response.statusCode() != HTTP_OK) {
            throw new IllegalStateException("HTTP 통신에 오류가 발생했습니다." + response.body());
        }

        return response;
    }

    private boolean isLastPage(HttpResponse<String> response) {
        return response.headers().firstValue("link")
                .map(header -> !header.contains("rel=\"next\"")) // rel="next"가 없거나
                .orElse(true); // link 헤더가 아예 없거나
    }
}
