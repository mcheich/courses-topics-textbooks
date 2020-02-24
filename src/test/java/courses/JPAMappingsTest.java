package courses;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private TopicRepository topicRepo;
	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TextbookRepository textbookRepo;
	
	
	@Test
	public void shouldSaveAndLoadTopic() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();

		entityManager.flush(); // forces jpa to hit the db when we try to find it
		entityManager.clear();

		Optional<Topic> result = topicRepo.findById(topicId);
		topic = result.get();
		assertThat(topic.getName(), is("topic"));
	}

	@Test
	public void shouldGenerateTopicID() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();

		entityManager.flush(); 
		entityManager.clear();

		assertThat(topicId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadCourse() {
		Course course = new Course("name", "description");
		course = courseRepo.save(course);
		long courseId = course.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		assertThat(course.getName(), is("name"));
	}
	
	@Test
	public void shouldEstablishCourseToTopicsRelationships() {
		Topic java = topicRepo.save(new Topic("java"));
		Topic ruby = topicRepo.save(new Topic("ruby"));
		
		Course course = new Course("OO Languages", "description", java, ruby);
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush();
		entityManager.clear();

		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTopics(), containsInAnyOrder(java,ruby));
	}
	
	
	@Test
	public void shouldFindCoursesForTopic() {
		Topic java = topicRepo.save(new Topic("java"));
		
		Course ooLanguage = courseRepo.save(new Course("OO Languages", "description", java));
		Course advancedJava = courseRepo.save(new Course("Adv Java", "description", java));

		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> coursesForTopic = courseRepo.findByTopicsContains(java);
		
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguage, advancedJava));
	}
	
	@Test
	public void shouldFindCoursesForTopicId() {
		Topic ruby = topicRepo.save(new Topic("ruby"));
		long topicId = ruby.getId();
		
		Course ooLanguage = courseRepo.save(new Course("OO Languages", "description", ruby));
		Course advancedRuby = courseRepo.save(new Course("Adv uby", "description", ruby));
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> coursesForTopicId = courseRepo.findByTopicsId(topicId);
		
		assertThat(coursesForTopicId, containsInAnyOrder(ooLanguage, advancedRuby));
	}
	
	@Test
	public void shouldEstablishTextBookToCourseRelationship() {
		
		
		Course course = courseRepo.save(new Course("name", "description"));
		long courseId = course.getId();
		
		Textbook book = new Textbook("title", course);
		textbookRepo.save(book);

		Textbook book2 = new Textbook("title 2", course);
		textbookRepo.save(book2);
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		
		assertThat(course.getTextbooks(), containsInAnyOrder(book, book2));
	}
	
	@Test
	public void shouldGenerateTextbookId() {
		Course course = courseRepo.save(new Course("name", "description"));
		Textbook textbook = textbookRepo.save(new Textbook("textbook", course));
		long textbookId = textbook.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(textbookId, is(greaterThan(0L)));
	}

}
