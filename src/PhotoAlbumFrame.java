import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PhotoAlbumFrame extends JFrame {
	private Album album;
	private ImagePanel onClickedImgPanel = null;
	private String sortCriteria = "Date";
	
	private JPanel topMenu = new JPanel(new BorderLayout());
	private JButton btDate = new JButton("Date");
	private JButton btCategory = new JButton("Category");
	
	private JPanel content = null;
	
	private JPanel bottomMenu = new JPanel(new FlowLayout());
	private JButton btEdit = new JButton("EDIT");
	private JButton btDelete = new JButton("DELETE");
	private JButton btLoad = new JButton("LOAD");
	private JButton btAdd = new JButton("ADD");
	private JButton btSave = new JButton("SAVE");
		
	private class ImageGroupPanel extends JPanel {
		ImageGroupPanel(String name) {
			TitledBorder border = new TitledBorder(new LineBorder(Color.BLACK), name);
			this.setBorder(border);
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
		}
	}
	private class ImagePanel extends JPanel {		
		private Photo photoRef;
		ImagePanel(Photo photo) {
			this.photoRef = photo;
			JLabel image = new JLabel();
			image.setPreferredSize(new Dimension(60,60));
			image.setIcon(new ImageIcon(photo.getFilePath()));
			this.setLayout(new BorderLayout());
			this.add(image, BorderLayout.CENTER);
			JLabel label = new JLabel(photo.getName());
			label.setHorizontalAlignment(JLabel.CENTER);
			this.add(label, BorderLayout.SOUTH);
		}
		public Photo getPhotoRef() {
			return this.photoRef;
		}
	}

	PhotoAlbumFrame(Album photoAlbum) {
		// FhotoAlbumFrame
		super("Photo Album");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500,600);
		
		this.album = photoAlbum;

		// top menu Panel
		topMenu.add(btDate, BorderLayout.WEST);
		topMenu.add(btCategory, BorderLayout.EAST);

		// initial content
		loadImagesByCreatedTime();
		
		// bottom menu Panel
		bottomMenu.add(btEdit);
		bottomMenu.add(btAdd);
		bottomMenu.add(btDelete);
		bottomMenu.add(btLoad);
		bottomMenu.add(btSave);
		
		// FhotoAlbumFrame
		this.add(bottomMenu, BorderLayout.SOUTH);
		this.add(topMenu, BorderLayout.NORTH);

		PhotoAlbumFrame _thisFrame = this;
		// add listeners
		btEdit.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (album.getSelectedPhotoRef() == null) {
				            JOptionPane.showMessageDialog(null, "Click Photo!", "Alert", JOptionPane.WARNING_MESSAGE);
						} else {
							EditFrame frame = new EditFrame(album.getSelectedPhotoRef(), _thisFrame);
							frame.showFrame();
						}
					}
				}
		);
		btAdd.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PhotoInfoFrame frame = new PhotoInfoFrame(album.getSelectedPhotoRef(), _thisFrame);
						frame.showFrame();
					}
				}
		);
		btDate.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickedImgPanel = null;
						album.setSelectedPhotoRef(null);
						loadImagesByCreatedTime();
					}
				}
		);
		btCategory.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickedImgPanel = null;
						album.setSelectedPhotoRef(null);
						loadImagesByCategory();
					}
				}
		);
		btDelete.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (album.getSelectedPhotoRef() == null) {
							JOptionPane.showMessageDialog(null, "Click Photo!", "Alert", JOptionPane.WARNING_MESSAGE);
						} else {
							album.eraseClickedPhoto();
							onClickedImgPanel = null;
							album.setSelectedPhotoRef(null);
							if (sortCriteria.equals("Date")) {
								loadImagesByCreatedTime();
							} else {
								loadImagesByCategory();
							}
							JOptionPane.showMessageDialog(null, "Delete success!", "INFORMATION_MESSAGE", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
		);
		
		btLoad.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						album.reRead();
						if (sortCriteria.equals("Date")) {
							loadImagesByCreatedTime();
						} else {
							loadImagesByCategory();
						}
					}
				}
		);

		btSave.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						album.save();
						JOptionPane.showMessageDialog(null, "Save success!", "INFORMATION_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					}
				}
		);
	}	
	public void loadImagesByCreatedTime() {		
		if (this.content != null) {
			this.remove(content);
		}
		
		// create new content
		JPanel content = new JPanel(new GridLayout(4,0));

		// sort images by CreatedTime
		this.album.sortByCreatedTime();
		int j = 0;
		for (int i = 0; i<album.numPhotos(); i=j) {
			String tag = album.getPhoto(i).getCreatedTime().substring(0,10);
			ImageGroupPanel group = new ImageGroupPanel(tag);
			for (j = i; j<album.numPhotos(); j++) {
				if (tag.equals(album.getPhoto(j).getCreatedTime().substring(0,10))) {
					ImagePanel img = new ImagePanel(album.getPhoto(j));
					img.addMouseListener(
						new MouseAdapter() {
							private Color background;
							@Override
							public void mousePressed(MouseEvent e) {
								// clicked same image Panel
								if (onClickedImgPanel == e.getSource()) {
									return;
								}
								background = img.getBackground();
								if (onClickedImgPanel != null) {
									onClickedImgPanel.setBackground(background);
								}
								album.setSelectedPhotoRef(img.getPhotoRef());
								onClickedImgPanel = (PhotoAlbumFrame.ImagePanel) e.getSource();
								img.setBackground(Color.LIGHT_GRAY);
				                img.revalidate();
							}
						}
					);
					group.add(img);
				} else {
					break;
				}
			}
			content.add(group);
		}
		this.content = content;
		this.add(this.content, BorderLayout.CENTER);
		this.revalidate();
		this.sortCriteria = "Date";
	}
	public void loadImagesByCategory() {
		if (this.content != null) {
			this.remove(content);
		}
		// create new content
		JPanel content = new JPanel(new GridLayout(4,0));

		// sort images by category
		this.album.sortByCategory();
		
		int j = 0;
		for (int i = 0; i<album.numPhotos(); i=j) {
			String tag = album.getPhoto(i).getCategory();
			ImageGroupPanel group = new ImageGroupPanel(tag);
			for (j = i; j<album.numPhotos(); j++) {
				if (tag.equals(album.getPhoto(j).getCategory())) {
					ImagePanel img = new ImagePanel(album.getPhoto(j));
					img.addMouseListener(
						new MouseAdapter() {
							private Color background;
							@Override
							public void mousePressed(MouseEvent e) {
								// clicked same image Panel
								if (onClickedImgPanel == e.getSource()) {
									return;
								}
								background = img.getBackground();
								if (onClickedImgPanel != null) {
									onClickedImgPanel.setBackground(background);
								}
								album.setSelectedPhotoRef(img.getPhotoRef());
								onClickedImgPanel = (PhotoAlbumFrame.ImagePanel) e.getSource();
								img.setBackground(Color.LIGHT_GRAY);
				                img.revalidate();
							}
						}
					);
					group.add(img);
				} else {
					break;
				}
			}
			content.add(group);
		}
		this.content = content;
		this.add(this.content, BorderLayout.CENTER);
		this.revalidate();
		this.sortCriteria = "Category";
	}
	public String getSortCriteria() {
		return this.sortCriteria;
	}
	public void showFrame() {
		this.setVisible(true);
	}	
	public Album getAlbum() {
		return this.album;
	}
}

