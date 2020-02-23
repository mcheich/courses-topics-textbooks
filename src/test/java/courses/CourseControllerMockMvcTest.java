
package courses;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
class CourseControllerMockMvcTest {

	@Autowired
	private MockMvc mockMvc;

	/*
	 * Mike Note to Self
	 * 
	 * I do not understand the difference between Mock and MockBean I get the use -
	 * i.e. Mocks for an Object, MockBeans for a repository of objects. But I am not
	 * sure why...
	 */

	@MockBean
	private CourseRepository courseRepo;

	@MockBean
	private TopicRepository topicRepo;

	@MockBean
	private TextbookRepository textbookRepo;

	@Mock
	private Course courseOne;

	@Mock
	private Course courseTwo;

	@Mock
	private Topic topicOne;

	@Mock
	private Topic topicTwo;

	@Mock
	private Textbook textbookOne;

	@Mock
	private Textbook textbookTwo;

	/*
	 * MIKE Note to Self: I am not sure I understand exceptions, and how, why, or
	 * when to throw them. Why do these test require them, and others do not? What
	 * do they do? Who are they for - developers, end users, both?
	 */

	/********* All Course Tests Follow ***********/
	@Test
	public void shouldGetStatusOfOkWhenWhenNavigatingToAllCourses() throws Exception {
		this.mockMvc.perform(get("/show-courses")).andExpect(status().isOk())
				.andExpect(view().name("courses-template"));
	}

	@Test
	public void shouldAddAllCoursesToTheModel() throws Exception {
		Collection<Course> allCourses = Arrays.asList(courseOne, courseTwo);
		when(courseRepo.findAll()).thenReturn(allCourses);
		this.mockMvc.perform(get("/show-courses")).andExpect(model().attribute("courses", is(allCourses)));
	}

	/********* Single Course Tests Follow ***********/
	@Test
	public void shouldGetStatusOfOKWhenNavigatingToOneCourse() throws Exception {
		long arbitraryId = 1;
		when(courseRepo.findById(arbitraryId)).thenReturn(Optional.of(courseOne));
		this.mockMvc.perform(get("/course?id=1")).andExpect(status().isOk()).andExpect(view().name("course-template"));
	}

	@Test
	public void shouldAddSingleCourseToTheModel() throws Exception {
		when(courseRepo.findById(1L)).thenReturn(Optional.of(courseOne));
		this.mockMvc.perform(get("/course?id=1")).andExpect(model().attribute("courses", is(courseOne)));
	}

	/********* All Topics Tests Follow ***********/
	@Test
	public void shouldRouteToAllTopics() throws Exception {
		this.mockMvc.perform(get("/topics")).andExpect(view().name(is("topics-template")));
	}

	@Test
	public void shouldGetStatusOfOkForAllCourses() throws Exception {
		when(topicRepo.findAll()).thenReturn(Arrays.asList(topicOne, topicTwo));
		this.mockMvc.perform(get("/topics")).andExpect(status().isOk());
	}

	@Test
	public void shouldPutAllTopicsToModel() throws Exception {
		Collection<Topic> allTopics = Arrays.asList(topicOne, topicTwo);
		when(topicRepo.findAll()).thenReturn(allTopics);
		this.mockMvc.perform(get("/topics")).andExpect(model().attribute("topics", is(allTopics)));
	}

	/********* Single Topic Tests Follow ***********/
	@Test
	public void shouldRouteToOneTopic() throws Exception {
		long arbitraryId = 1;
		when(topicRepo.findById(arbitraryId)).thenReturn(Optional.of(topicOne));
		this.mockMvc.perform(get("/topic?id=1")).andExpect(view().name(is("topic-template")));
	}

	@Test
	public void shouldGetStatusOfOkForSingleTopic() throws Exception {
		long arbitraryId = 1;
		when(topicRepo.findById(arbitraryId)).thenReturn(Optional.of(topicOne));
		this.mockMvc.perform(get("/topic?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldAddOneTopicToModel() throws Exception {
		when(topicRepo.findById(1L)).thenReturn(Optional.of(topicOne));
		this.mockMvc.perform(get("/topic?id=1")).andExpect(model().attribute("topics", topicOne));
	}

	/********* All Textbooks Tests Follow ***********/
	@Test
	public void shouldRouteToAllTextbooks() throws Exception {
		this.mockMvc.perform(get("/textbooks")).andExpect(view().name(is("textbooks-template")));
	}

	@Test
	public void shouldGetStatusOfOkForAllTextbooks() throws Exception {
		this.mockMvc.perform(get("/textbooks")).andExpect(status().isOk());
	}

	@Test
	public void shouldAddAllTextbooksToModel() throws Exception {
		Collection<Textbook> allTextbooks = Arrays.asList(textbookOne, textbookTwo);
		when(textbookRepo.findAll()).thenReturn(allTextbooks);
		this.mockMvc.perform(get("/textbooks")).andExpect(model().attribute("textbooks", allTextbooks));
	}

	/********* Single Textbook Tests Follow ***********/
	@Test
	public void shouldRouteToSingleTextbook() throws Exception {
		long arbitraryId = 1;
		when(textbookRepo.findById(arbitraryId)).thenReturn(Optional.of(textbookOne));
		this.mockMvc.perform(get("/textbook?id=1")).andExpect(view().name(is("textbook-template")));
	}

	
	/*Michael's Note to Self:
	 * 
	 * I do not feel like the following test are driving development.
	 * They are not failing at the start, they are passing. 
	 * 
	 * The test before this drives creation of the template, 
	 * but after that, the rest work.  I don't think I should have been creating
	 * the @RequestParams, or the @GettingMapping when I built out the CourseController 
	 * in the first place.  Then these tests would have driven that build out.
	 * */
	@Test
	public void shouldGetStatusOfOkForSingleTextbook() throws Exception {
		long arbitraryId = 1;
		/*
		 * Not fully sure why I need this following line of code for single ietmes, but
		 * not the All items I think, with the "All items", the template resolves
		 * without a RequestParamter. But with the single topics, I need to inject an
		 * actual item...
		 */
		when(textbookRepo.findById(arbitraryId)).thenReturn(Optional.of(textbookOne));
		this.mockMvc.perform(get("/textbook?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldAddSingleTextbookToModel() throws Exception {
		long arbitraryId = 1;
		when(textbookRepo.findById(arbitraryId)).thenReturn(Optional.of(textbookOne));
		this.mockMvc.perform(get("/textbook?id=1")).andExpect(model().attribute("textbooks", textbookOne));
	}
}
