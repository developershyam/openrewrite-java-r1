
# Description
This is project to create sample recipes. And test using rewrite test.
Also publish to maven repo(in this case pushed to maven local) so that it can e used in other project as dependencies.

```bash
# Build recipe
gradle build
# Test recipe using unit test
gradle test
# Publish recipe to local maven repo
gradle publishToMavenLocal
```

# List of recipt got created

- 3 Programatically create using java files
- 2 created using yml files



dir /s /b rewrite.yml
jar tf build/libs/openrewrite-java-r1-1.0.0.jar | findstr rewrite.yml