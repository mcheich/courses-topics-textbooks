package courses.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import courses.models.Course;
import courses.models.Topic;
import courses.repositories.CourseRepository;
import courses.repositories.TopicRepository;

@RestController
@RequestMapping("/courses")
public class CourseRestCountroller {

	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TopicRepository TopicRepo;

	@RequestMapping("")
	public Iterable<Course> findAllCourses() {

		return courseRepo.findAll();
	}

	@RequestMapping("{id}")
	public Optional<Course> findOneCourse(@PathVariable long id) {
		
		return courseRepo.findById(id);
	}
	
	@RequestMapping("/topics/{topicName}")
	public Collection<Course> findAllCoursesByTopic(@PathVariable(value="topicName") String topicName) {
		Topic topic = TopicRepo.findByNameIgnoreCaseLike(topicName);
		return courseRepo.findByTopicsContains(topic);	
	}

}
