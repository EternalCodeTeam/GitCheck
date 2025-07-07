package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GitHubReleaseProvider implements GitReleaseProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_LATEST_RELEASE_URL = "https://api.github.com/repos/%s/releases/latest";

    private final HttpClient httpClient;

    public GitHubReleaseProvider(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public GitHubReleaseProvider() {
        this(HttpClient.newHttpClient());
    }

    @Override
    public GitRelease getLatestRelease(GitRepository repository) {
        JSONObject json = this.requestLastRelease(repository);

        return GitRelease.builder()
                .name(JSONUtil.asString(json, "name"))
                .branch(JSONUtil.asString(json, "target_commitish"))
                .tag(JSONUtil.asGitTag(json, "tag_name"))
                .pageUrl(JSONUtil.asString(json, "html_url"))
                .publishedAt(JSONUtil.asInstant(json, "published_at"))
                .build();
    }

    private JSONObject requestLastRelease(GitRepository repository) {
        String formattedUrl = String.format(GET_LATEST_RELEASE_URL, repository.getFullName());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(formattedUrl))
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {
                case 404 -> throw new GitException("Repository or release not found: " + repository.getFullName() + " (404)");
                case 403 -> throw new GitException("Rate limit exceeded or access forbidden (403)");
                case 401 -> throw new GitException("Authentication required (401)");
                case 200 -> {
                }
                default -> throw new GitException("Unexpected response code: " + response.statusCode());
            }

            String responseBody = response.body();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(responseBody);

            if (!(parsed instanceof JSONObject)) {
                throw new GitException("Invalid JSON response: not a JSON object");
            }

            return (JSONObject) parsed;
        }
        catch (IOException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new GitException("Network error during HTTP request to GitHub API", exception);
        }
        catch (ParseException exception) {
            throw new GitException("Invalid JSON response: parse error", exception);
        }
    }
}
