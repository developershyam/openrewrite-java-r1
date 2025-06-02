package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

class YAMLMethodRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        try {
            InputStream in = new FileInputStream(new File("src/main/resources/META-INF/rewrite/rewrite.yml"));
            spec.recipe(in, "com.sample.r1.MyYAMLMethodRecipe");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addsHelloAndMyTestMethodToFooBar() {
        
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
                        
                        public String myTestMethod(String name) {
                            return "Hello from com.sample.User!";
                        }
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