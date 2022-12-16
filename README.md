# Java Back-end for the Gaming Tournaments Manager Project 

This project implements the backend for my other project, [Gaming Tournament Front](https://github.com/kumo829/GamingTournamentFront), using Java as the main programming language.

For this project I'll use a Clean Architecture, implementing the Hexagonal Architecture and DDD. 

To provide a general context of this architecture:


## Clean Architecture

Clean Architecture, that is usually explained using this diagram which shows how such an architecture might look on an abstract level:

![Clean Architecture Diagram](.github/assets/img/clean_architecture.png)

Here, the business rules are testable by design and independent of frameworks, databases, UI technologies, and other external applications or interfaces.

That means that the domain code must not have any outward-facing dependencies. Instead, with the help of the DIP, all dependencies point toward the domain code.

The layers in this architecture are wrapped around each other in concentric circles. The main rule in such an architecture is the dependency rule, which states that all dependencies between those layers must point inward.

The core of the architecture contains the **domain entities**, which are accessed by the surrounding **use cases**. The use cases are what we have called services earlier but are more fine-grained to have a single responsibility (that is, a single reason to change), thus avoiding the problem of broad services.

Around this core, we can find all the other components of our application that support the business rules. This support can mean providing persistence or providing a UI, for example. Also, the outer layers may provide adapters to any other third-party component.

Since the domain code knows nothing about which persistence or UI framework is used, it cannot contain any code specific to those frameworks and will concentrate on the business rules. We have all the freedom we could wish for to model the domain code. We could, for example, apply Domain-Driven Design (DDD) in its purest form. Not having to think about persistence or UI-specific problems makes that so much easier.

As we might expect, clean architecture comes at a cost. Since the domain layer is completely decoupled from the outer layers, such as persistence and UI, we have to maintain a model of our application's entities in each of the layers.


## Hexagonal Architecture

For the Hexagonal architecture, there are different ways to represent it (and implement it). The preceding figure shows what a hexagonal architecture might look like.

![Hex Architecture Diagram 1](.github/assets/img/hex_architecture_1.png)

Within the hexagon, we find our **domain entities** and the **use cases** that work with them. Note that the hexagon has no outgoing dependencies, instead all dependencies point toward the center.

Outside of the hexagon, we find various adapters that interact with the application. There might be a web adapter that interacts with a web browser, some adapters interacting with external systems, and an adapter that interacts with a database.

The adapters on the left-hand side are adapters that **drive** our application (because they call our application core) while the adapters on the right-hand side are **driven** by our application (because they are called by our application core).

To allow communication between the application core and the adapters, the application core provides specific **ports**. For driving adapters, such a port might be an interface that is implemented by one of the use case classes in the core and called by the adapter. For a driven adapter, it might be an interface that is implemented by the adapter and called by the core.

That's why sometimes the Hexagonal architecture is represented using this diagram, that combines the entities and use cases in a central element called **Business Logic**, **Domain**, or **Core**.

![Hex Architecture Diagram 2](.github/assets/img/hex_architecture_2.png)

The previous diagram is often simplified in this way, which also helps to integrate the concepts of DDD:

![Hex Architecture Diagram 3](.github/assets/img/hex_architecture_3.png)

One of the main ideas of the hexagonal architecture is to separate business code from technology code. To achieve these goals, we must determine a place where the business code will exist, isolated and protected from any technology concerns. It'll give rise to the creation of our first hexagon: the **Domain hexagon**.

In the **Domain** hexagon, we assemble the elements responsible for describing the core problems we want our software to solve. **Entities** and **Value objects** are the main elements that are utilized in the Domain hexagon. Entities represent things we can assign an identity to, and value objects are immutable components that we can use to compose our entities. The terms refer to both the entities and value objects that come from DDD principles.

![Domain Hexagon](.github/assets/img/domain.png)

The **Domain** hexagon represents an effort to understand and model a real-world problem.

We also need ways to use, process, and orchestrate the business rules coming from the Domain hexagon. That's what the **Application** hexagon does. It sits between the business and technology sides, serving as a middleman to interact with both parties. The Application hexagon utilizes **ports** and **use cases** to perform its functions.
![Application Hexagon](.github/assets/img/application.png)

The **Infrastructure** (sometimes also called **Framework**) hexagon provides the outside world interface. That's the place where we have the opportunity to determine how to expose application features – this is where we define REST or gRPC endpoints, for example. And to consume things from external sources, we use the Infrastructure hexagon to specify the mechanisms to fetch data from databases, message brokers, or any other system. In the hexagonal architecture, we materialize technology decisions through **adapters**.

![Infrastructure Hexagon](.github/assets/img/infrastructure.png)
