import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PhotoInfoFrame extends JFrame {
	private JPanel topMenu = new JPanel(new FlowLayout());
	
	private JPanel pName = new JPanel(new GridLayout(2,0));
	private JLabel lName = new JLabel("Name");		
	
	private JPanel pAddedT = new JPanel(new GridLayout(2,0));
	private JLabel lAddedT = new JLabel("Added Time");
	
	private JPanel pCategory = new JPanel(new GridLayout(2,0));
	private JLabel lCategory = new JLabel("Category");
	
	private JPanel pCreatedT = new JPanel(new GridLayout(2,0));
	private JLabel lCreatedT = new JLabel("Created Time");

	private JPanel pFile = new JPanel(new GridLayout(2,0));
	private JLabel lFile = new JLabel("Image File");
	
	private JPanel pSelect = new JPanel(new GridLayout(2,0));
	private JLabel lSelect = new JLabel("Select");
	private JButton btSelect = new JButton("File");
	
	private JPanel bottomMenu = new JPanel(new FlowLayout());
	private JButton btCancel = new JButton("Cancel");
	private JButton btOK = new JButton("OK");
	
	PhotoInfoFrame(Photo onClickedPhoto, PhotoAlbumFrame albumFrame) {
		super("Photo Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(650,150);
		
		// Top menu Panel
		// Name Panel
		lName.setHorizontalAlignment(JLabel.CENTER);
		pName.add(lName);
		JTextField tName = new JTextField(5);
		pName.add(tName);
		pName.setPreferredSize(new Dimension(100,80));

		// Added Time Panel
		lAddedT.setHorizontalAlignment(JLabel.CENTER);
		pAddedT.add(lAddedT);
		pAddedT.setPreferredSize(new Dimension(100,80));

		// Category Panel
		lCategory.setHorizontalAlignment(JLabel.CENTER);
		pCategory.add(lCategory);
		JTextField tCategory = new JTextField(5);
		pCategory.add(tCategory);
		pCategory.setPreferredSize(new Dimension(100,80));

		// Created Time Panel
		lCreatedT.setHorizontalAlignment(JLabel.CENTER);
		pCreatedT.add(lCreatedT);
		JTextField tCreatedT = new JTextField(5);
		pCreatedT.add(tCreatedT);
		pCreatedT.setPreferredSize(new Dimension(100,80));

		// Image File Panel
		lFile.setHorizontalAlignment(JLabel.CENTER);
		pFile.add(lFile);
		JTextField tFile = new JTextField(5);
		pFile.add(tFile);
		pFile.setPreferredSize(new Dimension(100,80));

		// Select Panel
		lSelect.setHorizontalAlignment(JLabel.CENTER);
		pSelect.add(lSelect);
		pSelect.add(btSelect);
		pSelect.setPreferredSize(new Dimension(100,80));

		topMenu.add(pName);
		topMenu.add(pAddedT);
		topMenu.add(pCategory);
		topMenu.add(pCreatedT);
		topMenu.add(pFile);
		topMenu.add(pSelect);
		this.add(topMenu, BorderLayout.NORTH);

		// bottom menu Panel
		bottomMenu.add(btCancel);
		bottomMenu.add(btOK);
		this.add(bottomMenu, BorderLayout.SOUTH);
		
		// add listeners
		btSelect.addActionListener(
				new ActionListener() {
					JFileChooser chooser = new JFileChooser();
					public void actionPerformed(ActionEvent e) {			            
			            chooser.showOpenDialog(null);
			            String filePath = chooser.getSelectedFile().getPath();
				        tFile.setText(filePath);
					}
				}
		);
		btOK.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 // save photo
				        Photo newPhoto = new Photo();
				        newPhoto.setCategory(tCategory.getText());
				        newPhoto.setName(tName.getText());
				        newPhoto.setCreatedTime(tCreatedT.getText());
				        newPhoto.setFilePath(tFile.getText());
				        albumFrame.getAlbum().addPhoto(newPhoto);
						// re draw
				        if (albumFrame.getSortCriteria().equals("Date")) {
							albumFrame.loadImagesByCreatedTime();
						} else {
							albumFrame.loadImagesByCategory();
						}
						dispose();
					}
				}
		);
		btCancel.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				}
		);
	}
	public void showFrame() {
		this.setVisible(true);
	}
}
