package com.project.flightbooking.service.search;


import com.project.flightbooking.dto.FlightSearchInput;
import com.project.flightbooking.dto.QpxExpressFlightSearchResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class QpxExpressFlightSearchServiceImpl implements QpxExpressFlightSearchService {

    private String apiKey;

//    @Value()// Add value here
    private String qpxExpressUrl;

    private FlightSearchInput flightSearchInput;

    public QpxExpressFlightSearchServiceImpl() {

    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setQpxExpressUrl(String qpxExpressUrl) {
        this.qpxExpressUrl = qpxExpressUrl;
    }

    @Override
    public void setFlightSearchInput(FlightSearchInput flightSearchInput) {
        this.flightSearchInput = flightSearchInput;
    }

    @SuppressWarnings("unchecked")
    @Override
    public QpxExpressFlightSearchResult search() {

        // create request body
        JSONObject adultObject = new JSONObject();
        adultObject.put("adultCount", flightSearchInput.getAdultCount());

        JSONObject departObject = new JSONObject();
        departObject.put("origin", flightSearchInput.getOrigin());
        departObject.put("destination", flightSearchInput.getDestination());
        departObject.put("date", flightSearchInput.getDepartDate());

        JSONObject returnObject = new JSONObject();
        returnObject.put("origin", flightSearchInput.getDestination());
        returnObject.put("destination", flightSearchInput.getOrigin());
        returnObject.put("date", flightSearchInput.getReturnDate());

        JSONArray sliceList = new JSONArray();
        sliceList.add(departObject);
        sliceList.add(returnObject);

        JSONObject requestObject = new JSONObject();
        requestObject.put("passengers", adultObject);
        requestObject.put("slice", sliceList);

        JSONObject request = new JSONObject();
        request.put("request", requestObject);

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

        // send request and parse result
        RestTemplate restTemplate = new RestTemplate();

        long startTime = System.currentTimeMillis();

        ResponseEntity<QpxExpressFlightSearchResult> searchResult = restTemplate.exchange(qpxExpressUrl + apiKey,
                HttpMethod.POST, entity, QpxExpressFlightSearchResult.class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed: " + elapsedTime);

        return searchResult.getBody();
    }

}