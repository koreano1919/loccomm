package it.nero.loccomm.cart;

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
            .importPackages("it.nero.loccomm.cart");

        noClasses()
            .that()
                .resideInAnyPackage("it.nero.loccomm.cart.service..")
            .or()
                .resideInAnyPackage("it.nero.loccomm.cart.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.nero.loccomm.cart.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
