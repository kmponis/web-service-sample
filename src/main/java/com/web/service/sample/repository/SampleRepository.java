package com.web.service.sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.web.service.sample.model.Sample;

@Repository
public interface SampleRepository extends MongoRepository<Sample, String> {

  Sample findByUsername(String username);
}
