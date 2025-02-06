import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.ConditionType

// Define the access token (replace this with your actual token)
def accessToken = "eyJraWQiOiJLaGphOWlTbXU2bU9nRUhUcnFucXIxQS1tQU1Ka1Zyb0FHdUNUWTFkU2ZFIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULmFTVGJWaERYdTRzYVlIbjc2b1lIMzNyOFU2NXNJQ2FqSEt0bi1oMGNqa0kiLCJpc3MiOiJodHRwczovL25paC1uY2kub2t0YS5jb20vb2F1dGgyL2F1c3FzdXltMGF0cFN4WFRBMjk3IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTcyODI3ODc0NSwiZXhwIjoxNzI4MjgyMzQ1LCJjaWQiOiIwb2FxdGlydXVpelU5bzFHWDI5NyIsInNjcCI6WyJjdXN0b20iXSwic3ViIjoiMG9hcXRpcnV1aXpVOW8xR1gyOTcifQ.FF-tS4ROVb_b53vuAkaB8J7yPKvYEUCfr319bVw10fA1n6VDHOQNsKNVzh-RjV0ejg5KJVHw0z4NQCaeHxjnQriKVjw0_7qgT4y-RnJ4q8WIZ_OsUR6QOesuykEbFY6guFmDGSL9u3Z45TdzmLqo5fD2CBf0H8P-2HlPuXnrqwNetN-c24EGQHz2fX5Rb7vT7LDY-LqCj_GVfHPAwj0eIgqeAbtwFhHkF7qgkVI_VFkjO-4TAbygdW-HOyl5Yi8eU3Rmrw8rEgdVve_3JNa4yTCZfor05EDssKpT0F7tGQydsiCLUEOHxOoqn3by1habwJ7imD0-DTjsMMQhNK0meg"

// Find the API request object from Object Repository
def requestObject = findTestObject('Object Repository/GetUserDetails')

// Manually set the Authorization header with Bearer token
requestObject.getHttpHeaderProperties().clear()
requestObject.getHttpHeaderProperties().add(new TestObjectProperty('Authorization', ConditionType.EQUALS, "Bearer ${accessToken}"))

// Send the request and get the response
ResponseObject response = WS.sendRequest(requestObject)

// Print the response text
println("Response: " + response.getResponseText())

// Verify the response status code
WS.verifyResponseStatusCode(response, 200)
