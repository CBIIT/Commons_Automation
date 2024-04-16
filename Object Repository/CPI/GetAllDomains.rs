<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>GetAllDomains</name>
   <tag></tag>
   <elementGuidId>0c493da7-f9e4-4d70-b70f-f68eb4786081</elementGuidId>
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
      <value>=Bearer eyJraWQiOiIwdHJDLXgtUVlTckVtcHpyWHp1TURIenJTdXlfSXJKbXNHUGNSWEcxeWZjIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULi1VXzBIR09IaUdOOFB3TndIUjM1TzR0UWVCYS12TllRV0lPU3RMNmF0MUkiLCJpc3MiOiJodHRwczovL25paC1uY2kub2t0YXByZXZpZXcuY29tL29hdXRoMi9hdXMxcml5MHV2eUNNQlZpWjBoOCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE3MTMyMzk1MzYsImV4cCI6MTcxMzI0MzEzNiwiY2lkIjoiMG9hMXl0dXhmeDFtYWRTRUEwaDgiLCJzY3AiOlsiY3VzdG9tIl0sInN1YiI6IjBvYTF5dHV4ZngxbWFkU0VBMGg4In0.cq_9pgQ0gv-kyJj2UekHzZOfv4S1-l6kddrVcXK0vIGQRRkwVN0rf42-21cUiDiYMsqgifljiEaGTfwZntc00wIVH7L_rPfwAZ7Y5flE83UtDLMV3er4TM5PFe1EfLY-24OHej2xQhtP1ItH0UFQAlGeNllmzfHI6N4-lqHXvrciiREHlbkO7yinJOGuJRLFj2PbsIa0kwHiQGz1ZzzK1DXqxxYE7JOYPE_by4T9XQJkIOgzsEZMmR6OJm1BxzqqEPX4B5Z7_dCcHMZ3VIfPfZMJOWsLjb6sikRhs9zoIc-vP5dnw5nscP4lpMkQ1Wy8I4kSy0gG5x_yyTsG-FyZRw</value>
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
