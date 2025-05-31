package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class MethodAdd2RecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new MethodAdd2Recipe("com.sample.FooBar", "hello"));
    }

    @Test
    void addsHelloToFooBar() {
        
        rewriteRun(
            java(
                """
                    package com.sample;

                    class FooBar {
                    }
                """,
                """
                    package com.sample;

                    class FooBar {
                        public String hello(String name) {
                            return "Hello from com.sample.FooBar!";
                        }
                    }
                """
            )
        );
    }


}