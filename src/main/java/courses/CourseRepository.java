package courses;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	/*Michael's note to self...
	 * 
	 * I am just not sure I get this.  Would like to talk more about it in class.
	 * 
	 * */
	Collection<Course> findByTopicsContains(Topic topic);

	Collection<Course> findByTopicsId(Long topicId);

	Collection<Course> findByTextbooksContains(Textbook textbook);
}
