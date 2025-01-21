# README FILE
## Directory Structure:

/gitea-automation-tests
├── /src
│   ├── /main
│   │   └── /java
│   └── /test
│       └── /java
│           └── /UITests
│               ├── LoginGitTest.java
│               └── NewProjectPageTest.java
├── /target
│   └── (compiled test results)
├── .gitignore
└── README.md
# Gitea Automation Test Suite

This repository contains an automated testing suite for testing the Gitea application. The tests are written in Java using Selenium and JUnit5 to ensure that the Gitea web interface and its functionalities are tested for quality and performance.

## Testing Strategy & Objectives

The objective of this automation test suite is to ensure that the core functionalities of the Gitea application are thoroughly tested. 
The suite includes smoke tests and regression tests that focus on critical paths such as user login, 
project creation, and error handling scenarios. These tests will ensure that both valid and invalid input scenarios are handled correctly.
### Test Types:
**Smoke Tests**: These are high-level tests to ensure that the basic functionality of the app works as expected, such as logging in and creating projects.

**Regression Tests**: These ensure that new features or updates to the application do not break existing functionality.
### Test Cases:

- **Login Functionality**: Tests the login behavior for valid and invalid credentials.
- **Project Creation**: Ensures that new projects can be created, with validations for mandatory fields (e.g., Title).
- **Error Handling**: Verifies that the system behaves correctly when required fields are missing or incorrect inputs are provided.

### Test Plan:

The tests are designed to:
- Verify the basic functionality of critical paths.
- Ensure that users can log in and create projects with valid inputs.
- Handle cases of empty or invalid input gracefully.

The following test cases are part of the suite:
- **testInvalidLogin**
- **testValidLogin**
- **testEmptyUsername**
- **testEmptyPassword**
- **testEmptyUsernameAndPassword**
- **testCreateProject**
- **testEmptyFields**
- **testTitleOnly**
- **testCancelButton**
- **testBoldStyleInDescription**

### Test Suite Completion Criteria:
- **Coverage**: The suite must cover at least the critical paths of the application such as login, project creation, and input validations.
- **Pass Criteria**: All tests related to valid and invalid inputs should pass as expected, and the app should handle error cases gracefully.

## Setup Testing Environment

### Prerequisites:
- **Java 11+**: Make sure that you have Java 11 or higher installed.
- **Maven**: The tests are managed with Maven, so ensure that Maven is installed and configured.
- **ChromeDriver / FirefoxDriver**: Ensure you have the corresponding WebDriver for your browser in your system's PATH.

### Install Dependencies:

Clone this repository and install the required dependencies.

```bash
git clone https://github.com/yourusername/giteaProject.git
cd gitea-automation-tests
mvn clean install
```
### WebDriver Setup:

To run the tests on your local machine, you'll need the correct browser WebDriver. You can download WebDriver for your browser from the following links:


- [ChromeDriver](https://developer.chrome.com/docs/chromedriver/downloads?hl=he#chromedriver_1140573590)
- [GeckoDriver (Firefox)]()

Make sure the driver is installed and accessible in your system's PATH or specify its location in your test code.

## Running the Tests Locally
Running All Tests:

To run the complete test suite, use the following command:

```bash
mvn test
```
```bash
mvn test -Dtest=LoginGitTest#testInvalidLogin
```
```bash
mvn test -Dtest=LoginGitTest#testInvalidLogin,LoginGitTest#testTitleOnly

```
### Viewing Test Results:

After executing the tests, Maven will output results in the console, and detailed reports can be found in the target directory in the form of surefire-reports.
# Running Tests Remotely (Using GitHub Actions with Ngrok)
In addition to running tests locally or on a CI platform, you can use ngrok to create a temporary public URL for your local server, enabling you to run tests remotely against your local environment.

## Setting up Ngrok:

- **Install Ngrok** : If you don't have ngrok installed yet, you can download it from ngrok's website. Once installed, you can run it from the command line.

- **Start Your Local Server**: Ensure your local application (in this case, Gitea) is running on a port, for example, port 3000.

- **Expose Your Local Server**: Run ngrok to expose your local server to the internet. In this example, we will expose port 3000:
``` bash
ngrok http 3000
```
After running the above command, ngrok will provide a public URL that tunnels to your local Gitea instance. It will look something like this:
``` bash
Forwarding   http://abcd1234.ngrok.io -> http://localhost:3000
```
You can use the http://abcd1234.ngrok.io URL to access your local Gitea instance remotely.


## Running Tests Remotely (Using GitHub Actions)
GitHub Actions provides a Continuous Integration (CI) workflow to automatically run the tests on every commit or pull request. You can trigger the tests remotely in GitHub Actions.

### Setting up GitHub Actions:

In your repository, navigate to the .github/workflows directory.
Create a new YAML file for the test workflow, for example, test.yml:
## UI Tests

``` yaml
name: UI testing

on:
  workflow_dispatch:
    inputs:
      grid_url:
        required: true
        type: string
        description: Grid to run tests on (local by default)
        default: http://localhost:4444

      chrome_131_0:
        description: 'Chrome (131.0)'
        required: false
        type: boolean

      firefox_133_0:
        description: 'Firefox (133.0)'
        required: false
        type: boolean

jobs:
  TestChrome_131_0:
    name: Test Chrome 131.0
    runs-on: ubuntu-latest
    if: ${{ github.event.inputs.chrome_131_0 == 'true' }}
    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up JDK 23
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '23'

      - name: Launch standalone grid - Chrome
        if: ${{ contains(github.event.inputs.grid_url, 'localhost') }}
        run: |
          docker run --name grid_container -d -p 4444:4444 --shm-size=2gb selenium/standalone-chrome:131.0-chromedriver-131.0-grid-4.27.0-20250101

      - name: Run Tests
        run: |
          export BROWSER=chrome
          export GRID_URL=${{ github.event.inputs.grid_url }}

          mvn test -Dtest=**/UITests/*

          # Tear down grid if exists
          docker stop grid_container || true

  TestFirefox_133_0:
    name: Test Firefox 133.0
    runs-on: ubuntu-latest
    if: ${{ github.event.inputs.firefox_133_0 == 'true' }}
    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up JDK 23
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '23'

      - name: Launch standalone grid - Firefox
        if: ${{ contains(github.event.inputs.grid_url, 'localhost') }}
        run: |
          docker run --name grid_container -d -p 4444:4444 --shm-size=2gb selenium/standalone-firefox:133.0-geckodriver-0.35-grid-4.27.0-20250101

      - name: Run Tests
        run: |
          export BROWSER=firefox
          export GRID_URL=${{ github.event.inputs.grid_url }}

          mvn test -Dtest=**/UITests/*

          # Tear down grid if exists
          docker stop grid_container || true


```
## API Tests 
``` yaml 
name: API testing

on:
  workflow_dispatch:

jobs:
  api-tests:
    name: Run API Tests
    runs-on: ubuntu-latest
    steps:
      # Step 1: Checkout code
      - name: Checkout code
        uses: actions/checkout@v3

      # Step 2: Set up JDK 19
      - name: Set up JDK 23
        uses: actions/setup-java@v3
        with:
          distribution: 'temurin'
          java-version: '23'


      # Step 4: Run API Tests
      - name: Run API Tests

        run: |
          mvn -q clean test -Dtest=**/APITests/*

```

### This configuration ensures the following:

-The tests will run whenever there is a push to the main branch or a pull request is made against it.

-The workflow uses the latest Ubuntu runner and sets up Java 11 and Maven.

-Dependencies are installed using mvn install.

-The tests are executed using mvn test.

-If the tests fail, test results are uploaded for easy inspection.

### Running Tests Remotely:

1-Commit and push your changes to the repository.

2-Navigate to the Actions tab in GitHub.

3-You will see a workflow run triggered by your push or pull request.

4-The test results can be viewed directly in the workflow logs or the uploaded artifacts.

### Test Suite Completion Criteria

#### The automated test suite should meet the following completion criteria:

1- Coverage: The suite must cover at least the critical paths of the application such as login and project creation.\

2-Pass Criteria: All tests related to valid and invalid inputs should pass as expected.


### Troubleshooting
If you encounter issues with the tests:

1-Ensure your browser drivers are up to date.

2-Make sure the Gitea application is accessible and running.

