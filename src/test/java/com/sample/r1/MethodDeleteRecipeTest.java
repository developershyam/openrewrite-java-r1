package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class MethodDeleteRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MethodDeleteRecipe("com.sample.User", "myMethod"));
    }

    @Test
    void deletesMyMethodFromUser() {
        rewriteRun(
            java(
                """
                    package com.sample;

                    class User {
                        public String myMethod(String name) {
                            return "Hello from com.sample.User!";
                        }
                    }
                """,
                """
                    package com.sample;

                    class User {
                    }
                """
            )
        );
    }

    @Test
    void doesNotDeleteOtherMethods() {
        rewriteRun(
            java(
                """
                    package com.sample;

                    class User {
                        public String anotherMethod(String name) {
                            return "Hello!";
                        }
                    }
                """
            )
        );
    }
}