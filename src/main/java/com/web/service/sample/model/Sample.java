package com.web.service.sample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Sample entity to bind with MongoDB - Used by SampleRepository.java
 */
@Data
@Builder
@AllArgsConstructor
public class Sample {

  private String id;

  // @Indexed(unique = true)
  private String username;

  private String description;

}
