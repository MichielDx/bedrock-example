package de.holomekc;

import static io.quarkus.vertx.VertxContextSupport.subscribeAndAwait;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;

@QuarkusTest
class BedrockTest {

    @Inject
    BedrockRuntimeAsyncClient client;

    @Test
    void should_chat_with_ai() throws Throwable {
        // given
        var nativeRequestTemplate = "{ \"inputText\": \"{{prompt}}\" }";

        var prompt = "Describe the purpose of a 'hello world' program in one line.";

        // Embed the prompt in the model's native request payload.
        String nativeRequest = nativeRequestTemplate.replace("{{prompt}}", prompt);

        // when
        final var response = subscribeAndAwait(() -> Uni.createFrom().completionStage(client.invokeModel(
                b -> b.modelId("amazon.titan-text-express-v1").body(SdkBytes.fromUtf8String(nativeRequest)))));

        // then
        System.out.printf(response.body().asUtf8String());
        assertThat(response.body().asUtf8String()).isNotBlank();
    }
}
