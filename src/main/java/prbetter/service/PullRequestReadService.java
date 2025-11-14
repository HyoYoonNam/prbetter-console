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

    private final URI apiUri;
    private final HttpClient client;

    public static PullRequestReadService of(String repositoryName, HttpClient httpClient) {
        URI apiUri = URI.create(API_URI_PREFIX.concat(repositoryName).concat(API_URI_POSTFIX));
        return new PullRequestReadService(apiUri, httpClient);
    }

    private PullRequestReadService(URI apiUri, HttpClient httpClient) {
        this.apiUri = apiUri;
        this.client = httpClient;
    }

    public List<PullRequest> readAllPages() throws IOException, InterruptedException {
        List<PullRequest> result = new ArrayList<>();
        int currentPage = 0;

        while (true) {
            HttpResponse<String> httpResponse = read(++currentPage);

            List<PullRequest> pullRequests = JsonPullRequestMapper.mapFromArray(httpResponse.body());
            result.addAll(pullRequests);

            if (isLastPage(httpResponse)) {
                break;
            }
        }

        return result;
    }

    private HttpResponse<String> read(int page) throws IOException, InterruptedException {
        HttpRequest httpRequest = getRequest(page);
        return getResponse(httpRequest);
    }

    private HttpRequest getRequest(int page) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiUri.toString() + "?page=" + page))
                .header("Accept", "application/vnd.github.json")
                .build();
    }

    private HttpResponse<String> getResponse(HttpRequest httpRequest)
            throws IOException, InterruptedException {
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private boolean isLastPage(HttpResponse<String> response) {
        return response.headers().firstValue("link")
                .map(header -> !header.contains("rel=\"next\"")) // rel="next"가 없거나
                .orElse(true); // link 헤더가 아예 없거나
    }
}
