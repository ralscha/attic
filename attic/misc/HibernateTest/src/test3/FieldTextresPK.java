package test3;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class FieldTextresPK implements Serializable {
    
    private Field fieldId;
    private Language langId;
    
    
    @ManyToOne 
    @JoinColumn(name = "field_id", nullable = false)
    public Field getFieldId() {
        return fieldId;
    }

    public void setFieldId(Field fieldId) {
        this.fieldId = fieldId;
    }


    @ManyToOne
    @JoinColumn(name = "lang_id", nullable = false)
    public Language getLangId() {
        return langId;
    }

    public void setLangId(Language langId) {
        this.langId = langId;
    }
}
