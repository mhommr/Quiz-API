Quiz API
=============================
## Overview

In this assignment students are tasked with creating a small RESTful API that simulates basic backend functionality for a quiz application. Students are given a skeleton that has the `GET quiz` endpoint completely implemented as well as the infrastructure required for it to function. This will allow students to focus solely on completing the other 6 endpoints listed in the [Endpoint Documentation](#endpoint-documentation) below. Student's should start by looking through the provided skeleton and running the app to see what it does.

### Summary of the API

This Quiz API contains 3 entities: `Quiz`, `Question`, and `Answer`. Each have an auto-generated numerical primary key. The `Quiz` entity has a name and maintains a collection of `Question` entities that are part of the the `Quiz` using an `@OneToMany` annotation, provided by JPA, to denote the relationship between the two tables in the database. The `Question` entity has a text field, the `Quiz` that it belongs to annotated with `@ManyToOne` representing the opposite side of the relationship stored in the `Quiz` class, and a collection of `Answer` objects annotated with `@OneToMany`. Finally, the `Answer` entity also has a text field, a boolean to denote if the given `Answer` is the correct one for the `Question` it belongs to, and the `Question` object that it belongs to annotated with `@ManyToOne`.

An entity relationship diagram is provided below that represents the database used by the Quiz API:
![quiz-api](https://user-images.githubusercontent.com/32781877/158852533-29305164-9e9e-41b2-a808-fb1d717b70cf.png)


A data transfer object representing the basic response for each entity type is provided in the skeleton. Notice that the DTOs are slightly different than the entities. This is intentional and is done in order to avoid infinite recursion when serializing to JSON. The student will need to add more DTOs and when they do will also need to add more functionality to their mapper interfaces. (HINT: you will at the minimum need to add a QuizRequestDto)

---

### Endpoint Documentation

The focus of this assignment is on creating endpoints and implementing their functionality in the provided Controller, Service, and Repositories. Note that there is a repository for each table in the database, but there is only 1 controller and 1 service which should handle all the endpoints below. The `Get /quiz` endpoint is implemented for you as an example for you to follow while implemented the other 6.

- [ ] `GET quiz`
    - Returns the collection of `Quiz` elements

- [ ] `POST quiz`
    Creates a quiz and adds to collection
    - Returns the `Quiz` that it created

- [ ] `DELETE quiz/{id}`
    Deletes the specified quiz from collection
    - Returns the deleted `Quiz`

- [ ] `PATCH quiz/{id}/rename/{newName}`
    Rename the specified quiz using the new name given
    - Returns the renamed `Quiz`

- [ ] `GET quiz/{id}/random`
    - Returns a random `Question` from the specified quiz

- [ ] `PATCH quiz/{id}/add`
    Adds a question to the specified quiz
    - Receives a `Question`
    - Returns the modified `Quiz`
    
- [ ] `DELETE quiz/{id}/delete/{questionID}`
    Deletes the specified question from the specified quiz
    - Returns the deleted `Question`
