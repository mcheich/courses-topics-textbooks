package courses;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CoursePopulator implements CommandLineRunner {

	@Resource
	private CourseRepository courseRepo;

	@Resource
	private TopicRepository topicRepo;

	@Resource
	private TextbookRepository textbookRepo;

	@Override
	public void run(String... args) throws Exception {
		
		
		Topic java = new Topic("Java");
		java = topicRepo.save(java);  //Why do I have java= on this line of code?  am I setting the course equal to the repo?
		Topic spring= new Topic("spring");
		java = topicRepo.save(spring);
		Topic tdd = new Topic("tdd"); 
		java = topicRepo.save(tdd);
		
		Course java101 = new Course("Intro to Java", "Learn the Fundamental of java", java, spring, tdd);
		java101 = courseRepo.save(java101);
		Course java102 = new Course("Advanced Java", "Learn the way more java yp", java, tdd, spring);
		java101 = courseRepo.save(java102);
		
		textbookRepo.save(new Textbook("Head First Java", java101));
		textbookRepo.save(new Textbook("Head First Design Patterns", java102));
		textbookRepo.save(new Textbook("Clean Code", java102));
		textbookRepo.save(new Textbook("Intro to JPA", java102));
		
	}
}
