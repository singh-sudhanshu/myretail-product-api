# MyRetail REST API

MyRetail RESTful service provides the client application ability to:

    1. Retrieve Product and Price information by Product Id

    2. Send request to modify the price information in the database

Get Product Information:
-----------------------

### Input: 
The client application does a GET request at the path "/products/{id}" for a product 

### Internal Working: 
When the API receives the request, it sends a request to "redsky.target.com" and retrieves the 
product information. This product information doesn't contain price that is needed by the user. The price is retrieved
from a data store. The price information is now combined with the required product information to provide only the 
required product information to the user.

### Output: 
For a product with product id '13860428', the sample JSON output is as shown below

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 19.28,"currency_code":"USD"}}

### Errors/Validations: 
Appropriate error messages are provided after validating the data. The client application can use the message in the response to display the same to the user appropriately.


Update Product Price in the datastore:
-------------------------------------

### Input: 
The user/client application can do a PUT request with input similar to the response received in GET and should be able
to modify the price in the datastore. The request is done at the same path "/products/{id}"

### Sample Input: 
JSON Body - {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 15.67,"currency_code":"USD"}}

### Internal Working: 
When the API receives PUT request, it does request validations to see if the product is available. If it is, 
it updates the price for the product is modified in the data store.

### Output: 
Success message is returned if the price modification is done.

### Errors/Validations: 
Appropriate error messages are provided after validating the data. More information is available in 
the below sections. The client application can use the message in the response to display the same to the user appropriately.

Technologies Used
-----------------

1. Spring Boot - https://projects.spring.io/spring-boot/
2. MongoDB - https://www.mongodb.com/
3. Swagger - http://swagger.io/
4. Gradle - https://gradle.org

Instructions to Setup
---------------------
1. Clone the code from git repository - https://github.com/sudhsi/myretail-product-api
2. Go to myretail-product directory
3. Run the following command to start
`./gradlew bootRun`
4. Open browser and visit Swagger.
`http://localhost:8080/swagger-ui.html`
5. Swagger documentation explains the expected request and response for GET and PUT requests.

Testing
-------
Application has been developed using TDD/BDD approach.
For unit test JUnit has been used and for integration test cucumber for java is used.
The test cases can be executed by running the command './gradlew test'

Swagger UI:
----------
Swagger displays the following information for an API method by default.

  1. Type of request(GET/PUT) and the path of request.
  2. Status and format of the response.
  3. Response Content Type.
  4. Parameters list.
  5. Possible Failure Responses with HTTP code.

The user can modify the values in the fields provided and can do "Try it out!" at the bottom.

More information about the API methods and the responses is provided below.

API Requests and Responses
--------------------------
### PUT Request:

Following PUT request will store information of productID:13860428 in NOSQL database

### Request:

`curl -X PUT "http://localhost:8080/api/v1/products/13860429" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"current_price\": { \"currency_code\": \"USD\", \"value\": 75.23 }, \"id\": 13860429}" \ 
  ' 
 'http://localhost:8080/products/13860428'`
  
### Response Body:

>{
  "id": 13860429,
  "returnDetails": {
    "code": 200,
    "message": "Success",
    "source": "retail-product-api"
  }
}
 
* When productId in request url and body is different it will return 400 Bad Request

## GET Request:
 
### Request:
 
 `curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/13860428'`
 
 ### Response:
 
 >{
   "id": 13860429,
   "name": "SpongeBob SquarePants: SpongeBob's Frozen Face-off",
   "returnDetails": {
     "code": 200,
     "message": "Success",
     "source": "retail-product-api"
   },
   "current_price": {
     "value": 12.57,
     "currency_code": "USD"
   }
 }
 
 * When you give a productID that doesn't exist you will get 404 Product not found.
 
 ### Request:
 
 `curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/1234566'`
 
 ### Response:
 
 >{
   "returnDetails": 
        "code": 404
        "message": "Product Not found"
        "source": "retail-product-api"
 }
 
 ### Swagger screenshots for the RESTful API:
 ---------------------------------------

 ### GET Request Information in Swagger UI
 ---------------------------------
 ![Alt text](/src/test/resources/executions/swagger_home.png?raw=true "Swagger Home")

 Sample GET Response  in Swagger UI
 --------------------------------------------
 ![Alt text](/Sample_GET_Success.png?raw=true "Sample GET Response")

 PUT Request Information in Swagger UI
 ---------------------------------------------
 ![Alt text](/Default_PutRequest.png?raw=true "Default POST Information")

 Sample PUT Response in Swagger UI
 ---------------------------------
 ![Alt text](/Sample_PUT_Success.png?raw=true "Sample POST Response")