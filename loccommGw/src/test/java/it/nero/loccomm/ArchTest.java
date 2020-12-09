package it.nero.loccomm;

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
            .importPackages("it.nero.loccomm");

        noClasses()
            .that()
                .resideInAnyPackage("it.nero.loccomm.service..")
            .or()
                .resideInAnyPackage("it.nero.loccomm.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.nero.loccomm.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
