package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

	@Resource
	CourseRepository courseRepo;
	
	@Resource
	TopicRepository topicRepo;
	
	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value="id") long id, Model model) throws CourseNotFoundException {
		
		Optional<Course> course = courseRepo.findById(id);
		
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "courses";
		}
		
		throw new CourseNotFoundException();
	}

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		
		model.addAttribute("courses", courseRepo.findAll());
		return "courses";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value="Id") long Id, Model model) throws TopicNotFoundException {
		
		Optional<Topic> topic = topicRepo.findById(Id);
		
		if(topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic";
		}
		
		throw new TopicNotFoundException();
	}

	@RequestMapping("/topics")
	public String findAllTopics(Model model) {
		
		model.addAttribute("topics",topicRepo.findAll());
		return "topics";
		
	}

}
