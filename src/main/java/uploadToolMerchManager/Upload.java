package uploadToolMerchManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import javax.print.attribute.standard.Chromaticity;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Upload extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static Thread thread;
	
	public static JButton btnNewButton = new JButton("Upload");
	public static JTextField user;
	public static JTextField pass;
	public static JTextField pathExcel;
	public static File  file;
	public static JTextArea textArea;
	public static  List<uploadFile> lstFile;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Upload frame = new Upload();
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Upload() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
					
					btnNewButton.setEnabled(false);
			        thread = new Thread(new Runnable() {

			            public void run() {
			            UploadFunction function=new UploadFunction();
			            function.upload();
			            System.out.println("ok");
			            }
			        });
			        thread.start();
					
			}
		});
		btnNewButton.setBounds(171, 131, 110, 38);
		contentPane.add(btnNewButton);
		
		user = new JTextField();
		user.setBounds(141, 38, 267, 20);
		contentPane.add(user);
		user.setColumns(10);
		
		pass = new JTextField();
		pass.setBounds(141, 69, 267, 20);
		contentPane.add(pass);
		pass.setColumns(10);
		
		pathExcel = new JTextField();
		pathExcel.setColumns(10);
		pathExcel.setBounds(141, 100, 267, 20);
		contentPane.add(pathExcel);
		
		JLabel lblNewLabel = new JLabel("userName");
		lblNewLabel.setBounds(10, 41, 69, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPass = new JLabel("PassWord");
		lblPass.setBounds(10, 72, 46, 14);
		contentPane.add(lblPass);
		
		JButton btnNewButton_1 = new JButton("Ch\u1ECDn file excel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choose=new JFileChooser();
				FileNameExtensionFilter fileNameFilter=new FileNameExtensionFilter("Chon File","xlsx");
				choose.setFileFilter(fileNameFilter);
				int x=choose.showOpenDialog(null);
				if(x==JFileChooser.APPROVE_OPTION) {
					
					  file=choose.getSelectedFile();
					  pathExcel.setText(file.getAbsolutePath());
				
					
				}
			}
		});
		btnNewButton_1.setBounds(10, 99, 110, 23);
		contentPane.add(btnNewButton_1);
		
		 textArea = new JTextArea();
		textArea.setBounds(123, 178, 207, 59);
		contentPane.add(textArea);
	}
}
