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