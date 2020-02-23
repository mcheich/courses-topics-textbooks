package courses;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;


public class CourseControllerTest {
	
	@InjectMocks
	private CourseController underTest;
	
	@Mock
	private Course courseOne;
	
	@Mock
	private Course courseTwo;
	
	@Mock
	private CourseRepository courseRepo;
	
	@Mock
	private Model model;
	
	@Mock
	private Topic topicOne;

	@Mock
	private Topic topicTwo;
	
	@Mock
	private TopicRepository topicRepo;
	
	@Mock
	private Textbook textbookOne;

	@Mock
	private Textbook textbookTwo;

	@Mock
	private TextbookRepository textbookRepo;
	
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddSingleCourseToModel() throws CourseNotFoundException {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(courseOne));
		
		underTest.findOneCourse(arbitraryCourseId, model);
		
		verify(model).addAttribute("courses", courseOne);
	}
	
	@Test
	public void shouldAddAllCoursesToModel() {
		Collection<Course> allCourses = Arrays.asList(courseOne, courseTwo);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		underTest.findAllCourses(model);
		
		verify(model).addAttribute("courses", allCourses);
	}
	
	@Test
	public void shouldAddSingleTopicToModel() throws TopicNotFoundException {
		long arbitraryCourseId = 1;
		when(topicRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(topicOne));
		
		underTest.findOneTopic(arbitraryCourseId, model);
		
		verify(model).addAttribute("topics", topicOne);
	}

	@Test
	public void shouldAddAllTopicsToModel() {
		Collection<Topic> allTopics = Arrays.asList(topicOne, topicTwo);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		underTest.findAllTopics(model);
		
		verify(model).addAttribute("topics", allTopics);
	}
	
	@Test
	public void shouldAddOneTextbook() throws TextbookNotFoundException {
		long arbitraryId = 1;
		when(textbookRepo.findById(arbitraryId)).thenReturn(Optional.of(textbookOne));
		
		underTest.findOneTextbook(arbitraryId, model);
		
		verify(model).addAttribute("textbooks", textbookOne);
	}
	
	@Test
	public void shouldAddAllTextbooks() {
		Collection<Textbook> allTextbooks = Arrays.asList(textbookOne, textbookTwo);
		when(textbookRepo.findAll()).thenReturn(allTextbooks);
		
		underTest.findAllTextooks(model);
		
		verify(model).addAttribute("textbooks", allTextbooks);
	}
	

}
