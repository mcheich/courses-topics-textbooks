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
	
	@Resource
	TextbookRepository textbookRepo;

	
	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value="id") long id, Model model) throws CourseNotFoundException {
		
		Optional<Course> course = courseRepo.findById(id);
		
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course-template";
		}
		
		throw new CourseNotFoundException();
	}

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		
		model.addAttribute("courses", courseRepo.findAll());
		return "courses-template";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value="id") long id, Model model) throws TopicNotFoundException {
		
		Optional<Topic> topic = topicRepo.findById(id);
		
		if(topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic-template";
		}
		
		throw new TopicNotFoundException();
	}

	@RequestMapping("/topics")
	public String findAllTopics(Model model) {
		
		model.addAttribute("topics",topicRepo.findAll());
		return "topics-template";
		
	}

	@RequestMapping("/textbook")
	public String findOneTextbook(long Id, Model model) throws TextbookNotFoundException {

		Optional<Textbook> textbook = textbookRepo.findById(Id);
		
		if(textbook.isPresent()) {
			model.addAttribute("textbooks", textbook.get());
			model.addAttribute("courses", courseRepo.findByTextbooksContains(textbook.get()));
			return "textbook-template";
		}
		
		throw new TextbookNotFoundException();
		
	}

	@RequestMapping("/textbooks")
	public String findAllTextooks(Model model) {
		
		model.addAttribute("textbooks", textbookRepo.findAll());
		return "textbooks-template";
		
	}

}
