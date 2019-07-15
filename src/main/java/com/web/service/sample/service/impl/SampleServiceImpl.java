package com.web.service.sample.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.web.service.sample.model.Sample;
import com.web.service.sample.properties.ResponseProperties;
import com.web.service.sample.repository.SampleRepository;
import com.web.service.sample.service.SampleService;

@Component
public class SampleServiceImpl implements SampleService {

  @Autowired
  private SampleRepository sampleRepository;

  @Autowired
  private ResponseProperties responseProperties;

  @Override
  public ResponseEntity<String> saveSample(Sample sample) {
    if (null == sample.getUsername()) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
          .body(responseProperties.errorObjectNotSaved);
    }

    Sample existingSample = sampleRepository.findByUsername(sample.getUsername());
    if (null == existingSample) {
      sampleRepository.save(sample);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseProperties.objectSaved);
    }

    existingSample.setDescription(sample.getDescription());
    sampleRepository.save(existingSample);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseProperties.objectUpdated);
  }

  @Override
  public ResponseEntity<String> deleteSample(String username) {
    Sample existingSample = sampleRepository.findByUsername(username);
    if (null == existingSample) {
      return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
          .body(responseProperties.errorObjectDoesntExists);
    }

    sampleRepository.delete(existingSample);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseProperties.objectDeleted);
  }

  @Override
  public Sample getSampleByUsername(String username) {
    return sampleRepository.findByUsername(username);
  }

  @Override
  public List<Sample> getAllSample() {
    return sampleRepository.findAll();
  }

}
