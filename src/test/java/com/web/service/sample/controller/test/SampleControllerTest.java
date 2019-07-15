package com.web.service.sample.controller.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.service.sample.configuration.test.ManageDatabaseTest;
import com.web.service.sample.model.Sample;
import com.web.service.sample.properties.ResponseProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SampleControllerTest {

  @Autowired
  private ManageDatabaseTest manageDatabaseTest;

  @Autowired
  private ResponseProperties responseProperties;

  private RestTemplate restTemplate;
  private HttpHeaders httpHeaders;
  private ObjectMapper objectMapper;

  private String baseUrl;
  private String saveSampleUrl;
  private String deleteSampleUrl;
  private String sampleByUsernameUrl;
  private String allSampleUrl;

  @Before
  public void setUp() throws Exception {
    restTemplate = new RestTemplate();
    httpHeaders = new HttpHeaders();
    objectMapper = new ObjectMapper();

    baseUrl = "http://localhost:8980";
    saveSampleUrl = "/saveSample";
    deleteSampleUrl = "/deleteSample";
    sampleByUsernameUrl = "/sampleByUsername/";
    allSampleUrl = "/allSample";
  }

  @After
  public void clearUp() {
    manageDatabaseTest.deleteSamples();
  }

  @Test(expected = HttpClientErrorException.class)
  public void givenEmptydDB_whenSaveSampleWithoutUsername_thenAssertObjectNotSaved() {
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    String jsonSample = "{\"description\":\"TestDescription1\"}";
    HttpEntity<String> httpEntity = new HttpEntity<String>(jsonSample, httpHeaders);
    restTemplate.postForObject(baseUrl + saveSampleUrl, httpEntity, String.class);
  }

  @Test
  public void givenEmptyDB_whenSaveSample_thenAssertObjectSaved() {
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    String jsonSample = "{\"username\":\"TestUser1\", \"description\":\"TestDescription1\"}";
    HttpEntity<String> httpEntity = new HttpEntity<String>(jsonSample, httpHeaders);
    String response = restTemplate.postForObject(baseUrl + saveSampleUrl, httpEntity, String.class);
    assertEquals(responseProperties.objectSaved, response);

    manageDatabaseTest.deleteSamples();
  }

  @Test
  public void givenInitialisedDB_whenSaveSample_thenAssertObjectUpdated() {
    manageDatabaseTest.createSamples();

    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    String jsonSample = "{\"username\":\"TestUser1\", \"description\":\"TestDescription1\"}";
    HttpEntity<String> entity = new HttpEntity<String>(jsonSample, httpHeaders);
    String response = restTemplate.postForObject(baseUrl + saveSampleUrl, entity, String.class);
    assertEquals(responseProperties.objectUpdated, response);

    manageDatabaseTest.deleteSamples();
  }

  @Test(expected = HttpClientErrorException.class)
  public void givenEmptyDB_whenDeleteSample_thenAssertObjectNotDeleted() {
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
    parameters.add("username", "TestUser1");
    HttpEntity<MultiValueMap<String, String>> request =
        new HttpEntity<MultiValueMap<String, String>>(parameters, httpHeaders);
    restTemplate.postForEntity(baseUrl + deleteSampleUrl, request, String.class);
  }

  @Test
  public void givenInitialisedDB_whenDeleteSample_thenAssertObjectDeleted() {
    manageDatabaseTest.createSamples();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
    parameters.add("username", "TestUser1");
    HttpEntity<MultiValueMap<String, String>> request =
        new HttpEntity<MultiValueMap<String, String>>(parameters, httpHeaders);
    ResponseEntity<String> response =
        restTemplate.postForEntity(baseUrl + deleteSampleUrl, request, String.class);
    assertEquals(responseProperties.objectDeleted, response.getBody());

    manageDatabaseTest.deleteSamples();
  }

  @Test
  public void givenEmptyDB_whenGetSampleByUsername_thenAssertNull() {
    Sample sample =
        restTemplate.getForObject(baseUrl + sampleByUsernameUrl + "TestUser1", Sample.class);
    assertNull(sample, () -> "Returned empty object");
  }

  @Test
  public void givenInitialisedDB_whenGetSampleByUsername_thenAssertNotNull() {
    manageDatabaseTest.createSamples();

    Sample sample =
        restTemplate.getForObject(baseUrl + sampleByUsernameUrl + "TestUser1", Sample.class);
    assertAll("Assert Sample object", () -> {
      assertNotNull(sample, () -> "Returned object");
      assertEquals("TestUser1", sample.getUsername());
      assertEquals("TestDescription1", sample.getDescription());
    });

    manageDatabaseTest.deleteSamples();
  }

  @Test
  public void givenEmptyDB_whenGetAllSample_thenAssertEmptyList() throws IOException {
    String response = restTemplate.getForObject(baseUrl + allSampleUrl, String.class);
    List<Sample> samples = objectMapper.readValue(response,
        objectMapper.getTypeFactory().constructCollectionType(List.class, Sample.class));
    assertFalse(samples.size() > 0, () -> "Returned an empty list");
  }

  @Test
  public void givenInitialisedDB_whenGetAllSample_thenAssertSampleList() throws IOException {
    manageDatabaseTest.createSamples();

    String response = restTemplate.getForObject(baseUrl + allSampleUrl, String.class);
    List<Sample> samples = objectMapper.readValue(response,
        objectMapper.getTypeFactory().constructCollectionType(List.class, Sample.class));
    assertAll("Assert a non empty list", () -> {
      assertEquals(samples.size(), 2);
      assertNotNull(samples.get(0), () -> "First object");
      assertEquals(samples.get(0).getUsername(), "TestUser1");
      assertEquals(samples.get(0).getDescription(), "TestDescription1");
      assertNotNull(samples.get(1), () -> "Second object");
      assertEquals(samples.get(1).getUsername(), "TestUser2");
      assertNull(samples.get(1).getDescription());
    });

    manageDatabaseTest.deleteSamples();
  }

}
