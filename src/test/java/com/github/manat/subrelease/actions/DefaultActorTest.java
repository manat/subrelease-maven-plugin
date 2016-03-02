package com.github.manat.subrelease.actions;

import com.github.manat.subrelease.invoker.Invoker;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActorTest extends DefaultActorTestBase {

    @Override
    DefaultActor createActor(Invoker invoker) {
        return new DefaultActor(invoker);
    }
}