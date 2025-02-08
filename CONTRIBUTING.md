# Contributing  

## Fork the Project and Create Your Own Branch  

1. **Fork the Project**  
   Click the Fork button on the original project page to copy the project to your GitHub account.  
2. **Clone to Local**  
   Clone the forked project to your local development environment.  
3. **Create a New Branch**  
   Create and switch to a new branch locally for modifications.  
4. **Make Changes**  
   Implement the necessary code changes in the new branch.  
5. **Commit Changes**  
   After completing the modifications, commit your changes with a concise commit message.  
6. **Push to Remote**  
   Push the changes to your forked remote repository.  
7. **Create a Pull Request (PR)**  
   Open a Pull Request on GitHub, selecting the source branch and target branch.  

## Code Style Guide  

This project follows the [Google Java Style Guide](http://hawstein.com/2014/01/20/google-java-style/).  

Additionally, adhere to the following conventions:  

1. **Use `Path` or `URL` instead of `File`** whenever handling file paths.  
2. **Use `@javax.annotation.Nullable`** for method parameters that may be `null`.  
3. **Use `Optional` as the return type** for methods that may return `null`.  
4. **Use `@javax.annotation.Nonnull`** for parameters and return values that must not be `null`, and validate parameters using `Validate.notNull()`.  
5. **Annotate thread-safe classes with `@ThreadSafe`** if they are confirmed or required to be thread-safe.  
6. **Use JOML interfaces (`Vector3fc`, `Matrix4fc`, etc.)** as method parameter types unless modifications are required.  
7. **Use singular names for folders, directories, and package names.**  
8. **Be cautious when using the Stream API** in **sequential** iteration scenarios.  

## Commit Message Guidelines  

### Commit Message Format  

Each commit message consists of a **header**, **details**, and an **ending**. The **header** follows a specific format and includes a **type**, **scope**, and **subject**:  
```
<type>(<scope>): <subject>
<empty line>
<details>
<empty line>
<ending>
```  
The **header** is mandatory, while the **scope** is optional.  

Each line in the commit message must not exceed 100 characters to ensure readability on GitHub and various Git interfaces.  

The **ending** should include an [issue closing reference](https://help.github.com/en/articles/closing-issues-using-keywords) if applicable.  

Examples:  
```
docs(README): update README
```  
```
feat(Mod): support to load mod in development environment
```  

### Types  

The commit type must be one of the following:  

- **build**: Changes related to the build process or auxiliary tools  
- **docs**: Documentation updates  
- **feat**: Addition of a new feature  
- **fix**: Bug fixes  
- **perf**: Performance improvements  
- **refactor**: Code refactoring  
- **style**: Changes that do not affect code functionality (e.g., formatting, spaces)  
- **test**: Adding tests  

### Subject  

The subject provides a concise description of the change:  
- Use the imperative mood and present tense: "change" instead of "changed" or "changes".  
- Do not capitalize the first character.  
- Do not end with a period (.)  

### Details  

Similar to the subject, use the imperative mood and present tense.  
The details should explain the reason for the change and how it differs from the previous version.  

### Ending  

The ending may include an [issue closing reference](https://help.github.com/en/articles/closing-issues-using-keywords) or details on breaking changes.  

### Revert  

If reverting a previous commit, the header should begin with `revert:`, followed by the subject of the original commit.  
The details should include `This reverts commit <hash>.`, where `<hash>` is the SHA of the commit being reverted.  

## Pull Request (PR) Guidelines  

_Similar to Commit Guidelines, refer to Commit section._