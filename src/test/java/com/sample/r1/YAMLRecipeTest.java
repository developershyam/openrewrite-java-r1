package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

class YAMLRecipeTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        try {
            InputStream in = new FileInputStream(new File("src/main/resources/META-INF/rewrite/rewrite.yml"));
            spec.recipe(in, "com.sample.MyYAMLRecipe");
            
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

                    import java.util.Date;

                    public abstract class Customer {
                        private Date dateOfBirth;
                        private String firstName;
                        private String lastName;

                        public abstract void setCustomerInfo(String lastName);
                    }
                """,
                """
                    package com.sample;

                    import java.util.Date;

                    public abstract class Customer {
                        private Date dateOfBirth;
                        private String firstName;
                        private String lastName;

                        public void setCustomerInfo(Date dateOfBirth, String firstName, String lastName) {
                            this.dateOfBirth = dateOfBirth;
                            this.firstName = firstName;
                            this.lastName = lastName;
                        }
                        
                        public String hello() {
                            return "Hello from com.sample.Customer!";
                        }

                        public String myTestMethod(String name) {
                            return "Hello from com.sample.Customer!";
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