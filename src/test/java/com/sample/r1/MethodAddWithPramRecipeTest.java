package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class MethodAddWithPramRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MethodAddWithPramRecipe("com.sample.User", "myMethod"));
    }

    @Test
    void addsHelloToFooBar() {
        
        rewriteRun(
            java(
                """
                    package com.sample;

                    class User {
                    }
                """,
                """
                    package com.sample;

                    class User {
                        public String myMethod(String name) {
                            return "Hello from com.sample.User!";
                        }
                    }
                """
            )
        );
    }


}