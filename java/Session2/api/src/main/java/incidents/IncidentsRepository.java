package incidents;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "incidents", path = "incidents")
public interface IncidentsRepository extends MongoRepository<IncidentBean, String> {

    public IncidentBean findById(String incidentID);
    public List<IncidentBean> findAll();

}