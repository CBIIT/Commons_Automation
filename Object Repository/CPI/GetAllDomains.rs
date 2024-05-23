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
      <name>Authorization</name>
      <type>Main</type>
      <value>Bearer eyJraWQiOiJRMVY2Tkw4NEdvbWF3VEt6akpOc256b1ZFOWxjX2hPVmJXQXFWWXhmOVJnIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnNtbl9mUUJWa0J2dUppNU5Oa3FEVlhaSnpKaDVSWW1zRFVIc05XYU1CZ3ciLCJpc3MiOiJodHRwczovL25paC1uY2kub2t0YXByZXZpZXcuY29tL29hdXRoMi9hdXMxcml5MHV2eUNNQlZpWjBoOCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE3MTQ0OTM4MTMsImV4cCI6MTcxNDQ5NzQxMywiY2lkIjoiMG9hMXl0dXhmeDFtYWRTRUEwaDgiLCJzY3AiOlsiY3VzdG9tIl0sInN1YiI6IjBvYTF5dHV4ZngxbWFkU0VBMGg4In0.WB6xbhGKksWwIz8eeKhhezTkF-XridSPrlK9cdlFcR54C094mhQ_kIuxSsdGNAFOEkmsF7uoF3gc-t7ZfdPfLYZ76KBX_9IDf0uGWRk5KiwUzXvxrQCglVuQtwgI3lQDgLy-2tKttHyomcSCVkpSuOpbKXxg7lDuI7BgdqPdBSoafUoYDk4PZKdM_Eal1hG5c_b2fYbNxdNzE2A5i-PLjx7__XnYIP0MPgNAtbdsftbp_Zx_sv-8s86CLgISQ4F4DxnoPUib5I4sr6UwHOo9fXJXJP8m3-4S1kGkDh-WZ5R6qlzY89TJ0l5CsxvRADew8keCad6AK137l5I0SBFevg</value>
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
