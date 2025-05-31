package com.sample.r1;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class ExpandCustomerInfoTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new ExpandCustomerInfo());
    }

    @Test
    void doesNotModifyUnexpectedMethods() {
        rewriteRun(
            java(
                """
                    package com.sample;

                    import java.util.Date;

                    public abstract class Customer {
                        private Date dateOfBirth;
                        private String firstName;
                        private String lastName;

                        public abstract void setOtherCustomerInfo(String lastName);

                        public void setCustomerInfo(int meow) {
                            System.out.println("Hello " + meow);
                        }
                    }
                """
            )
        );
    }

    @Test
    void expandsExpectedCustomerInfoMethod() {
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
                    }
                """
            )
        );
    }
}