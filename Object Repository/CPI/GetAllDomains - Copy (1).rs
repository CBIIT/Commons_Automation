<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>GetAllDomains - Copy (1)</name>
   <tag></tag>
   <elementGuidId>6d9604dd-4c9a-4299-b672-6a34bc643d19</elementGuidId>
   <selectorMethod>XPATH</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>faf5bdd3-eed7-40b2-bc1f-695c45496f98</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>=Bearer eyJraWQiOiJRMVY2Tkw4NEdvbWF3VEt6akpOc256b1ZFOWxjX2hPVmJXQXFWWXhmOVJnIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnRzQTllemk4Zmxrd2pwN2p2NlZQY3dlT1c4NHRLWEg3WlRtUHVyYTM1SVUiLCJpc3MiOiJodHRwczovL25paC1uY2kub2t0YXByZXZpZXcuY29tL29hdXRoMi9hdXMxcml5MHV2eUNNQlZpWjBoOCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE3MTQ0MTk4MDYsImV4cCI6MTcxNDQyMzQwNiwiY2lkIjoiMG9hMXl0dXhmeDFtYWRTRUEwaDgiLCJzY3AiOlsiY3VzdG9tIl0sInN1YiI6IjBvYTF5dHV4ZngxbWFkU0VBMGg4In0.m-6vlfe8V3sO53C5XYyIk-YwfVE5b8G0EBLL0-6K3GuB3GTMNotor-kWN1vcbuC_7x-R9HFSut5DBbCz96d2xjLas7vjTzJz5AEy-GbjzNXx_xWvMYnMZyjKwMyzzAw-WrBcvxNAx498kSHFmEY1GCo3LAaqG7ZTmRlQ6g80xOQCQ8WpPdulKGBJnL2G4bg59LBh85ThnWzvqHLbFB7N0xD-HTwFZmil4jSvIvzygTlx0IkhlbXQdysIGmd17mzFglVGUsah-8IaCwMnRbSIrbANymfBsravyV_Yh_-WV42p85V-fbBHUmi8tOoOdHfuI2FMBxpoOp0RrcG-5mARig</value>
      <webElementGuid>882d5400-1312-4065-981f-290c37d420a8</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.3.2</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://participantindex-qa.ccdi.cancer.gov/v1/domains/</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
