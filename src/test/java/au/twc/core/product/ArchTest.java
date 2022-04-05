package au.twc.core.product;

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
            .importPackages("au.twc.core.product");

        noClasses()
            .that()
                .resideInAnyPackage("au.twc.core.product.service..")
            .or()
                .resideInAnyPackage("au.twc.core.product.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..au.twc.core.product.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
