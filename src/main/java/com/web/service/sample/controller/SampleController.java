package com.web.service.sample.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.web.service.sample.model.Sample;
import com.web.service.sample.service.SampleService;
import io.swagger.annotations.ApiOperation;

@RestController
public class SampleController {

  @Autowired
  private SampleService sampleService;

  @ApiOperation(value = "Create or Update object Sample", response = ResponseEntity.class)
  @PostMapping(value = "/saveSample")
  public ResponseEntity<String> saveSample(@RequestBody Sample sample) {
    return sampleService.saveSample(sample);
  }

  @ApiOperation(value = "Delete object Sample", response = ResponseEntity.class)
  @PostMapping(value = "/deleteSample")
  public ResponseEntity<String> deleteSample(@RequestParam(value = "username") String username) {
    return sampleService.deleteSample(username);
  }

  @ApiOperation(value = "Get object Sample by username", response = Sample.class)
  @GetMapping(value = "/sampleByUsername/{username}")
  public Optional<Sample> getSampleByUsername(@PathVariable("username") String username) {
    return Optional.ofNullable(sampleService.getSampleByUsername(username));
  }

  @ApiOperation(value = "Get all object Samples as a list", response = Sample.class)
  @GetMapping(value = "/allSample")
  public List<Sample> getAllSample() {
    return sampleService.getAllSample();
  }

}
