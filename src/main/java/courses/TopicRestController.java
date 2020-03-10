package courses;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restTopics")
public class TopicRestController {
	
	@Resource
	private TopicRepository topicRepo;

	@Resource
	private CourseRepository courseRepo;
	
	
	
	@RequestMapping("")
	public Iterable<Topic> findAllTopics() {
		return topicRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<Topic> findTopicById(@PathVariable Long id) {
		return topicRepo.findById(id);
	}
	
	@RequestMapping("/topics/{topicName}")
	public Collection<Course> findAllCoursesByTopic(@PathVariable(value="topicName") String topicName){
		Topic topic = topicRepo.findByName(topicName);
		return courseRepo.findByTopicsContains(topic);
	}

}
