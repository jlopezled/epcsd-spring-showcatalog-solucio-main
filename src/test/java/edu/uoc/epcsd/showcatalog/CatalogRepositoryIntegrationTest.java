package edu.uoc.epcsd.showcatalog;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.CategoryEntity;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.CategoryRepositoryImpl;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CatalogRepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SpringDataCategoryRepository springDataCategoryRepository;

    private CategoryRepository categoryRepository;

    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {

        categoryRepository = new CategoryRepositoryImpl(springDataCategoryRepository);

        CategoryEntity category = CategoryEntity.fromDomain(new Category(null,"Category 1","Description"));

        CategoryEntity persisted =entityManager.persist(category);
        entityManager.flush();

        Optional<Category> found = categoryRepository.findCategoryById(persisted.getId());
        System.out.println(found);
        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(category.getId());
    }




}
