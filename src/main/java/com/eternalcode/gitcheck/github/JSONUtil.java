package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitTag;
import java.time.Instant;
import java.util.Optional;
import org.json.simple.JSONObject;

final class JSONUtil {

    private JSONUtil() {
        throw new UnsupportedOperationException("Cannot create instance of utility class JSONUtil");
    }

    static String asString(JSONObject jsonObject, String key) {
        return asOpt(jsonObject, key, String.class)
                .orElseThrow(() -> new IllegalArgumentException("No value found for key: " + key));
    }

    static GitTag asGitTag(JSONObject jsonObject, String key) {
        return asOpt(jsonObject, key, String.class)
                .map(GitTag::of)
                .orElseThrow(() -> new IllegalArgumentException("No value found for key: " + key));
    }

    static Instant asInstant(JSONObject jsonObject, String key) {
        return asOpt(jsonObject, key, String.class)
                .map(Instant::parse)
                .orElseThrow(() -> new IllegalArgumentException("No value found for key: " + key));
    }

    private static <T> Optional<T> asOpt(JSONObject jsonObject, String key, Class<T> clazz) {
        Object value = jsonObject.get(key);

        if (value == null) {
            return Optional.empty();
        }

        if (!clazz.isInstance(value)) {
            throw new IllegalArgumentException("Value for key " + key + " is not of type " + clazz.getSimpleName());
        }

        return Optional.of(clazz.cast(value));
    }
}