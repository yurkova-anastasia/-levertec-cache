# Cache Implementation

## Overview

This project aims to implement a caching system utilizing Least Recently Used (LRU)
and Least Frequently Used (LFU) algorithms. The application is structured with service 
and DAO layers, where the service layer invokes the DAO layer for CRUD operations on entities.
The data synchronization between DAO
and cache is facilitated through a proxy, custom annotation, using AspectJ.

## Features

- **Caching Algorithms:** LRU and LFU algorithms are implemented to efficiently manage the cache.
- **Service and DAO Layers:** The application is organized with service and DAO layers, following the principles of separation of concerns. The service layer interacts with DTOs, and the DAO layer provides a data source.
- **CRUD Operations:** The service layer supports Create, Read, Update, and Delete (CRUD) operations for working with entities.
- **Configuration via YAML:** Algorithm preferences and the maximum size of the collection are configurable via the `application.yml` file.
- **DTO Validation:** DTOs passed to the service layer are validated, including the addition of regex validation for specific fields.

## How to Run

1. Clone the repository: `git clone https://github.com/yurkova-anastasia/clevertec-reflection.git`
2. Open the project in your preferred Java IDE.
3. Set the configurations in `application.yml` according to your preferences.
4. Run the application.
