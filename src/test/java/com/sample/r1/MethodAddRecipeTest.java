package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class MethodAddRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MethodAddRecipe("com.sample.User", "hello"));
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
                        public String hello() {
                            return "Hello from com.sample.User!";
                        }
                    }
                """
            )
        );
    }

    @Test
    void doesNotChangeExistingHello() {
        rewriteRun(
            java(
                """
                    package com.sample;
        
                    class User {
                        public String hello() { return ""; }
                    }
                """
            )
        );
    }

    @Test
    void doesNotChangeOtherClasses() {
        rewriteRun(
            java(
                """
                    package com.sample;
        
                    class Bash {
                    }
                """
            )
        );
    }
}