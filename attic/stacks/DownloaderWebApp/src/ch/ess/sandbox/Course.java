package ch.ess.sandbox;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

@Entity
@Table( name = "course" )
public class Course implements Comparable<Course>, Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private CourseType type = CourseType.PUBLIC;

	private Address address;
	
	private Point point;
	
	private String uri;

	private String phoneNumber;

	private String description;

	public Course() {}

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	@NotNull
	public long getId() {
		return this.id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	@Column( name = "name" )
	@NotNull
	@Length( min = 1, max = 50 )
	public String getName() {
		return this.name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Column( name = "type" )
	@Enumerated(EnumType.STRING)
	@NotNull
	public CourseType getType() {
		return type;
	}

	public void setType( CourseType type ) {
		this.type = type;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}
	
	public void setAddress( Address address ) {
		this.address = address;
	}
	
	@Embedded
	public Point getPoint() {
		return point;
	}

	public void setPoint( Point point ) {
		this.point = point;
	}

	@Column( name = "uri" )
	@Length( max = 255 )
	@Pattern( regex = "^https?://.+$", message = "validator.custom.url" )
	public String getUri() {
		return this.uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
	}

	@Column( name = "phone" )
	@Length( min = 10, max = 10 )
	@Pattern( regex = "^\\d*$", message = "validator.custom.digits" )
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber( String phoneNumber ) {
		this.phoneNumber = phoneNumber;
	}

	@Column( name = "description" )
	public String getDescription() {
		return this.description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public int compareTo( Course o ) {
		return ObjectUtils.toString( this.getName() ).compareTo( ObjectUtils.toString( o.getName() ) );
	}
	
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( !( o instanceof Course ) ) {
            return false;
        }

        final Course other = (Course) o;

        // short circuit by comparing ids
        if ( this.id > 0 && other.getId() > 0 ) {
        	return this.id == other.getId();
        }
        
        if ( this.name == null ) {
        	if ( other.getName() != null ) {
        		return false;
        	}
        }
        else if ( !this.name.equals( other.getName() ) ) {
        	return false;
        }
        
        if ( this.address == null ) {
        	if ( other.getAddress() != null ) {
        		return false;
        	}
        }
        else if ( !this.address.equals( other.getAddress() ) ) {
        	return false;
        }
        
        return true;
    }

    public int hashCode() {
    	final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ( name != null ? name.hashCode() : 0 );
        result = PRIME * result + ( address != null ? address.hashCode() : 0 );
    	return result;
    }
	
    public String toString() {
        return new ToStringBuilder( this, ToStringStyle.DEFAULT_STYLE ).
        	append( "id", id ).
            append( "name", name ).
            toString();
    }
	
}
