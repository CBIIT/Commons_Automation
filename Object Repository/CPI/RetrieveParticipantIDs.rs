<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>RetrieveParticipantIDs</name>
   <tag></tag>
   <elementGuidId>923203c8-b837-48de-af88-4f3016b71637</elementGuidId>
   <selectorMethod>XPATH</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n\&quot;participant_ids\&quot;\n: [\n\&quot;PT_9W90T730\&quot;\n] }&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>445c3a35-6387-41b2-957f-b099d47388b2</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>Bearer eyJraWQiOiJRMVY2Tkw4NEdvbWF3VEt6akpOc256b1ZFOWxjX2hPVmJXQXFWWXhmOVJnIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnVjTzV6UTZoUjJfRFY5RllwZUlYZFRLdzZqR0JVYmpqQ0tiSWMwY1Q3M1EiLCJpc3MiOiJodHRwczovL25paC1uY2kub2t0YXByZXZpZXcuY29tL29hdXRoMi9hdXMxcml5MHV2eUNNQlZpWjBoOCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE3MTQ0OTM5NjYsImV4cCI6MTcxNDQ5NzU2NiwiY2lkIjoiMG9hMXl0dXhmeDFtYWRTRUEwaDgiLCJzY3AiOlsiY3VzdG9tIl0sInN1YiI6IjBvYTF5dHV4ZngxbWFkU0VBMGg4In0.h2VZhOUqmogwDUEINvnvTBdttupn1hWZoYqvXKif3MVsUwxgPqYn24lzh--kO52w18bm4wCf19wWsGdZMhxKOgDjOcLgFZFPsowiEYdLmv7jMRtpQDkykg-OjkQ9duTYb0D7fJuA3LV0jOzkDlAvuIpyiImD1zN29pQ0BN0Yjrr2ZTMdQFqGme7t7VLwelLM7xqaJ4dYIE_UEybPH4vQbGdmRg4T_J8QQfRRoRRiftaGMCveCwqF0_OaLnSn0k1HOqp2O6hNKjQmWon1a8tPQ0mOBW4qBIsAiQAWmYV0RLVAMBZbDEqve0PfGaCv6IrA0e0FVqTAL1qmt7Kqxtllwg</value>
      <webElementGuid>882d5400-1312-4065-981f-290c37d420a8</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.3.2</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://participantindex-qa.ccdi.cancer.gov/v1/participant_ids/domains</restUrl>
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
