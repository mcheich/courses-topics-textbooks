package courses;

import static org.hamcrest.CoreMatchers.not;

import java.util.Collection;
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

	/*
	 * Michael Note to self.
	 * 
	 * I am not sure I fully understand the @RequestParam stuff. Is it basically
	 * saying, parse the URL, and find the parameter?
	 * 
	 */
	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value = "id") long id, Model model) throws CourseNotFoundException {

		Optional<Course> course = courseRepo.findById(id);

		if (course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course-template";
		}

		throw new CourseNotFoundException();
	}

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {

		model.addAttribute("courses", courseRepo.findAll());
		//model.addAttribute("courses", courseRepo.findAllByOrderByNameAsc());
		return "courses-template";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id") long id, Model model) throws TopicNotFoundException {

		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic-template";
		}

		throw new TopicNotFoundException();
	}

	@RequestMapping("/topics")
	public String findAllTopics(Model model) {

		model.addAttribute("topics", topicRepo.findAll());
		return "topics-template";

	}

	@RequestMapping("/textbook")
	public String findOneTextbook(@RequestParam(value = "id") long Id, Model model) throws TextbookNotFoundException {

		Optional<Textbook> textbook = textbookRepo.findById(Id);

		if (textbook.isPresent()) {
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

	@RequestMapping("/add-course")
	public String addCourse(String courseName, String courseDescription, String topicName) {
		Topic topic = topicRepo.findByName(topicName);
		Course newCourse = courseRepo.findByName(courseName);

		if (topic == null) {
			topic = new Topic(topicName);
			topicRepo.save(topic);
		}

		if (newCourse == null) {
			newCourse = new Course(courseName, courseDescription, topic);
			courseRepo.save(newCourse);
		}

		return "redirect:/show-courses";
	}

	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {

		if (courseRepo.findByName(courseName) != null) {
			Course deletedCourse = courseRepo.findByName(courseName);
			courseRepo.delete(deletedCourse);
		}
		return "redirect:/show-courses";
	}

	@RequestMapping("/delete-course-by-id")
	public String deleteCourseById(Long courseId) {
		
		courseRepo.deleteById(courseId);
		return "redirect:/show-courses";
	}
	
	@RequestMapping("/find-by-topic")
	public String findCoursesByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("courses", courseRepo.findByTopicsContains(topic));
		
		return "/topic-template";
	}
	
	@RequestMapping("/sort-courses")
	public String sortCourses(Model model) {
		
		model.addAttribute("courses", courseRepo.findAllByOrderByNameAsc());
		return "courses-template";
	}
}
