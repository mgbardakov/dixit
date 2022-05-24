package site.hornsandhooves.dixit.service.lobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.hornsandhooves.dixit.TestDataFactory;

import static org.junit.jupiter.api.Assertions.*;

class SimpDestinationResolverTest {

    private SimpDestinationResolver simpDestinationResolver;

    @BeforeEach
    void setUp() {
        this.simpDestinationResolver = new SimpDestinationResolver();
    }

    @Test
    public void whenGetDestinationFromSubscribeMessageAndItIsExpectedString() {
        //when
        var subMessage = TestDataFactory.getSubscribeMessage();
        var expectedDestination = "Expected destination";
        //then
        var actualDestination = simpDestinationResolver.getDestination(subMessage);
        //assertions
        assertEquals(expectedDestination, actualDestination);
    }

    @Test
    public void whenGetDestinationFromUnsubString() {
        //when
        var unsubMessage = TestDataFactory.getUnsubscribeMessage();
        var expectedDestination = "Expected destination";
        simpDestinationResolver.addDestination(TestDataFactory.getSubscribeMessage());
        //then
        var actualDestination = simpDestinationResolver.getDestination(unsubMessage);
        //assertions
        assertEquals(expectedDestination, actualDestination);
    }
}
