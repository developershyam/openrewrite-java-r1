
# Description
This is project to create sample recipes. And test using rewrite test.
Also publish to maven repo(in this case pushed to maven local) so that it can e used in other project as dependencies.

```bash
# Build recipe it will build and test
gradle build
# Publish recipe to local maven repo
gradle publishToMavenLocal
```

# Imperative Recipes
3 recipe build using Recipe class

# Declaritive Recipes
2 YAMLL composite recipe in yml file

# Enable print Tree using debug
Use System env variable 
```bash
# Windows
set MOD_ENABLE_DEBUG=true

#Linux
export MOD_ENABLE_DEBUG=true
```