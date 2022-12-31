# User Management Service

This service is in charge of everything related to user creation and updates. It implements the following use-cases:

![User management service use cases](.github/assets/img/use_cases.png)




As I'm trying to implement the Hexagonal architecture "by-the-book", the components of the service are arranged in the following structure:

![Hex Architecture Model](.github/assets/img/arch_model.png)


This is an example of the flow that a request to the `RegisterAccountUseCase` follows:

![request flow example](.github/assets/img/request_flow.png)

## Development Strategy

For the implementation of this module, I'm putting all the *layers* of the Hexagonal Architecture inside one single project, using **one package per layer**.

### Benefits of this approach

- I don't have to manage different Gradle projects and configurations; this is a all-in-one approach, so I have only one `build.gradle` file.

### Drawbacks of this approach

- In the end, the idea of having all-in-one single project ended creating more problems that benefits. As I started implemented the **Domain** and **Application** hexagons which basically depends on a few third-party libraries, the separation and usage of these dependencies was easy. But as soon as I added the **Infrastructure** hexagon everything became a nighmare, as I'm using several dependencies and modules from *Spring*. And the "temptation" of using some of those functionalities in the inner layers was huge (as they were already there). For the next service I'll use a project-per-layer approach.
- I wanted to depend only on interfaces for the *Spring Validator* on the *Domain* Hexagon, giving the *Infrastructure* the ability to provide the desired implementation. But, as I have to provide an implementation when adding the *Infrastructure* layer, at the end all the effort to only depend on interfaces (or APIs) seems to be broken.

# Tech Stack

| Tool / Framework                                                                                                                                                                                                                                 | Version | Use                                                                    | References |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|------------------------------------------------------------------------|------------|
| <figure style="margin: 0" align="center"><img src="https://spring.io/images/projects/spring-boot-7f2e24fb962501672cc91ccd285ed2ba.svg" width="40" alt="Spring Boot" /><figcaption>Spring Boot</figcaption></figure>                              | 3.0.1   | Main Development Framework                                             | https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/ |
| <figure style="margin: 0" align="center"><img src="https://spring.io/images/projects/spring-data-79cc203ed8c54191215a60f9e5dc638f.svg" width="40" alt="Spring Data JPA" /><figcaption>Spring Data JPA</figcaption></figure>                      | 3.0.0   | Repository support for the Jakarta Persistence API (JPA)               | https://docs.spring.io/spring-data/jpa/docs/3.0.0/reference/html/  |
| <figure style="margin: 0" align="center"><img src="https://spring.io/images/projects/spring-kafka-1f159a30a8723794dfa7260ffbdae5b0.svg?v=2" width="40" alt="Spring for Apache Kafka" /><figcaption>Spring for Apache Kafka</figcaption></figure> | 3.0.1   | Development of Kafka-based messaging solutions                         | https://docs.spring.io/spring-kafka/reference/html/  |
| <figure style="margin: 0" align="center"><img src="https://avatars.githubusercontent.com/u/19369327?s=200&v=4" width="40" alt="REST-assured" /><figcaption>REST-Assured</figcaption></figure>                                                                                                              | 5.3.0   | Simple tool for REST services validation                               | https://github.com/rest-assured/rest-assured/wiki/Spring#spring-mock-mvc-module  |
| <figure style="margin: 0" align="center"><img src="http://hamcrest.org/images/logo.jpg" width="40" alt="Hamcrest" /><figcaption>Hamcrest</figcaption></figure>                                                                                                                      | 2.2     | Matchers that can be combined to create flexible expressions of intent | http://hamcrest.org/JavaHamcrest/javadoc/2.2/org/hamcrest/Matchers.html  |
| <figure style="margin: 0" align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cucumber/cucumber-plain.svg" width="40" alt="Cucumber" /><figcaption>Cucumber</figcaption></figure>                                                                                                                      | 7.10.1  | BDD Framework                                                          | https://cucumber.io/docs/cucumber/  |
| <figure style="margin: 0" align="center"><img src="https://d33wubrfki0l68.cloudfront.net/8f1fa15e47f7ce06b05c856e89734463f0629e19/844e9/logo.svg" width="40" alt="Test Containers" /><figcaption>Test Contaniers</figcaption></figure>                                                                                                        | 1.17.6  | Lightweight containers for integration tests for databases and servers | https://www.testcontainers.org/  |
| <figure style="margin: 0" align="center"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" width="40" alt="PostgreSQL" /><figcaption>PostgreSQL</figcaption></figure>                                                                                                                  | 15.1    | Production database                                                    | https://www.postgresql.org/docs/  |
| <figure style="margin: 0" align="center"><img src="https://avatars.githubusercontent.com/u/11459762?s=200&v=4" width="40" alt="H2" /><figcaption>H2</figcaption></figure>                                                                                                                                  | 2.1     | In-Memory Test Database                                                | https://www.h2database.com|