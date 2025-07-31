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

    private final TypiCodeClient client = new TypiCodeClient();

    @Test
    public void deletePostTest() {
        log.info("Get random post id.");
        int randomPostId = client.getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAsStream(Post[].class)
                .findAny().orElseThrow(() -> new IllegalArgumentException("No posts found"))
                .getId();

        log.info("Verify post exist by id: {}", randomPostId);
        client.getPostById(randomPostId)
                .checkStatusCode(SC_OK);

        log.info("Delete post by id: {}", randomPostId);
        client.deletePostById(randomPostId)
                .checkStatusCode(SC_OK);

        log.info("Verify post does not exist by id: {}", randomPostId);
        client.getPostById(randomPostId)
                .checkStatusCode(SC_NOT_FOUND);
    }

    @Test
    public void deleteNonExistentPostTest() {
        int nonExistentId = 9999;
        client.deletePostById(nonExistentId)
                .checkStatusCode(SC_NOT_FOUND);
    }

    @Test
    public void deletePostTwiceTest() {
        int randomPostId = client.getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAsStream(Post[].class)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No posts found"))
                .getId();

        client.deletePostById(randomPostId)
                .checkStatusCode(SC_OK);

        client.deletePostById(randomPostId)
                .checkStatusCode(SC_NOT_FOUND);
    }

}
