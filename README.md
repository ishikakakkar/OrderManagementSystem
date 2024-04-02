# OrderManagementSystem

Create REST APIs to accomplish the following:
1. Get a price quote for a given product
2. Submit an order to purchase the product
3. Handle resilience scenarios for API failures.
4. Demonstrate an example for scalability for service.

## Functional Requirements

Implemented the following functionalities in the system:
1. A vendor can onboard a new product in the system. 
2. Update the product details for existing product. 
3. Get product details by providing product Id. 
4. Get list of all the existing products in the system. 
5. A user can place an order to purchase one or more products. 
6. A user can delete the order. 

## Non-Functional Requirements

1. Scalability: The system should be scalable to handle high volume. 
2. Resiliency: The system should be resilient to failures and should be highly available. 
3. Load balancing: The load among multiple servers should be balanced.  
4. Performance: The system should be responsive at all times and should have low latency. 
5. Consistent: The system should be concurrent and have synchronization. 
6. Database Scalability: The database should handle huge amount of data. 
7. Exception Handling: Handle all the possible exceptions. 
8. Testing: Thoroughly test all the modules of the system. 

## Assumptions & Limitations

1. Products once added can not be deleted, but can only be updated. 
2. Product info has only text based information, images and videos are not considered. 
3. Order once placed cannot be updated, but can be deleted. 
4. Order details do not include status information.  
5. Caching layer is not implemented. 
6. The system is not yet deployed.

## Technology Used

1. Coding Language: Java
2. Framework: Spring Boot
3. Database: MySql
4. API Testing: Postman
5. Unit Testing: Mockito
6. IDE: IntelliJ


## High Level Design

## Entity Relationship Diagram

## Sequence Diagram

## Scalability

1. Add cache layer in front of DB to decrease latency. 
2. Horizontally scale services by adding more nodes. 
3. Vertically scale the DB by increasing the capacity. 
4. We can also horizontally scale DB by using sharding.  

## Resiliency

1. We can avoid SPOF by adding more nodes for each service. 
2. We can use master-slave architecture in DB for resiliency. 
3. We can make servers stateless. 

## Future Scope

1. Deleting orders should be soft delete. 
2. Add Status information in the order details. 
3. Add images and videos for the products. Store them in blob storage. 
4. Use CDN for images.
5. Implement concurrency and synchronization. 
5. Develop monitoring and health check service. 
6. Implement caching. 


