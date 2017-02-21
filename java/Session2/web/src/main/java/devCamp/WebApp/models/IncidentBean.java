package devCamp.WebApp.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//import org.springframework.samples.mvc.convert.MaskFormat;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncidentBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8934737151319658838L;

	@JsonProperty("id")
	private String id;

	@JsonProperty("Title")
	private String Title;
	
	@JsonProperty("Description")
	private String Description;

	@JsonProperty("Street")
	private String Street;

	@JsonProperty("City")
	private String City;

	@JsonProperty("State")
	private String State;

	@JsonProperty("ZipCode")
	private String ZipCode;

	@NotEmpty
	@JsonProperty("FirstName")
	private String FirstName;

	@NotEmpty
	@JsonProperty("LastName")
	private String LastName;

//	@MaskFormat("(###) ###-####")
	@JsonProperty("PhoneNumber")
	private String PhoneNumber;

	@JsonProperty("OutageType")
	private String OutageType;

	@JsonProperty("IsEmergency")
	private boolean IsEmergency;

	@JsonProperty("Resolved")
	private boolean Resolved;

	@JsonProperty("ImageUri")
	private String ImageUri;

	@JsonProperty("ThumbnailUri")
	private String ThumbnailUri;

	@JsonProperty("Created")
	private String Created;

	@JsonProperty("LastModified")
	private String LastModified;

	@JsonProperty("SortKey")
	private String SortKey;



	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("properties ");
        sb.append("id=").append(id).append(", ");
        sb.append("title=").append(Title).append(", ");
        sb.append("description=").append(Description).append(", ");
        sb.append("Street=").append(Street).append(", ");
        sb.append("City=").append(City).append(", ");
        sb.append("State=").append(State).append(", ");
        sb.append("ZipCode=").append(ZipCode).append(", ");
        sb.append("FirstName=").append(FirstName).append(", ");
        sb.append("LastName=").append(LastName).append(", ");
        sb.append("PhoneNumber=").append(PhoneNumber).append(", ");
        sb.append("OutageType=").append(OutageType).append(", ");
        sb.append("IsEmergency=").append(IsEmergency).append(", ");
        sb.append("Resolved=").append(Resolved).append(", ");
        sb.append("ImageUri=").append(ImageUri).append(", ");
        sb.append("ThumbnailUri=").append(ThumbnailUri).append(", ");
        sb.append("Created=").append(Created).append(", ");
        sb.append("LastModified=").append(LastModified).append(", ");
        sb.append("SortKey=").append(SortKey).append(", ");

		return sb.toString();
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}



	public String getDescription() {
		return Description;
	}



	public void setDescription(String description) {
		Description = description;
	}



	public String getStreet() {
		return Street;
	}



	public void setStreet(String street) {
		Street = street;
	}



	public String getCity() {
		return City;
	}



	public void setCity(String city) {
		City = city;
	}



	public String getState() {
		return State;
	}



	public void setState(String state) {
		State = state;
	}



	public String getZipCode() {
		return ZipCode;
	}



	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}



	public String getFirstName() {
		return FirstName;
	}



	public void setFirstName(String firstName) {
		FirstName = firstName;
	}



	public String getLastName() {
		return LastName;
	}



	public void setLastName(String lastName) {
		LastName = lastName;
	}



	public String getPhoneNumber() {
		return PhoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}



	public String getOutageType() {
		return OutageType;
	}



	public void setOutageType(String outageType) {
		OutageType = outageType;
	}



	public boolean isIsEmergency() {
		return IsEmergency;
	}

	public boolean getIsEmergency() {
		return IsEmergency;
	}

	public void setIsEmergency(boolean isEmergency) {
		IsEmergency = isEmergency;
	}



	public boolean isResolved() {
		return Resolved;
	}



	public void setResolved(boolean resolved) {
		Resolved = resolved;
	}



	public String getImageUri() {
		return ImageUri;
	}



	public void setImageUri(String imageUri) {
		ImageUri = imageUri;
	}



	public String getThumbnailUri() {
		return ThumbnailUri;
	}



	public void setThumbnailUri(String thumbnailUri) {
		ThumbnailUri = thumbnailUri;
	}



	public String getCreated() {
		return Created;
	}



	public void setCreated(String created) {
		Created = created;
	}



	public String getLastModified() {
		return LastModified;
	}



	public void setLastModified(String lastModified) {
		LastModified = lastModified;
	}



	public String getSortKey() {
		return SortKey;
	}



	public void setSortKey(String sortKey) {
		SortKey = sortKey;
	}

	static public IncidentBean getDemoIncident() {
    	IncidentBean incident = new IncidentBean();

    	incident.setDescription("description");
    	incident.setStreet("the Street");
    	incident.setCity("the City");
    	incident.setState("CO");
    	incident.setZipCode("00000");
    	incident.setFirstName("firstname");
    	incident.setLastName("lastname");
    	incident.setPhoneNumber("303-555-1212");
    	incident.setOutageType("outageType");
    	incident.setIsEmergency(false);
    	incident.setResolved(true);
    	return incident;
	}
}
