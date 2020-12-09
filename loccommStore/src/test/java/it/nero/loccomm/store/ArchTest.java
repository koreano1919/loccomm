package it.nero.loccomm.store;

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
            .importPackages("it.nero.loccomm.store");

        noClasses()
            .that()
                .resideInAnyPackage("it.nero.loccomm.store.service..")
            .or()
                .resideInAnyPackage("it.nero.loccomm.store.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.nero.loccomm.store.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
