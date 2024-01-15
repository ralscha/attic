package ch.ess.sandbox;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.Pattern;

@Embeddable
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String street;

	private String city;

	private String state;

	private String county;

	private String postalCode;

	private String country;

	@Column( name = "street" )
	@Length( max = 50 )
	public String getStreet() {
		return street;
	}

	public void setStreet( String street ) {
		this.street = street;
	}

	@Column( name = "city" )
	@Length( max = 30 )
	public String getCity() {
		return city;
	}

	public void setCity( String city ) {
		this.city = city;
	}

	@Column( name = "county" )
	@Length( max = 30 )
	public String getCounty() {
		return county;
	}

	public void setCounty( String county ) {
		this.county = county;
	}

	@Column( name = "state" )
	@Length( min = 2, max = 2 )
	public String getState() {
		return state;
	}

	public void setState( String state ) {
		this.state = state;
	}

	@Column( name = "postal_code" )
	@Length( min = 5, max = 5 )
	@Pattern( regex = "^\\d*$", message = "validator.custom.digits" )
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode( String postalCode ) {
		this.postalCode = postalCode;
	}

	@Column( name = "country" )
	@Length( max = 30 )
	public String getCountry() {
		return country;
	}

	public void setCountry( String country ) {
		this.country = country;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ( ( country == null ) ? 0 : country.hashCode() );
		result = PRIME * result + ( ( postalCode == null ) ? 0 : postalCode.hashCode() );
		result = PRIME * result + ( ( street == null ) ? 0 : street.hashCode() );
		return result;
	}

	public boolean equals( Object o ) {
		if ( this == o ) {
			return true;
		}
		
		if ( !( o instanceof Address ) ) {
			return false;
		}
		
		final Address other = (Address) o;
		if ( country == null ) {
			if ( other.country != null ) {
				return false;
			}
		}
		else if ( !country.equals( other.country ) ) {
			return false;
		}
		
		if ( postalCode == null ) {
			if ( other.postalCode != null ) {
				return false;
			}
		}
		else if ( !postalCode.equals( other.postalCode ) ) {
			return false;
		}
		
		if ( street == null ) {
			if ( other.street != null ) {
				return false;
			}
		}
		else if ( !street.equals( other.street ) ) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer( 50 );
		buf.append( street )
			.append( ", " )
			.append( city )
			.append( ", " )
			.append( state )
			.append( " " )
			.append( postalCode );
		return buf.toString();
	}

}
