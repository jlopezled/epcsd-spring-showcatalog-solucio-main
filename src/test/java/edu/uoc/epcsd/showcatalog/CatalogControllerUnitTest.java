package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.application.rest.CatalogRESTController;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogService;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.catalog.Catalog;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatalogControllerUnitTest {

    @Mock
    private static CatalogService catalogService;

    private static CatalogRESTController sut;

    private static List<Category> list;

    @BeforeAll
    static void setUpI() {
        Category category1 = Category.builder()
                .id(1L)
                .name("Category 1")
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .name("Category2")
                .build();
        list = Arrays.asList(category1, category2);

    }

    @BeforeEach
    void dependenciesInjection() {
        sut = new CatalogRESTController(catalogService);
    }

    @Test
    void testFindAllCategories() {

        when(catalogService.findAllCategories()).thenReturn(list);

        List<Category> checkList = sut.findCategories();

        assertAll("Compares two lists of categories",
                () -> assertEquals(checkList.size(), list.size()),
                () -> assertTrue(checkList.containsAll(list)),
                () -> assertTrue(list.containsAll(checkList)));

    }


}


