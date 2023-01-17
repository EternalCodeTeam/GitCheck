package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitTag;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.util.NoSuchElementException;

final class JSONUtil {

    private JSONUtil() {
    }

    static String asString(JSONObject jsonObject, String key) {
        return as(jsonObject, key, String.class);
    }

    static GitTag asGitTag(JSONObject jsonObject, String key) {
        String rawTag = as(jsonObject, key, String.class);

        return GitTag.of(rawTag);
    }

    static Instant asInstant(JSONObject jsonObject, String key) {
        String rawDateTime = as(jsonObject, key, String.class);

        return Instant.parse(rawDateTime);
    }

    private static <T> T as(JSONObject jsonObject, String key, Class<T> clazz) {
        Object obj = jsonObject.get(key);

        if (obj == null) {
            throw new NoSuchElementException("No value for key " + key);
        }

        if (!clazz.isInstance(obj)) {
            throw new IllegalArgumentException("Value for key " + key + " is not of type " + clazz.getSimpleName());
        }

        return clazz.cast(obj);
    }

}
