package com.epam.gavura;

import com.epam.gavura.client.TypiCodeClient;
import com.epam.gavura.model.Post;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class PatchMethodTest {

    private final TypiCodeClient client = new TypiCodeClient();

    @Test
    public void patchPostTest() {

        int randomPostId = client.getAllPosts()
                .checkStatusCode(SC_OK)
                .getBodyAs(Post[].class)[0]
                .getId();

        Post updatedPost = Post.builder()
                .title("Updated Title")
                .build();

        Post patchedPost = client.patchPostById(randomPostId, updatedPost)
                .checkStatusCode(SC_OK)
                .getBodyAs(Post.class);

        assertThat(patchedPost.getTitle())
                .as("Title should be updated")
                .isEqualTo(updatedPost.getTitle());
    }

}
