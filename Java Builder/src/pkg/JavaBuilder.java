/**
 * 
 */
package pkg;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
/**
 * @author Ankur
 *
 */
public class JavaBuilder implements ActionListener
{
	JFrame f;
	JMenuBar jmb;
	JMenu fle,edt,bld;
	JMenuItem nw,opn,sv,sva,ext,ct,cpy,pst,fnd,rep,cmp,run,cls;
	JFileChooser jfc;
	JTabbedPane jtb;
	JDialog jd;
	JPanel jp;
	JPanel jx;
	JTextArea lx;
	JTextArea tx;
	JScrollPane sx;
	JSplitPane sp;
	File dir;
	String fname,path;
	String title;
	boolean isSaved;
	int i=0,a=0;
	int j=0,y=-1;
	public JavaBuilder()
	{
		f=new JFrame();
		nw=new JMenuItem("New");
		opn=new JMenuItem("Open");
		sv=new JMenuItem("Save");
		sva=new JMenuItem("Save As");
		ext=new JMenuItem("Exit");
		cls=new JMenuItem("Close Current Tab");
		fle=new JMenu("File");
		fle.add(nw);
		fle.add(opn);
		fle.add(sv);
		fle.add(sva);
		fle.addSeparator();
		fle.add(cls);
		fle.add(ext);
		sv.setEnabled(false);
		sva.setEnabled(false);
		nw.addActionListener(this);
		opn.addActionListener(this);
		sv.addActionListener(this);
		sva.addActionListener(this);
		cls.addActionListener(this);
		ext.addActionListener(this);
		
		ct=new JMenuItem("Cut");
		cpy=new JMenuItem("Copy");
		pst=new JMenuItem("Paste");
		fnd=new JMenuItem("Find");
		rep=new JMenuItem("Find & Replace");
		edt=new JMenu("Edit");
		edt.add(ct);
		edt.add(cpy);
		edt.add(pst);
		edt.addSeparator();
		edt.add(fnd);
		edt.add(rep);
		ct.addActionListener(this);
		cpy.addActionListener(this);
		pst.addActionListener(this);
		fnd.addActionListener(this);
		rep.addActionListener(this);
		
		cmp=new JMenuItem("Compile");
		run=new JMenuItem("Run");
		bld=new JMenu("Build");
		bld.add(cmp);
		bld.add(run);
		cmp.addActionListener(this);
		run.addActionListener(this);
		
		jmb=new JMenuBar();
		jmb.add(fle);
		jmb.add(edt);
		jmb.add(bld);
		
		jfc=new JFileChooser();
		
		jtb=new JTabbedPane();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jx=new JPanel();
		jx.setName("Console");
		jx.setLayout(new BorderLayout());
		lx=new JTextArea();
		jx.add(lx);
		f.setJMenuBar(jmb);
		jtb.setMinimumSize(new Dimension(800,450));
	
		
		bld.setEnabled(false);
		edt.setEnabled(false);
		cls.setEnabled(false);
		
		jtb.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
					if(jtb.getTabCount()>0)
					{
						sx=(JScrollPane)jtb.getComponentAt(jtb.getSelectedIndex());
						tx=(JTextArea)sx.getViewport().getView();
						tx.addKeyListener(new KeyListener()
						{
		
							@Override
							public void keyPressed(KeyEvent arg0) {
								// TODO Auto-generated method stub
								sv.setEnabled(true);
							}
		
							@Override
							public void keyReleased(KeyEvent arg0) {
								// TODO Auto-generated method stub
								
							}
		
							@Override
							public void keyTyped(KeyEvent arg0) {
								// TODO Auto-generated method stub
								
							}
						});
	
					title=jtb.getTitleAt(jtb.getSelectedIndex());
					System.out.println(title);
					if(title.startsWith("Untitled"))
						isSaved=false;
					else
						isSaved=true;
					if(isSaved)
					{
						System.out.println("Build enabled");
						bld.setEnabled(true);
					}
					else
					{
						bld.setEnabled(false);
					}
				
				}
				else
				{
					edt.setEnabled(false);
					bld.setEnabled(false);
					sv.setEnabled(false);
					sva.setEnabled(false);
					cls.setEnabled(false);
				}
			}
		});
		sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jtb,jx);
		f.add(sp);
		f.setBounds(300,300,800,650);
		f.setVisible(true);
	}
	private void finder()
	{
		final JFrame jf=new JFrame("Find");
		final JTextField tf=new JTextField(tx.getSelectedText());
		JButton fd=new JButton("Find Next");
		fd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Pattern p=Pattern.compile(tf.getText());
				Matcher m=p.matcher(tx.getText());
				int b=0;
				while(m.find())
				{	
					if(b==a)
					{
						tx.select(m.start(), m.end());
					}
					b++;
				}
				if(a<b)
					a++;
				
			}
		});
		JButton cl=new JButton("Close");
		cl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jf.setVisible(false);
				jf.dispose();
				a=0;
			}
		});
		jf.add(tf,BorderLayout.NORTH);
		JPanel jp=new JPanel();
		jp.setLayout(new FlowLayout());
		jp.add(fd);
		jp.add(cl);
		jf.add(jp,BorderLayout.CENTER);
		jf.setBounds(100, 100, 300, 250);
		jf.setResizable(false);
		jf.setVisible(true);
	}
	private void replacer()
	{
		final JFrame jf=new JFrame("Find & Replace");
		JPanel p4=new JPanel();
		p4.setLayout(new FlowLayout());
		JButton fn=new JButton("Find Next");
		JButton rp=new JButton("Replace");
		JButton rpa=new JButton("Replace All");
		JButton cl=new JButton("Close");
		p4.add(fn);
		p4.add(rp);
		p4.add(rpa);
		p4.add(cl);
		
		JPanel p3=new JPanel(new BorderLayout());
		final JTextField tf2=new JTextField();
		p3.add(tf2,BorderLayout.NORTH);
		p3.add(p4);
		
		JPanel p2=new JPanel(new BorderLayout());
		p2.add(new JLabel("Replace With"),BorderLayout.NORTH);
		p2.add(p3);
		
		JPanel p1=new JPanel(new BorderLayout());
		final JTextField tf1=new JTextField();
		p1.add(tf1,BorderLayout.NORTH);
		p1.add(p2);
		jf.add(new JLabel("Find"),BorderLayout.NORTH);
		jf.add(p1);
		jf.setBounds(200, 150, 400, 150);
		jf.setVisible(true);
		fn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Pattern p=Pattern.compile(tf1.getText());
				Matcher m=p.matcher(tx.getText());
				int b=0;
				while(m.find())
				{	
					if(b==j)
					{
						tx.select(m.start(), m.end());
						y=j;
					}
					b++;
				}
				if(j<b)
					j++;
				if(j==b)
					j=0;
				
			}
		});
		rp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Pattern p=Pattern.compile(tf1.getText());
				Matcher m=p.matcher(tx.getText());
				int b=0;
				while(m.find())
				{	
					if(b==y)
					{
						tx.select(m.start(), m.end());
						tx.replaceSelection(tf2.getText());
						y=-1;
						j--;
						return;
					}
					b++;
				}
				m=p.matcher(tx.getText());
				if(y==-1 && m.find())
				{
					tx.select(m.start(), m.end());
					tx.replaceSelection(tf2.getText());
				}
			}
		});
		rpa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s=tx.getText();
				tx.setText(s.replaceAll(tf1.getText(), tf2.getText()));
			}
		});
		cl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jf.setVisible(false);
				jf.dispose();
				j=0;
				y=-1;
			}
		});
	}
	public void saver()
	{
		if(!isSaved)
		{
			int v=jfc.showSaveDialog(jtb);
			if(v==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					System.out.println("if");
					FileOutputStream fos=new FileOutputStream(jfc.getSelectedFile());
					BufferedOutputStream bos=new BufferedOutputStream(fos);
					jtb.setTitleAt(jtb.getSelectedIndex(),jfc.getSelectedFile().getName());
					System.out.print(jfc.getSelectedFile().getName());
					int ch;
					char c[]=tx.getText().toCharArray();
					int i=0;
					fname=jfc.getSelectedFile().getName();
					path=jfc.getSelectedFile().getAbsolutePath();
					dir=new File(jfc.getSelectedFile().getParent());
					while(i<c.length)
					{
						ch=c[i];
						bos.write(ch);
						i++;
					}
					isSaved=true;
					bos.close();
					bld.setEnabled(true);
					sv.setEnabled(false);
				}
				catch(IOException e)
				{
					System.out.print("File could'nt be saved");
				}
			}
		}
		else
		{
			try
			{
				System.out.println("Else");
				FileOutputStream fos=new FileOutputStream(path);
				BufferedOutputStream bos=new BufferedOutputStream(fos);
				char c[]=tx.getText().toCharArray();
				int i=0;
				while(i<c.length)
				{
					bos.write(c[i]);
					i++;
				}
				bos.close();
				sv.setEnabled(false);
			}
			catch(FileNotFoundException fnf)
			{
				System.out.print("Requested file could not be found");
			}
			catch(IOException ioe)
			{
				System.out.print("Problem writting into the file");
			}
		}
	
	}
	public void actionPerformed(ActionEvent ae)
	{
		JMenuItem mi=(JMenuItem)ae.getSource();
		if(mi==nw)
		{
			SwingUtilities.invokeLater(new NewFile());
			edt.setEnabled(true);
			sv.setEnabled(true);
			sva.setEnabled(true);
			cls.setEnabled(true);
			isSaved=false;
			bld.setEnabled(false);
		}
		if(mi==opn)
		{
			SwingUtilities.invokeLater(new OpenFile());
			bld.setEnabled(true);
			edt.setEnabled(true);
			sv.setEnabled(true);
			sva.setEnabled(true);
			cls.setEnabled(true);
		}
		if(mi==sv)
		{
			saver();
		}
		if(mi==sva)
		{
			int v=jfc.showSaveDialog(jtb);
			if(v==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					FileOutputStream fos=new FileOutputStream(jfc.getSelectedFile());
					BufferedOutputStream bos=new BufferedOutputStream(fos);
					jtb.setTitleAt(jtb.indexOfTab(jtb.getName())+1,jfc.getSelectedFile().getName());
					System.out.print(jfc.getSelectedFile().getName());
					int ch;
					char c[]=tx.getText().toCharArray();
					int i=0;
					fname=jfc.getSelectedFile().getName();
					path=jfc.getSelectedFile().getAbsolutePath();
					while(i<c.length)
					{
						ch=c[i];
						bos.write(ch);
						i++;
					}
					bos.close();
					bld.setEnabled(true);
				}
				catch(IOException e)
				{
					System.out.print("File could'nt be saved");
				}
			}
		}
		if(mi==fnd)
			finder();
		if(mi==rep)
			replacer();
		if(mi==cmp)
		{
			lx.setText("");
			if(sv.isEnabled())
				saver();
			String[] command = new String[4];
		    command[0] = "cmd";
		    command[1] = "/C";
		    command[2] = "javac";
		    command[3] = path;
		    try
		    {
		    	Process p = Runtime.getRuntime().exec(command);
		    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		    	String s = null;
		    	lx.setText("Compiling\n");
			    while ((s = stdInput.readLine()) != null)   
			    	lx.append(s);
			    // read any errors from the attempted command
			    while ((s = stdError.readLine()) != null) 
			    	lx.append(s);
		    }
		    catch(IOException e)
		    {}
		}
		if(mi==run)
		{
			lx.setText("Running\n");
			int b=fname.indexOf(".java");
			System.out.println(path.substring(0, path.indexOf(fname)));
			String pt=fname.substring(0,b);
			String[] command = new String[4];
		    command[0] = "cmd";
		    command[1] = "/k start cmd /k";
		    command[2] = "java";
		    command[3] = pt;
		    System.out.println(pt);
		    System.out.println(dir);
		    
		    try
		    {
		    	Runtime.getRuntime().exec(command,null,dir);
		    }
		    catch(IOException e)
		    {}
		}
		if(mi==cpy)
			tx.copy();
		if(mi==ct)
			tx.cut();
		if(mi==pst)
			tx.paste();
		if(mi==cls)
		{
			System.out.println("In close");
			if(sv.isEnabled())
			{
				int reply=JOptionPane.showConfirmDialog(jtb.getSelectedComponent(), "Do you want to save the changes?","Respond",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(reply==JOptionPane.YES_OPTION)
				{
					saver();
					System.out.print(jtb.getSelectedIndex());
					jtb.removeTabAt(jtb.getSelectedIndex());
					i--;
				}
				else if(reply==JOptionPane.NO_OPTION)
				{
					System.out.print(jtb.getSelectedIndex());
					jtb.removeTabAt(jtb.getSelectedIndex());
					i--;
				}
			}
			else
			{
				jtb.removeTabAt(jtb.getSelectedIndex());
				i--;
			}
		}
		if(mi==ext)
		{
			while(jtb.getTabCount()>0)
			{
				if(sv.isEnabled())
				{	
					int reply=JOptionPane.showConfirmDialog(jtb.getSelectedComponent(), "Do you want to save the changes made to "+jtb.getTitleAt(jtb.getSelectedIndex())+"?","Respond",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(reply==JOptionPane.YES_OPTION)
					{
						saver();
						jtb.removeTabAt(jtb.getSelectedIndex());
					}
					else if(reply==JOptionPane.NO_OPTION)
					{
						jtb.removeTabAt(jtb.getSelectedIndex());
					}
					else
						return;
				}
				else
					jtb.removeTabAt(jtb.getSelectedIndex());
			}
			terminator();
		}
	}
	public void terminator()
	{
		f.setVisible(false);
		f.dispose();
		System.exit(1);
	}
	
	public static void main(String[] args) 
	{
		new JavaBuilder();
	}
	public class NewFile implements Runnable 
	{
		public void run()
		{
			JTextArea ta=new JTextArea();
			tx=ta;
			tx.addKeyListener(new KeyListener()
			{

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
				/*	int c=0;
					if(e.getKeyChar()=='\n')
					{
						int i=0;
						while(i!='\n')
						{
							i=tx.getCaretPosition();
							Caret l=tx.getCaret();
							c++;
						}
						tx.setCaretPosition(i-1);
						
					}
				*/
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			JScrollPane sp=new JScrollPane(ta);
			sx=sp;
			System.out.print("i: "+i);
			jtb.add(sp,i);
			title="Untitled"+(i+1);
			isSaved=false;
			jtb.setTitleAt(i, title);
			jtb.setSelectedIndex(i);
			System.out.println(jtb.getTabCount()+"x");
			i++;
			bld.setEnabled(false);
		}
	}
	public class OpenFile implements Runnable
	{
		public void run()
		{
			JTextArea ta=new JTextArea();
			JScrollPane sp=new JScrollPane(ta);
			tx=ta;
			sx=sp;
			int v=jfc.showOpenDialog(jtb);
			if(v==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					fname=jfc.getSelectedFile().getName();
					path=jfc.getSelectedFile().getAbsolutePath();
					dir=new File(path.substring(0, path.indexOf(fname)));
					System.out.print(dir);
					FileInputStream fis=new FileInputStream(jfc.getSelectedFile());
					BufferedInputStream bis=new BufferedInputStream(fis);
					int ch;
					while((ch=bis.read())!=-1)
						ta.append((char)ch+"");
					jtb.add(sp,i);
					jtb.setTitleAt(i, fname);
					jtb.setSelectedIndex(i);
					i++;
				}
				catch(FileNotFoundException e)
				{
					System.out.print("Requested file could not be found");
				}
				catch(IOException e)
				{
					System.out.print("Couldn't open file");
				}
			}
		}
	}
}