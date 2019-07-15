package com.web.service.sample.configuration.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.service.sample.model.Sample;
import com.web.service.sample.repository.SampleRepository;

@Service
public class ManageDatabaseTest {

  @Autowired
  private SampleRepository sampleRepository;

  /**
   * Create TestUser and TestUser2 Sample objects
   */
  public void createSamples() {
    sampleRepository
        .save(Sample.builder().username("TestUser1").description("TestDescription1").build());
    sampleRepository.save(Sample.builder().username("TestUser2").build());
  }

  /**
   * Delete all Sample objects
   */
  public void deleteSamples() {
    sampleRepository.deleteAll();
  }

}
