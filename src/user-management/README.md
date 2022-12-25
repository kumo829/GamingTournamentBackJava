# User Management Service

This service is in charge of everything related to user creation and updates. It implements the following use-cases:

![User management service use cases](.github/assets/img/use_cases.png)


As I'm trying to implement the Hexagonal architecture "by-the-book", the components of the service are arranged in the following structure:

![Hex Architecture Model](.github/assets/img/arch_model.png)


This is an example of the flow that a request to the `RegisterAccountUseCase` follows:

![request flow example](.github/assets/img/request_flow.png)

