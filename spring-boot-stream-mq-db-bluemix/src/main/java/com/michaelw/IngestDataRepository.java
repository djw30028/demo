package com.michaelw;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface IngestDataRepository extends CrudRepository<IngestData, Long> {

}
