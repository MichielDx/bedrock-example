package de.holomekc;

import static io.quarkus.vertx.VertxContextSupport.subscribeAndAwait;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;

@QuarkusTest
class BedrockTest {

    @Inject
    BedrockRuntimeAsyncClient client;

    @Test
    void should_chat_with_ai() throws Throwable {
        // given

        // when
        subscribeAndAwait(() -> Uni.createFrom()
                .completionStage(client.invokeModel(b -> b.modelId("amazon.titan-text-express-v1"))));

        // then
    }
}
