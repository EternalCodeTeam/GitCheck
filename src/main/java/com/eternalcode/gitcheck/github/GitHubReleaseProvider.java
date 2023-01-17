package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class GitHubReleaseProvider implements GitReleaseProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_LATEST_RELEASE = "https://api.github.com/repos/%s/releases/latest";

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
        String getUrl = String.format(GET_LATEST_RELEASE, repository.getFullName());

        try {
            URL url = new URL(getUrl);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                throw new GitException("The URL is not an HTTP URL");
            }

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new GitException("The release of the repository " + repository.getFullName() + " was not found");
            }

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new GitException("The response code is not 200");
            }

            String response = this.readResponse(httpURLConnection);

            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(response);

            if (!(parsed instanceof JSONObject)) {
                throw new GitException("The response is not a JSON object");
            }

            return (JSONObject) parsed;
        } catch (IOException exception) {
            throw new GitException("Invalid URL", exception);
        } catch (ParseException exception) {
            throw new GitException("Invalid JSON response", exception);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines()
                    .collect(Collectors.joining());
        }
    }

}
