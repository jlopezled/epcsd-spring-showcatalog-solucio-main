package edu.uoc.epcsd.showcatalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uoc.epcsd.showcatalog.application.rest.CatalogRESTController;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogService;
import edu.uoc.epcsd.showcatalog.domain.service.CatalogServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CatalogRESTController.class)
public class CatalogControllerUnitTest {


    @MockBean
    private static CatalogService catalogService;

    @Autowired
    private MockMvc mvc;


    private static CatalogRESTController catalogRESTController;

    private static List<Category> list;

    @Before
    public void setUp() {

        catalogRESTController = new CatalogRESTController(catalogService);

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

    @Test
    public void testFindAllCategories() throws Exception {

        given(catalogService.findAllCategories()).willReturn(list);

        MvcResult result = this.mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<Category> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Category>>() {
        });

        assertAll("Check that the result is the same as expected",
                () -> assertTrue(actual.containsAll(list)),
                () -> assertTrue(list.containsAll(actual)));


        verify(catalogService, times(1)).findAllCategories();


    }


}


