package au.twc.core.product.dto;

import au.twc.core.product.domain.enumeration.AgeGroup;
import au.twc.core.product.domain.enumeration.Gender;

import java.io.Serializable;

/**
 * A Audience Dto.
 */
public class AudienceDto implements Serializable {

    private static final long serialVersionUID = 5781879491026837386L;

    private String id;

    private String name;

    private Integer suggestedMinAge;

    private Integer suggestedMaxAge;

    private Gender suggestedGender;

    private AgeGroup ageGroup;

    public String getId() {
        return id;
    }

    public AudienceDto id(String id){
        this.id = id;
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AudienceDto name(String name){
        this.name = name;
        return this;

    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getSuggestedMinAge() {
        return suggestedMinAge;
    }

    public AudienceDto suggestedMinAge(Integer suggestedMinAge) {
        this.suggestedMinAge = suggestedMinAge;
        return this;
    }

    public void setSuggestedMinAge(Integer suggestedMinAge) {
        this.suggestedMinAge = suggestedMinAge;
    }

    public Integer getSuggestedMaxAge() {
        return suggestedMaxAge;
    }

    public AudienceDto suggestedMaxAge(Integer suggestedMaxAge) {
        this.suggestedMaxAge = suggestedMaxAge;
        return this;
    }

    public void setSuggestedMaxAge(Integer suggestedMaxAge) {
        this.suggestedMaxAge = suggestedMaxAge;
    }

    public Gender getSuggestedGender() {
        return suggestedGender;
    }

    public AudienceDto suggestedGender(Gender suggestedGender) {
        this.suggestedGender = suggestedGender;
        return this;
    }

    public void setSuggestedGender(Gender suggestedGender) {
        this.suggestedGender = suggestedGender;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public AudienceDto ageGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AudienceDto)) {
            return false;
        }
        return id != null && id.equals(((AudienceDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AudienceDto{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", suggestedMinAge=" + getSuggestedMinAge() +
            ", suggestedMaxAge=" + getSuggestedMaxAge() +
            ", suggestedGender='" + getSuggestedGender() + "'" +
            ", ageGroup='" + getAgeGroup() + "'" +
            "}";
    }
}
