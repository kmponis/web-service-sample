package com.web.service.sample.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.web.service.sample.model.Sample;

public interface SampleService {

  /**
   * Create or Update object Sample.
   * 
   * @param (Sample) sample
   * @return (ResponseEntity<String>)
   */
  public ResponseEntity<String> saveSample(Sample sample);

  /**
   * Delete object Sample.
   * 
   * @param (String) username
   * @return (ResponseEntity<String>)
   */
  public ResponseEntity<String> deleteSample(String username);

  /**
   * Get object Sample by username
   * 
   * @param (String) username
   * @return (Sample)
   */
  public Sample getSampleByUsername(String username);

  /**
   * Get all object Samples as a list
   * 
   * @return (List(Sample))
   */
  public List<Sample> getAllSample();

}
