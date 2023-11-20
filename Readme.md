# Zendesk Ticket Viewer
This project is a Spring Boot application that interacts with the **ZendeskAPI** to fetch and delete ticket data.

## Running the project
You can run the application from the command line with Maven. Go to the root folder of the app and type:

```
mvn spring-boot:run
```

The application should be up and running on port 8080.

## Accessible Endpoints

- `GET /tickets`: Fetches all tickets.
- `GET /tickets/{ticket_id}`: Fetches a ticket with the provided ID.
- `DELETE /tickets/{id}`: Deletes a ticket with the provided ID.

## Testing

This project includes unit tests written with JUnit. You can run them with Maven using:

```
mvn test
```

## Exception handling

The application employs a central exception handling mechanism. For instance, `HttpClientErrorException` is caught when API requests fail.

## Built With

- Spring Boot
- Spring Web MVC
- Maven
- JUnit
- OkHttp (if this is still relevant)

## Sample API Calls:

* Fetch tickets:
```
GET http://localhost:8080/tickets/
```

* Fetch tickets by ID:
```
GET http://localhost:8080/tickets/{id}
```

* Delete ticket:
```
DELETE http://localhost:8080/tickets/delete/{id}
``` 

*{id} should be replaced by the actual id of the ticket that you want to delete

## Configuration

This application uses application.properties for its configuration. Store your OpenAPI API credentials in this file as follows:

```
api.username=YOUR_USERNAME
api.password=YOUR_PASSWORD
```

