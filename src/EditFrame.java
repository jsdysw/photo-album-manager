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

public class EditFrame extends JFrame {
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
	
	private JPanel bottomMenu = new JPanel(new FlowLayout());
	private JButton btCancel = new JButton("Cancel");
	private JButton btOK = new JButton("OK");
	
	EditFrame(Photo onClickedPhoto, PhotoAlbumFrame albumFrame) {
		super("Photo Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(650,150);
		
		// Top menu Panel
		// Name Panel
		lName.setHorizontalAlignment(JLabel.CENTER);
		pName.add(lName);
		JTextField tName = new JTextField(onClickedPhoto.getName());
		pName.add(tName);
		pName.setPreferredSize(new Dimension(100,80));

		// Added Time Panel
		lAddedT.setHorizontalAlignment(JLabel.CENTER);
		pAddedT.add(lAddedT);
		pAddedT.setPreferredSize(new Dimension(100,80));

		// Category Panel
		lCategory.setHorizontalAlignment(JLabel.CENTER);
		pCategory.add(lCategory);
		JTextField tCategory = new JTextField(onClickedPhoto.getCategory());
		pCategory.add(tCategory);
		pCategory.setPreferredSize(new Dimension(100,80));

		// Created Time Panel
		lCreatedT.setHorizontalAlignment(JLabel.CENTER);
		pCreatedT.add(lCreatedT);
		JTextField tCreatedT = new JTextField(onClickedPhoto.getCreatedTime());
		pCreatedT.add(tCreatedT);
		pCreatedT.setPreferredSize(new Dimension(100,80));

		// Image File Panel
		lFile.setHorizontalAlignment(JLabel.CENTER);
		pFile.add(lFile);
		JTextField tFile = new JTextField(onClickedPhoto.getFilePath());
		pFile.add(tFile);
		pFile.setPreferredSize(new Dimension(100,80));

		topMenu.add(pName);
		topMenu.add(pAddedT);
		topMenu.add(pCategory);
		topMenu.add(pCreatedT);
		topMenu.add(pFile);
		this.add(topMenu, BorderLayout.NORTH);

		// bottom menu Panel
		bottomMenu.add(btCancel);
		bottomMenu.add(btOK);
		this.add(bottomMenu, BorderLayout.SOUTH);
		
		// add listeners
		btCancel.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				}
		);
		btOK.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickedPhoto.setCategory(tCategory.getText());
						onClickedPhoto.setName(tName.getText());
						onClickedPhoto.setCreatedTime(tCreatedT.getText());
						onClickedPhoto.setFilePath(tFile.getText());
						if (albumFrame.getSortCriteria().equals("Date")) {
							albumFrame.loadImagesByCreatedTime();
						} else {
							albumFrame.loadImagesByCategory();
						}
						dispose();
					}
				}
		);
	}
	
	public void showFrame() {
		this.setVisible(true);
	}
}
