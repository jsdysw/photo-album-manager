import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Photo {
	private String id;
	private String name;
	private String addTime;
	private String category;
	private String createdTime;
	private String filePath;

	Photo() {
		// generate ID
		//Create formatter
		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");
		LocalDateTime localDateTime = LocalDateTime.now();
		//Get formatted String
		addTime = localDateTime.format(FORMATTER);
		id = "IMG" + addTime;
	}
	// setter
	public void setName(String _name) {
		if (_name.equals("")) {
			name = "";
		} else {
			this.name = _name;
		}
	}
	public void setID(String _id) {
		id = _id;
	}
	public void setAddTime(String _addTime) {
		addTime = _addTime;
	}
	public void setCategory(String _category) {
		if (_category.equals("")) {
			category = "Not Classified";
		} else {
			category = _category;
		}
	}
	public void setFilePath(String _filePath) {
		filePath = _filePath;
	}
	public void setCreatedTime(String _createdTime) {
		if (_createdTime.equals("")) {
			createdTime = "xxxx-xx-xx_xx:xx:xx:xxx";
		} else {
			createdTime = _createdTime;
		}
	}
	// getter
	public String getPhotoId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public String getCategory() {
		return category;
	}
	public String getAddTime() {
		return addTime;
	}
	public String getPhotoInfo() {	
		String result = "";
		result += id + ";";
		result += name + ";";
		result += createdTime + ";";
		result += category + ";";
		result += filePath +";";
		return result;
	}
}
