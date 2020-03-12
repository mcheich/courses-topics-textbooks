package courses.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import courses.models.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {

	Topic findByName(String topicName);

	Topic findByNameIgnoreCaseLike(String topicName);

}
