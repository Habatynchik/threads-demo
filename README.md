# Create a simple spring boot app with the following conditions:
- Create a variable(counter) that can be shared by all the clients/components in context, the initial value of the counter is 50.
- There is an ENDPOINT that will receive two request parameter, the first one will increase the number of producer threads. The second parameter will increase the number of consumer threads. The response will be HTTP 201 Created success status.
- Using any RDBMS, persist the request's information received by the app to the database.
- The producer threads will increase the value of the counter while the consumer threads will decrease it. Frequency of changing the counter value should be random per thread, bounds should be specified in app properties. When thread starts it should print it's info.
- Print in the console the current value of the counter when it changes and print which producer/consumer is responsible for the change.
- The threads will run in parallel and continue until the counter reaches 0 or 100. Persist in the database the timestamp when the counter reaches 0 or 100.
- Create another ENDPOINT that will receive one parameter, the parameter will change the current value of the counter. The response will be HTTP 200 Ok success status.
- Dockerizing the app is nice to have.

## Steps to Run the Application
1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
2. Build the Application

    Use mvn and docker compose to build and run the application:

    ```bash
   mvn clean install
   docker-compose up --build
   ```
3. Verify the Setup
   The Spring Boot application will be accessible at http://localhost:8080
