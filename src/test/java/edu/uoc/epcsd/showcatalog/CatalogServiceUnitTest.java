package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogService;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceUnitTest {

    @Mock
    private static ShowRepository showRepository;

    @Mock
    private static CategoryRepository categoryRepository;

    @Mock
    private static KafkaTemplate<String, Show> kafkaTemplate;


    private static Category category;

    private static List<Show> shows = new ArrayList<>();

    private static Long showIdThatExists = 1L;

    private static Long showIdThatNotExists = 1L;

    private static CatalogService sut;

    @BeforeAll
    static void setUp() {

        category = Category.builder()
                .id(1L)
                .name("Category")
                .build();
        Show show1 = Show.builder()
                .id(1L)
                .name("Show 1")
                .category(category)
                .build();
        Show show2 = Show.builder()
                .id(2L)
                .name("Show 2")
                .category(category)
                .build();
        shows = Arrays.asList(show1,show2);


    }

    @BeforeEach
    void dependenciesInjection() {
        sut = new CatalogServiceImpl(showRepository, categoryRepository, kafkaTemplate);
    }

    @Test
    void testFindShowThatExists() {

        // Given
        when(showRepository.findShowById(showIdThatExists)).thenReturn(shows.stream().findFirst().filter(i -> i.getId().equals(showIdThatExists)));
        // When
        Optional<Show> resultShow = sut.findShowById(showIdThatExists);
        // Then
        assertAll("Check that is the expected show",
                () -> assertTrue(resultShow.isPresent()),
                () -> assertTrue(shows.contains(resultShow.get())),
                () -> assertEquals(resultShow.get().getId(), showIdThatExists));

        verify(showRepository).findShowById(showIdThatExists);

    }

    @Test
    void testFindShowThatNotExists() {


        // Given
        when(showRepository.findShowById(showIdThatNotExists)).thenReturn(Optional.empty());
        // When
        Optional<Show> resultShow = sut.findShowById(showIdThatNotExists);
        // Then
        assertTrue(resultShow.isEmpty());


    }


}
