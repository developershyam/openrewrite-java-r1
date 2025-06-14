package com.sample.r1;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.NonNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Collectors;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.TreeVisitor;

@Value
@EqualsAndHashCode(callSuper = false)
public class MethodDeleteRecipe extends Recipe {

    @NonNull
    String fullyQualifiedClassName;

    @NonNull
    String methodName;

    public MethodDeleteRecipe() {
        fullyQualifiedClassName = "com.sample.User";
        methodName = "hello";
    } 
    
    @JsonCreator
    public MethodDeleteRecipe(@NonNull @JsonProperty("fullyQualifiedClassName") String fullyQualifiedClassName,
                              @NonNull @JsonProperty("methodName") String methodName) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.methodName = methodName;
    }

    @Override
    public String getDisplayName() {
        return "Delete method '" + methodName + "' from " + fullyQualifiedClassName;
    }

    @Override
    public String getDescription() {
        return "Deletes the method named '" + methodName + "' from the specified class.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl, ExecutionContext ctx) {
                J.ClassDeclaration cd = super.visitClassDeclaration(classDecl, ctx);
                if (cd.getType() != null && fullyQualifiedClassName.equals(cd.getType().getFullyQualifiedName())) {
                    cd = cd.withBody(
                        cd.getBody().withStatements(
                            cd.getBody().getStatements().stream()
                                .filter(stmt -> !(stmt instanceof J.MethodDeclaration &&
                                    methodName.equals(((J.MethodDeclaration) stmt).getSimpleName())))
                                .collect(Collectors.toList())
                        )
                    );
                }
                return cd;
            }
        };
    }
}