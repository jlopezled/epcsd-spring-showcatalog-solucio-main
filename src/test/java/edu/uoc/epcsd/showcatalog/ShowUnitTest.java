package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ShowUnitTest {

    private static Show show;

    @BeforeAll
    static void setUpShow() {
        show = Show.builder()
                .name("Show de prueba")
                .description("Show de prueba")
                .build();
    }

    @Test
    void testShowIsCancelled() {
        //Given
        assertEquals(show.getStatus(),Status.CREATED);
        //When
        show.cancel();
        //Then
        assertEquals(show.getStatus(),Status.CANCELLED);
    }








}
