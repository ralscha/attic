
package ch.ech.xmlns.ech_0071._1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.ech.xmlns.ech_0071._1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.ech.xmlns.ech_0071._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Nomenclature }
     * 
     */
    public Nomenclature createNomenclature() {
        return new Nomenclature();
    }

    /**
     * Create an instance of {@link Nomenclature.Cantons }
     * 
     */
    public Nomenclature.Cantons createNomenclatureCantons() {
        return new Nomenclature.Cantons();
    }

    /**
     * Create an instance of {@link Nomenclature.Districts }
     * 
     */
    public Nomenclature.Districts createNomenclatureDistricts() {
        return new Nomenclature.Districts();
    }

    /**
     * Create an instance of {@link Nomenclature.Municipalities }
     * 
     */
    public Nomenclature.Municipalities createNomenclatureMunicipalities() {
        return new Nomenclature.Municipalities();
    }

    /**
     * Create an instance of {@link CantonType }
     * 
     */
    public CantonType createCantonType() {
        return new CantonType();
    }

    /**
     * Create an instance of {@link DistrictType }
     * 
     */
    public DistrictType createDistrictType() {
        return new DistrictType();
    }

    /**
     * Create an instance of {@link MunicipalityType }
     * 
     */
    public MunicipalityType createMunicipalityType() {
        return new MunicipalityType();
    }

}
