package com.sample.r1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.jspecify.annotations.NonNull;
import org.openrewrite.*;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;

// Making your recipe immutable helps make them idempotent and eliminates a variety of possible bugs.
// Configuring your recipe in this way also guarantees that basic validation of parameters will be done for you by rewrite.
// Also note: All recipes must be serializable. This is verified by RewriteTest.rewriteRun() in your tests.
@Value
@EqualsAndHashCode(callSuper = false)
public class MethodAddWithPramRecipe extends Recipe {
    
    @NonNull
    String fullyQualifiedClassName;

    @NonNull
    String methodName;

    public MethodAddWithPramRecipe() {
        fullyQualifiedClassName = "com.sample.User";
        methodName = "myMethod";
    }    

    // All recipes must be serializable. This is verified by RewriteTest.rewriteRun() in your tests.
    @JsonCreator
    public MethodAddWithPramRecipe(@NonNull @JsonProperty("fullyQualifiedClassName") String fullyQualifiedClassName,
                                @NonNull @JsonProperty("methodName") String methodName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.methodName = methodName;
    }

    @Override
    public String getDisplayName() {
        return "Display  "+ methodName;
    }

    @Override
    public String getDescription() {
        return "Description "+methodName+" method to the specified class.";
    }


     @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        // getVisitor() should always return a new instance of the visitor to avoid any state leaking between cycles
        return new HelloMethodVisitor();
    }

    public class HelloMethodVisitor extends JavaIsoVisitor<ExecutionContext> {

        private final JavaTemplate helloTemplate =
                JavaTemplate.builder( "public String "+methodName+"(String name) { return \"Hello from #{}!\"; }")
                        .build();
        
        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl, ExecutionContext executionContext) {
            
            // Don't make changes to classes that don't match the fully qualified name
            if (classDecl.getType() == null || !classDecl.getType().getFullyQualifiedName().equals(fullyQualifiedClassName)) {
                return classDecl;
            }

            // Check if the class already has a method named "hello"
            boolean helloMethodExists = classDecl.getBody().getStatements().stream()
                    .filter(statement -> statement instanceof J.MethodDeclaration)
                    .map(J.MethodDeclaration.class::cast)
                    .anyMatch(methodDeclaration -> methodDeclaration.getName().getSimpleName().equals(methodName));
                    

            // If the class already has a `hello()` method, don't make any changes to it.
            if (helloMethodExists) {
                return classDecl;
            }
            // Interpolate the fullyQualifiedClassName into the template and use the resulting LST to update the class body
            classDecl = classDecl.withBody( helloTemplate.apply(new Cursor(getCursor(), classDecl.getBody()),
            classDecl.getBody().getCoordinates().lastStatement(),
            fullyQualifiedClassName ));

            return classDecl;
        }
    }
}