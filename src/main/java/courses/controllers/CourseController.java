package courses.controllers;

import static org.hamcrest.CoreMatchers.not;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import courses.CourseNotFoundException;
import courses.TextbookNotFoundException;
import courses.TopicNotFoundException;
import courses.models.Course;
import courses.models.Textbook;
import courses.models.Topic;
import courses.repositories.CourseRepository;
import courses.repositories.TextbookRepository;
import courses.repositories.TopicRepository;

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
			model.addAttribute("course", course.get());
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
			model.addAttribute("topic", topic.get());
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

		Course foundCourse = courseRepo.findByName(courseName);
		
		
		if (foundCourse != null) {
			
			for(Textbook text : foundCourse.getTextbooks()) {
				textbookRepo.delete(text);
			}
			courseRepo.delete(foundCourse);
		}
		return "redirect:/show-courses";
	}

	@RequestMapping("/delete-course-by-id")
	public String deleteCourseById(Long courseId) {
		
		Optional<Course> foundCourse = courseRepo.findById(courseId);
		Course courseToRemoveCourse = foundCourse.get();
		
		for(Textbook text : courseToRemoveCourse.getTextbooks()) {
			textbookRepo.delete(text);
		}
		
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
	
	@RequestMapping(path="/topics/{topicName}", method=RequestMethod.POST)
	public String addTopic(@PathVariable String topicName, Model model) {
		Topic topicToAdd = topicRepo.findByName(topicName);
		
		if(topicToAdd == null) {
			topicToAdd = new Topic(topicName);
			topicRepo.save(topicToAdd);
		}
		model.addAttribute("topics", topicRepo.findAll());		
		return "partials/topics-list-added";
	}
	
}
