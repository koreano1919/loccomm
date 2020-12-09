package it.nero.loccomm.order;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("it.nero.loccomm.order");

        noClasses()
            .that()
                .resideInAnyPackage("it.nero.loccomm.order.service..")
            .or()
                .resideInAnyPackage("it.nero.loccomm.order.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.nero.loccomm.order.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
