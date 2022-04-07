package com.ezshopping.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.*;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.conditions.ArchConditions;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;

import java.io.PrintStream;


@AnalyzeClasses(packages = "com.ezshopping", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    @ArchTest
    static final ArchRule layersAccessRule = Architectures.layeredArchitecture()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Repository").definedBy("..repository..")
            .layer("Mapper").definedBy("..mapper..")
            .layer("Config").definedBy("..config..")
            .layer("Entities").definedBy("..entity..")
            .layer("Mapper").definedBy("..mapper..")
            .layer("Handler").definedBy("..handler..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service", "Mapper", "Config", "Handler")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Entities").mayOnlyBeAccessedByLayers("Repository", "Service", "Mapper", "Config")
            .whereLayer("Mapper").mayOnlyBeAccessedByLayers("Mapper", "Service", "Handler");

    @ArchTest
    static final ArchRule doNotUseFiledInjection = ArchRuleDefinition
            .noFields()
            .should(ArchConditions.beAnnotatedWith("org.springframework.beans.factory.annotation.Autowired")
            .as("Don't use filed injection"))
            .because("Directors said that :P");

    @ArchTest
    static final ArchRule doNotUseSystemOut = ArchRuleDefinition
            .noClasses()
            .should()
            .accessClassesThat()
            .belongToAnyOf(PrintStream.class)
            .as("Only logs should be used in order to log information");
}
