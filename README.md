# Enterprise Quality Engineering Framework

## Project Overview

This project demonstrates a complete enterprise-level quality engineering solution. It includes UI automation, API automation, performance testing, CI/CD integration, and structured documentation. The goal is to ensure functional correctness, reliability, security, and performance stability of the application under test.

---

## Folder Structure

- `src/main/java` – Page Object classes and utility components
- `src/test/java` – Test cases for UI and API
- `test-output` – Test reports and screenshots
- `docs` – All required documentation for submission
- `.github/workflows` – GitHub Actions CI/CD pipeline configuration

---

## How to Run Tests

### Run All Tests
```bash
mvn clean test

Run Only UI Tests
mvn test -Dgroups=ui

Run Only API Tests
mvn test -Dgroups=api

Reports
1. After test execution, reports are generated in the test-output folder.
2. Open ExtentReport.html in a browser to view test results
3. Screenshots and logs are available inside test-output/screenshots

Documentation (Located in docs folder)
1.Test Strategy Document – Explains testing approach, scope, and planning
2. Automation Architecture – Describes framework design and tools used
3. Test Coverage Matrix – Excel file mapping features to test types
4. Sample Automated Test Reports – Extent or Allure reports
5. Performance Test Summary – Load, stress, and spike test results
6. Defect RCA Samples – Root cause analysis of sample defects

CI/CD Integration
- GitHub Actions pipeline runs tests on every push and pull request
- Reports are archived as downloadable artifacts
- Java and Maven are auto-configured in the pipeline

Contact
For any questions or walkthroughs, please reach out to the QA team.