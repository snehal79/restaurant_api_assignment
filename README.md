# restaurant_api

Restaurant backend API which accepts orders based on table number from various serving staff in the restaurant. It
stores the all the items along with a cooking time for the item to be completed. HTTP REST is used, to bring request
parameters into the application using spring-boot. The application performs below operations and produces results based
on different request/query parameters:

* Add one or more items with a table number, remove an item for a table, and query the items still remaining for a
  table.
* Stores the item, the table number, and how long the item will take to cook.
* On deletion request, remove a specified item for a specified table number.
* On query request, show all items for a specified table number.
* On query request, show a specified item for a specified table number.
* Accepts at least 10 simultaneous incoming add/remove/query requests.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org)
- [MySQL 8](https://www.mysql.com/)

## Running the application locally

## DB Setup

### Assumptions

- `restaurant_db` is created with access user.
- set environment variables `DB_DATASOURCE_USERNAME` and `DB_DATASOURCE_PASSWORD`
- import `sql/restaurant_db.sql`

## Code Setup

There are several ways to run a Spring Boot application on your local machine, import the project in your IDE, and
execute the `main` method in the `com/restaurent/order/RestaurantApplication.java` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Testing

### Unit tests

```shell
mvn test
```

### Postman test collection

Used postman for API testing https://www.postman.com/

Testcases are here: `postman_collection/restaurant_api_tests_collection.postman_collection.json`
