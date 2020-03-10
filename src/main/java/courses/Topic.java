package courses;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@ManyToMany(mappedBy = "topics")
	private Collection<Course> courses;

	public String getName() {
		return name;
	}

	public long getId() {
		return this.id;
	}

	public Collection<Course> getCourses() {
		return courses;
	}
	
	

	public Topic() {

	}

	public Topic(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
