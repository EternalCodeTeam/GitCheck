package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitTag;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONUtilTest {

    @Test
    @SuppressWarnings("unchecked")
    void testAsString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("text", "test");
        jsonObject.put("time", "2020-01-01T00:00:00Z");
        jsonObject.put("tag", "v1.0.0");

        assertEquals("test", JSONUtil.asString(jsonObject, "text"));
        assertEquals(Instant.parse("2020-01-01T00:00:00Z"), JSONUtil.asInstant(jsonObject, "time"));
        assertEquals(GitTag.of("v1.0.0"), JSONUtil.asGitTag(jsonObject, "tag"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAsStringWithInvalidKey() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "test");

        assertThrows(IllegalArgumentException.class, () -> JSONUtil.asString(jsonObject, "time"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAsStringWithInvalidType() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", 0);

        assertThrows(IllegalArgumentException.class, () -> JSONUtil.asString(jsonObject, "text"));
    }


}