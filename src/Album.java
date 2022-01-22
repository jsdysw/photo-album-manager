import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Album {
	private ArrayList<Photo> photosArr = new ArrayList<Photo>();
	private Photo selectedPhotoRef = null;
	private String photoInfofilePath;
	
	Album(String photoInfofilePath) {
		this.photoInfofilePath = photoInfofilePath;
		try {
			// For local test
			File file = new File("./images/" + photoInfofilePath);
			Scanner input = new Scanner(file);
			input.useDelimiter("\n");
			while (input.hasNext()) {
				// parse info from the line and make photo instance
				parsePhotoInfo(input.nextLine()); 
			}
			input.close();
		} catch (Exception e) {
		    System.out.println(e);
		    System.out.println("so file reading failed");
		}
		PhotoAlbumFrame albumFrame = new PhotoAlbumFrame(this);
		albumFrame.showFrame();
	}
	public void addPhoto(Photo p) {
		photosArr.add(p);
	}
	public void reRead() {
		// erase photosArr before reload
		this.photosArr = new ArrayList<Photo>();
		try {
			// For local test
			File file = new File("./images/" + this.photoInfofilePath);
			Scanner input = new Scanner(file);
			input.useDelimiter("\n");
			while (input.hasNext()) {
				// parse info from the line and make photo instance
				parsePhotoInfo(input.nextLine()); 
			}
			input.close();
		} catch (Exception e) {
		    System.out.println(e);
		    System.out.println("so file reading failed");
		}
	}
	public void save() {
		File file = new File("./images/" + this.photoInfofilePath);
		// re-write
	    try {
	    	FileWriter fw = new FileWriter(file, false);
	    	for (int i = 0; i<this.photosArr.size(); i++) {
		    	fw.write(this.photosArr.get(i).getPhotoInfo()+'\n');
	    	}
	    	fw.close();
	    } catch (Exception e) {
	    	System.out.println("Save failed");
	    }
	}
	public void eraseClickedPhoto() {
		int idx = -1;
		for (int i = 0 ; i<this.photosArr.size(); i++) {
			if (this.photosArr.get(i).getPhotoId() == this.selectedPhotoRef.getPhotoId()) {
				idx = i;
				break;
			}
		}
		this.photosArr.remove(idx);
	}
	private void parsePhotoInfo(String photoInfo) {
		// ignore comment or blank
		photoInfo = photoInfo.trim();
		if (photoInfo.isBlank() || photoInfo.indexOf("//") > -1) {
			return;
		}
		// split the line into segments
		String[] elements = photoInfo.split(";");
		// assign elements to photo member variables
		String id;
		String name;
		String category;
		String filePath;
		String createdTime;
		id = elements[0].trim();
		name = elements[1].trim();
		createdTime = elements[2].trim();
		category = elements[3].trim();
		filePath = elements[4].trim();
		
		// if line doesn't include filePath
		// wrong date format
		if (!isfilePathValid(filePath)) {
			System.out.println("No Image File  ; Skip the input line : " + photoInfo);
			return;
		} else if (!isDateFormatValid(createdTime)) {
			System.out.println("Wrong Date Format ; Skip the input line :" + photoInfo);
			return;
		}
		// Initialize photo instance
		Photo photo = new Photo();
		photo.setID(id);
		photo.setName(name);
		photo.setCategory(category);
		photo.setFilePath(filePath);
		photo.setCreatedTime(createdTime);
		// id conflict check
		if (!isPhotoIdOverlapped(id)) {
			photosArr.add(photo);
		} else {
			System.out.println("ID Conflict (a photo with the same ID already exists); Skip the input line: "  + photoInfo);
			return;
		}
		
	}
	private boolean isfilePathValid(String filePath) {
		if (filePath.isBlank()) {
			return false;
		}
		return true;
	}
	private boolean isDateFormatValid(String createdTime) {
		DateFormat strDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS");
        try {
        	strDateFormat.parse(createdTime);
        } catch (Exception e) {
            return false;
        }
        return true;
	}
	private boolean isPhotoIdOverlapped(String newId) {
		for (int i = 0; i<photosArr.size(); i++) {
			if (photosArr.get(i).getPhotoId().equals(newId)) {
				return true;
			}
		}
		return false;
	}
	public int numPhotos() {
		return photosArr.size();
	}
	public Photo getPhoto(int ith) {
		return photosArr.get(ith);
	}
	public void setSelectedPhotoRef(Photo ref) {
		this.selectedPhotoRef = ref;
	}
	public Photo getSelectedPhotoRef() {
		return this.selectedPhotoRef;
	}
	
	public void sortByCreatedTime() {
		Collections.sort(this.photosArr, new Comparator<Photo>() {
			@Override
			public int compare(Photo p1, Photo p2) {
				return p1.getCreatedTime().compareTo(p2.getCreatedTime());    
			}		
		});
	}
	
	public void sortByCategory() {
		Collections.sort(photosArr, new Comparator<Photo>() {
			@Override
			public int compare(Photo p1, Photo p2) {
				return p1.getCategory().compareTo(p2.getCategory());
			}
		});
		
	}
	
}
