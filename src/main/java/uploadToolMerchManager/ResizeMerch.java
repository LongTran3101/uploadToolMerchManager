package uploadToolMerchManager;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.imgscalr.Scalr;

import com.google.gson.Gson;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ResizeMerch extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public static File[] listFileChoose;
	public static JTextArea textArea = new JTextArea(20,20);
	private JTextField linkSaveFile;
	private static JTextField width;
	private static JTextField hight;
	private JTextField tileresize;
	private static Thread thread;
	private static Thread threadMain;
	static	JTextField userName = new JTextField();
	private static JButton  btnNewButton = new JButton("Resize");
	static JTextArea textArea_1 = new JTextArea();
	static JTextArea textArea_Loi =new JTextArea(20, 20);
	static JButton btnUploadfile = new JButton("UploadFile");
	public static void main(String[] args) {
		threadMain = new Thread(new Runnable() {

			public void run() {
				try {
					System.out.println("1");
					ResizeMerch frame = new ResizeMerch();
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					frame.addWindowListener(new WindowAdapter() {
	                    @Override
	                    public void windowClosing(WindowEvent e) {
	                        System.out.println("WindowClosingDemo.windowClosing");
	                     
	                        if (thread != null) {
	                            try {
	                            	thread.interrupt();
	                            	//thread.suspend();;
	                            } catch (Exception er) {
	                               
	                            }
	                           
	                        } 
//	                        if (threadMain != null) {
//	                            try {
//	                            	threadMain.interrupt();
//	                            	//thread.suspend();;
//	                            } catch (Exception er) {
//	                               
//	                            }
//	                           
//	                        } 

	                    }

	                    @Override
	                    public void windowOpened(WindowEvent e) {

	                       
	                    }
	                });
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        });
		threadMain.start();
	
	}
	
	
	

	/**
	 * Create the frame.
	 */
	public ResizeMerch() {
		setBounds(100, 100, 1275, 907);
		getContentPane().setLayout(null);
		
		JButton btnChnnh = new JButton("Chọn ảnh");
		btnChnnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				JFileChooser choose=new JFileChooser();
				FileNameExtensionFilter fileNameFilter=new FileNameExtensionFilter("Hình ảnh","png");
				choose.setFileFilter(fileNameFilter);
				choose.setMultiSelectionEnabled(true);
				int x=choose.showOpenDialog(null);
				if(x==JFileChooser.APPROVE_OPTION) {
					
					File[]  files=choose.getSelectedFiles();
					listFileChoose=files;
					  //StringBuilder content = new StringBuilder();
					for (File file : files) {
						//content.append(file.getName()  + "\n");
						textArea.append(file.getName() +"\n");
						 System.out.println("You chose to open this file: " +
								 file.getAbsolutePath());
					}
					
				}
			}
		});
		btnChnnh.setBounds(0, 12, 117, 23);
		getContentPane().add(btnChnnh);

		
		
		
		JButton btnChnThMc = new JButton("Chọn thư mục lưu");
		btnChnThMc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser	chooser = new JFileChooser();
		        chooser.setCurrentDirectory(new java.io.File("."));
		        chooser.setDialogTitle("Chọn thư mục lưu");
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        //
		        // disable the "All files" option.
		        //
		        chooser.setAcceptAllFileFilterUsed(false);
		        //    
		        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

		           String stringUrlSave = chooser.getSelectedFile().toString();
		            linkSaveFile.setText(stringUrlSave + "\\");

		        } else {
		            System.out.println("No Selection ");
		        }

			}
		});
		btnChnThMc.setBounds(0, 48, 273, 23);
		getContentPane().add(btnChnThMc);
		
		linkSaveFile = new JTextField();
		linkSaveFile.setBounds(0, 82, 332, 20);
		getContentPane().add(linkSaveFile);
		linkSaveFile.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(345, 9, 433, 445);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		textArea.setEditable(false);
		
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnNewButton.setEnabled(false);
		        thread = new Thread(new Runnable() {

		            public void run() {
		                download();

		            }
		        });
		        thread.start();
				
				
				
			}
		});
		btnNewButton.setBounds(80, 181, 156, 46);
		getContentPane().add(btnNewButton);
		
	
		textArea_1.setBounds(24, 483, 754, 85);
		getContentPane().add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("Width");
		lblNewLabel.setBounds(10, 113, 46, 14);
		getContentPane().add(lblNewLabel);
		
		width = new JTextField();
		width.setText("4500");
		width.setBounds(70, 110, 86, 20);
		getContentPane().add(width);
		width.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(174, 116, 46, 14);
		getContentPane().add(lblHeight);
		
		hight = new JTextField();
		hight.setText("5400");
		hight.setColumns(10);
		hight.setBounds(230, 110, 86, 23);
		getContentPane().add(hight);
		
		JLabel lblTLResize = new JLabel("Tỉ lệ resize");
		lblTLResize.setBounds(10, 156, 84, 14);
		getContentPane().add(lblTLResize);
		
		tileresize = new JTextField();
		tileresize.setText("90");
		tileresize.setColumns(10);
		tileresize.setBounds(70, 153, 86, 20);
		getContentPane().add(tileresize);
		
		JLabel lblNewLabel_1 = new JLabel("userName");
		lblNewLabel_1.setBounds(10, 247, 84, 14);
		getContentPane().add(lblNewLabel_1);
		
		
		userName.setText("longtn");
		userName.setColumns(10);
		userName.setBounds(141, 244, 137, 20);
		getContentPane().add(userName);
		
	
		btnUploadfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					
				btnUploadfile.setEnabled(false);
			        thread = new Thread(new Runnable() {

			            public void run() {
			            	uploadFile();

			            }
			        });
			        thread.start();
					
					
					
				}
			
		});
		btnUploadfile.setBounds(80, 318, 156, 23);
		getContentPane().add(btnUploadfile);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(812, 48, 404, 669);
		getContentPane().add(scrollPane_1);
		
		scrollPane_1.setViewportView(textArea_Loi);
		textArea_Loi.setEditable(false);
		
		JLabel lblLi = new JLabel("Lỗi");
		lblLi.setBounds(904, 16, 46, 14);
		getContentPane().add(lblLi);

	}
	public static void uploadFile() {
		 int thanhcong=0;
		 int thatbai=0;
		 try {
			 textArea_1.setText("Start!");
			 int i=1;
			 int tong=listFileChoose.length;
			
			 for (File file : listFileChoose) {
				 try {
					 byte[] fileContent = FileUtils.readFileToByteArray(file);
						String encodedString = Base64.getEncoder().encodeToString(fileContent);
						Image img=new Image();
						img.setName(file.getName());
						img.setUsernam(userName.getText());
						img.setBaseFile(encodedString);
						textArea_1.setText(i+"/"+tong+"\n");
						textArea_1.append(file.getName());
						Gson gson = new Gson();
						String json = gson.toJson(img);
						String a=callAPIPost("http://45.32.101.196:8080/uploadMultifileExcel",json);
						
						if (a==null || !a.equalsIgnoreCase("ok"))
						{
							thatbai++;
							textArea_Loi.append(file.getName() +"\n");
						}else {
							thanhcong++;
						}
						i++;
				} catch (Exception e2) {
					continue;
				}
				 
				
		       
		        
		      } 
		      //System.out.println(a.size());
			 btnNewButton.setEnabled(true);
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } 
		 btnUploadfile.setEnabled(true);
		 textArea_1.setText("thanhcong: " + thanhcong+" ---  That bai:  "+thatbai+"\n");
		 textArea_1.append("Done!");
	}
	 
	  private static BufferedImage trimImage(BufferedImage image) {
	    try {
	      WritableRaster raster = image.getAlphaRaster();
	      int width = raster.getWidth();
	      int height = raster.getHeight();
	      int left = 0;
	      int top = 0;
	      int right = width - 1;
	      int bottom = height - 1;
	      int minRight = width - 1;
	      int minBottom = height - 1;
	      label53: for (; top < bottom; top++) {
	        for (int x = 0; x < width; x++) {
	          if (raster.getSample(x, top, 0) != 0) {
	            minRight = x;
	            minBottom = top;
	            break label53;
	          } 
	        } 
	      } 
	      label54: for (; left < minRight; left++) {
	        for (int y = height - 1; y > top; y--) {
	          if (raster.getSample(left, y, 0) != 0) {
	            minBottom = y;
	            break label54;
	          } 
	        } 
	      } 
	      label55: for (; bottom > minBottom; bottom--) {
	        for (int x = width - 1; x >= left; x--) {
	          if (raster.getSample(x, bottom, 0) != 0) {
	            minRight = x;
	            break label55;
	          } 
	        } 
	      } 
	      label52: for (; right > minRight; right--) {
	        for (int y = bottom; y >= top; y--) {
	          if (raster.getSample(right, y, 0) != 0)
	            break label52; 
	        } 
	      } 
	      return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
	    } catch (Exception e) {
	      return image;
	    } 
	  }
	  
	  private  void download() {
			 try {
				 textArea_1.setText("Start!");
				 for (File file : listFileChoose) {
					 try {
						 byte[] response = Files.readAllBytes(file.toPath());
					        int newHeight = Integer.parseInt(hight.getText());
					        int newWidth = Integer.parseInt(width.getText());
					        int newHeightresize = Integer.parseInt(hight.getText()) * Integer.parseInt(tileresize.getText()) / 100;
					        int newWidthresize = Integer.parseInt(width.getText()) * Integer.parseInt(tileresize.getText()) / 100;
					        ByteArrayInputStream bais = new ByteArrayInputStream(response);
					        BufferedImage inputimage = ImageIO.read(bais);
					        BufferedImage outputImage = trimImage(inputimage);
					        Scalr.Mode mode = Scalr.Mode.FIT_TO_HEIGHT;
					        BufferedImage outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS );
					        if (outputImage2.getWidth() > newWidth) {
					          Scalr.Mode mode2 = Scalr.Mode.FIT_TO_WIDTH;
					          outputImage2 = Scalr.resize(outputImage, Scalr.Method.ULTRA_QUALITY, mode2, newWidthresize - 10, newHeightresize - 10, Scalr.OP_ANTIALIAS );
					          int i = 150;
					          int j = Math.round(((newWidth - outputImage2.getWidth()) / 2));
					          int k = 2;
					          BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, k);
					          Graphics2D graphics2D1 = bufferedImage.createGraphics();
					          RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					          renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					          renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					          graphics2D1.setRenderingHints(renderingHints);
					          graphics2D1.drawImage(outputImage2, j, i, null);
					          graphics2D1.dispose();
					          ImageIO.write(bufferedImage, "png", new File(linkSaveFile.getText() + file.getName()));
					          continue;
					        } 
					        int hightwirte = 150;
					        int widthwirte = Math.round(((newWidth - outputImage2.getWidth()) / 2));
					        int type = 2;
					        BufferedImage outputImage4 = new BufferedImage(newWidth, newHeight, type);
					        Graphics2D graphics2D = outputImage4.createGraphics();
					        RenderingHints hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					        graphics2D.setRenderingHints(hints);
					        graphics2D.drawImage(outputImage2, widthwirte, hightwirte,null);
					        graphics2D.dispose();
					        ImageIO.write(outputImage4, "png", new File(linkSaveFile.getText() +file.getName()));
					} catch (Exception e2) {
						continue;
					}
					 
					
			       
			        
			      } 
			      //System.out.println(a.size());
				 btnNewButton.setEnabled(true);
			    } catch (Exception ex) {
			      ex.printStackTrace();
			    } 
			 btnNewButton.setEnabled(true);
			 textArea_1.setText("Done!");
		}
	  
	  public static String callAPIPost(String completeUrl, String body) {
		    try {
		        int CONNECTION_TIMEOUT_MS = 30000;
		        int LATENT_CONNECTION_TIMEOUT_MS = 60000;

		        CloseableHttpClient client = HttpClientBuilder.create().build();
		        RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(CONNECTION_TIMEOUT_MS))
		                .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECTION_TIMEOUT_MS)).build();
		        HttpPost httppost = new HttpPost(completeUrl);
		        try {
		            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
		            httppost.setHeader("Authorization", "Bearer ");
		            httppost.setConfig(config);
		            StringEntity stringEntity = new StringEntity(body);
		            httppost.getRequestUri();
		            httppost.setEntity(stringEntity);
		        } catch (Exception e) {

		        }
		        CloseableHttpResponse response = client.execute(httppost);
		        if (response.getCode() >= 300) {
		            throw new IOException("False");
		        }
		        HttpEntity entity = response.getEntity();

		        // StringWriter writer = new StringWriter();
		        // IOUtils.copy(entity.getContent(), writer);
		        return EntityUtils.toString(entity, "UTF-8");
		    } catch (Exception e) {
		    }

		    return null;
		}
}
