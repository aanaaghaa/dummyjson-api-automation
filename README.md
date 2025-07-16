<<<<<<< HEAD
  Dummy JSON API Automation Framework

This is an API Automation Testing Framework built using Java, TestNG, RestAssured, and ExtentReports to test the `https://dummyjson.com` API endpoints. It includes schema validation, custom logging, reporting, retry logic, and email notification of results.

 Tech Stack

| Tool/Lib         | Purpose                                  |
|------------------|------------------------------------------|
| Java 8+          | Programming Language                     |
| Maven            | Dependency Management & Build Tool       |
| RestAssured      | API Testing                              |
| TestNG           | Test Execution & Reporting               |
| ExtentReports    | Advanced Reporting                       |
| Jackson          | JSON Serialization/Deserialization       |
| RetryAnalyzer    | Retry flaky tests                        |
| JavaMail API     | Emailing the report after execution      |
 
Project Structure

src/main/java
	├──request | All the getters and setters used for API
└── src/test/java
	├── base  Base class with setup and teardown
	├── listeners  Extent report listener
	├── reporting  Custom HTML report generator & email sender
	├── requests  POJOs for 	request bodies
	├── uti	ls  Auth, RetryAnalyzer, DataProviders
	├── ProductAPITests.java
	├── ProductAPITestswithAuth.java
	└── ProductAPITestsFlaky.java
	
└── src/test/resources
	├── schemas  JSON Schemas for validation and README.md for the json files
	


 Features

-Authentication using `/auth/login`
- GET/POST/PUT/DELETE product endpoints
- Retry Analyzer for flaky endpoints
- JSON Schema Validation for response structure
- Custom Extent Reports with logs, screenshots (optional), and response breakdown
- Email the report post execution via Gmail SMTP
- Thread-safe response holder for parallel execution
- Edge case + Negative testing with appropriate validation
Sample Tests

- `GET /products`
- `GET /product/{id}`
- `POST /product/add`
- `PUT /product/{id}`
- `DELETE /product/{id}`
- `GET /product/{invalidId}` –  Negative scenario
- `GET /products` with JWT –  Auth scenario

How to Run

 1. Clone the Repository
```bash
git clone https://github.com/aanaaghaa/dummyjson-api-test.git
2. Open in Eclipse/IntelliJ
Ensure Maven is enabled and dependencies are downloaded.

3. Run with TestNG
Right-click on testng.xml or run directly from your test classes.

Report & Logs
After test execution:

Report is generated at:
./test-output/ExtentReport.html

Email is triggered automatically at the end of the suite (configured in BaseTest.java)

Note: SMTP credentials are stored in EmailReportSender.java. Use App Passwords if 2FA is enabled on Gmail.

Sample Auth Credentials
Username	Password
emilys	emilyspass
@DataProvider(name="validProductIds")
public Object[][] data() {
  return new Object[][] { {1}, {3}, {10} };
}
JSON Schemas
Located under schemas/ directory:

product-schema.json

all-products-schema.json

Used for validating response bodies with matchesJsonSchemaInClasspath().

Email Setup
Make sure to:

Replace username & password in EmailReportSender.java

Use Gmail App Password instead of actual password

Author
Anagha S
 anagha2924@gmail.com

License
This project is open-source for learning and demonstration purposes.

 API Used
Dummy JSON API
=======
# dummyjson-api-automation
>>>>>>> 20efc9d67766c7da920fba074f4214489de1a6b2
