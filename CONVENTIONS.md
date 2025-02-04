<div id="top"></div>

<br />
<div align="center">
  <h1 align="center">Guidelines and Code conventions</h1>
  <p align="center">
    Style guidelines and conventions to follow in the project
  </p>
</div>

# Commits Naming Convention
All commits should follow the following convention:

`CATEGORY_OF_COMMIT`: `MESSAGE`

The category of commits available are:
* `feat`: For when you are adding a new feature/code
* `fix`: For when you are fixing a bug
* `refactor`: For when you are refactoring something
* `chore`: For when you are doing anything else (documentation, formatting, etc.)

### Examples
* `feat: added user form`
* `fix: fixed max characters allowed for e-mails on user form`
* `refactor: refactored user form to be more performant`
* `chore: added documentation for user form`

# Branches Naming Convention
All branches should follow the following convention:

`CATEGORY_OF_BRANCH`/`description`

The category of branches available are:
* `feature`: For when you are adding, refactoring or removing a feature
* `bugfix`: For when you are fixing a bug
* `hotfix`: For when you are changing code with a temporary solution and/or without following the usual process (usually because of an emergency)
* `test`: For when you are experimenting something outside of an issue/ticket

### Examples
* `feature/create-user-form`
* `bugfix/fix-email-on-user-form`
* `hotfix/fix-user-form-submitting-button`
* `test/changing-restful-api-to-graphql`

# PR Naming and Descriptions
All PR's names should follow the following convention:

`CATEGORY_OF_BRANCH`: `Description`

The category of PRs available are the same as branches. Please fill the pull request template before opening the PR.

### Examples
* `FEATURE: User Form Implementation`
* `BUGFIX: Fix Email Field on User Form`
* `HOTFIX: Fix User Form Submitting Button`
* `TEST: Changing RESTFUL APIs to GraphQL`
