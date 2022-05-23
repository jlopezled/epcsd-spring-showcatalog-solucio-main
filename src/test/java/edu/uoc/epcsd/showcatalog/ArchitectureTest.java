package edu.uoc.epcsd.showcatalog;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = {"edu.uoc.epcsd.showcatalog.application","edu.uoc.epcsd.showcatalog.domain","edu.uoc.epcsd.showcatalog.infrastructure"})
public class ArchitectureTest {

    @ArchTest
    static final ArchRule onion_architecture_is_respected = onionArchitecture()
            .domainModels("..domain..")
            .domainServices("..domain.service..")
            .applicationServices("..application..")
            .adapter("persistence", "..infrastructure.repository..")
            .adapter("kakfa", "..infrastructure.kafka..")
            .ignoreDependency(CatalogServiceUnitTest.class, ShowCatalogApplicationTests.class);

    @ArchTest
    static ArchRule services_name_should_be_ending_with_Service =
            classes()
                    .that().resideInAPackage("..domain.service..")
                    .and().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("ServiceImpl");

}
