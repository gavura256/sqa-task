package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class DeleteMethodTest {

    @Test
    public void deletePostTest() {
        log.info("Get random post id.");
        int randomPostId = Arrays.stream(new TypiCodeClient().getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAs(Post[].class))
            .findAny().orElseThrow(() -> new IllegalArgumentException("No posts found"))
            .getId();

        log.info("Verify post exist by id: {}", randomPostId);
        new TypiCodeClient().getPostById(randomPostId)
            .checkStatusCode(SC_OK);

        log.info("Delete post by id: {}", randomPostId);
        new TypiCodeClient().deletePostById(randomPostId)
            .checkStatusCode(SC_OK);

        log.info("Verify post does not exist by id: {}", randomPostId);
        new TypiCodeClient().getPostById(randomPostId)
            .checkStatusCode(SC_NOT_FOUND);
    }

    @Test
    public void deleteNonExistentPostTest() {
        int nonExistentId = 9999;
        new TypiCodeClient().deletePostById(nonExistentId)
            .checkStatusCode(SC_NOT_FOUND);
    }

    @Test
    public void deletePostTwiceTest() {
        int randomPostId = Arrays.stream(new TypiCodeClient().getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAs(Post[].class))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("No posts found"))
            .getId();

        new TypiCodeClient().deletePostById(randomPostId)
            .checkStatusCode(SC_OK);

        new TypiCodeClient().deletePostById(randomPostId)
            .checkStatusCode(SC_NOT_FOUND);
    }
}
