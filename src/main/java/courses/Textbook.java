package courses;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Textbook {

	@Id
	@GeneratedValue
	private long Id;
	
	private String title;

	@ManyToOne
	private Course course;  

	public long getId() {
		return this.Id;
	}
	
	public String getTitle() {
		return title;
	}

	public Course getCourse() {
		return course;
	}

	public Textbook() {
		
	}
	
	public Textbook(String title, Course course) {
		this.title = title;
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (Id ^ (Id >>> 32));
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
		Textbook other = (Textbook) obj;
		if (Id != other.Id)
			return false;
		return true;
	}


}
