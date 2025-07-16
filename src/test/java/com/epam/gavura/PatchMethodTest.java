package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Post;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class PatchMethodTest {

    @Test
    public void patchPostTest() {
        int randomPostId = new TypiCodeClient().getAllPosts()
            .checkStatusCode(SC_OK)
            .getBodyAs(Post[].class)[0]
            .getId();

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");

        Post patchedPost = new TypiCodeClient().patchPostById(randomPostId, updatedPost)
            .checkStatusCode(SC_OK)
            .getBodyAs(Post.class);

        assertThat(patchedPost.getTitle())
            .as("Title should be updated")
            .isEqualTo(updatedPost.getTitle());
    }
}
