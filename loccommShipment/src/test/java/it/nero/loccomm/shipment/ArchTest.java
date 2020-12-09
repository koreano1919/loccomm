package it.nero.loccomm.shipment;

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
            .importPackages("it.nero.loccomm.shipment");

        noClasses()
            .that()
                .resideInAnyPackage("it.nero.loccomm.shipment.service..")
            .or()
                .resideInAnyPackage("it.nero.loccomm.shipment.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.nero.loccomm.shipment.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
