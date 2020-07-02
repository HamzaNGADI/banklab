import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.*;


public class banklab {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mafen f = new mafen();

	}
  
} 
class mafen extends JFrame implements MouseListener,Runnable
{
	Thread t = new Thread(this);
	private JTable tab = new JTable();
	private JScrollPane spn = new JScrollPane(tab);
	private JButton b,bb,prev;
	private JButton ajj,supp,mod,aff,trans;
    int state,pstate=0;
	private JTextField log;
	private JPasswordField mdp;
	private JLabel code,nom,pre,adr,credit,info,logl,mdpl, icn,icnn;
	private JTextField  tcode,tnom,tpre,tadr,tcredit,tinfo;
	private JCheckBox cpcheque,cpcarnet,devdh,devdev;
	private ImageIcon img = new ImageIcon("C:\\Users\\user\\Documents\\bnpp.gif"); 
	private ImageIcon imgre = new ImageIcon("C:\\Users\\user\\Documents\\retour.gif"); 

	datab d;
	public mafen()
	{
		setTitle("Bank Lab");
		setSize(250,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d = new datab();
		t.start();
		getContentPane().setLayout(new FlowLayout()) ;
		this.getContentPane().setBackground(new Color(0,148,0));
		icn = new JLabel();
		icn.setIcon(img);
		icn.setBounds(0,0 , 250, 250);
		icnn = new JLabel();
		icnn.setIcon(imgre);
		icnn.setBounds(0,0 , 250, 250);
		getContentPane().add(icn);

		//getContentPane().setLayout(new FlowLayout()) ;
		addMouseListener(this);
		logl = new JLabel("utilisateur : ");
		getContentPane().add(logl);
		log= new JTextField(10);
		log.setBounds(30,35 , 90, 20);
		getContentPane().add(log);
		mdpl = new JLabel("password : ");
		getContentPane().add(mdpl);
		mdp= new JPasswordField(10);
		mdp.setBounds(30,57 , 90, 20);
		getContentPane().add(mdp);
		b = new JButton("connexion");
		b.setBounds(50,80 , 90, 40);
		b.addMouseListener(this);
		getContentPane().add(b);
		ajj = new JButton("ajouter");
		ajj.setBounds(70,20 , 90, 40);
		ajj.addMouseListener(this);
		getContentPane().add(ajj);
		ajj.setVisible(false);
		mod = new JButton("modifier");
		mod.setBounds(70,60 , 90, 40);
		mod.addMouseListener(this);
		getContentPane().add(mod);
		mod.setVisible(false);
		supp = new JButton("supprimer");
		supp.setBounds(70,100 , 90, 40);
		supp.addMouseListener(this);
		getContentPane().add(supp);
		supp.setVisible(false);
		aff = new JButton("afficher");
		aff.setBounds(4,120 , 90, 40);
		aff.addMouseListener(this);
		getContentPane().add(aff);
		aff.setVisible(false);
		trans = new JButton("transaction");
		trans.setBounds(4,120 , 90, 40);
		trans.addMouseListener(this);
		getContentPane().add(trans);
		trans.setVisible(false);
		
		code = new JLabel("code");
		code.setBounds(30,5 , 90, 20);
		getContentPane().add(code);
		code.setVisible(false);
		tcode = new JTextField(15);
		tcode.setBounds(30,10 , 90, 20);
		getContentPane().add(tcode); 
		tcode.setVisible(false);
		
		nom = new JLabel("nom");
		nom.setBounds(30,10 , 90, 20);
		getContentPane().add(nom);
		nom.setVisible(false);
		tnom = new JTextField(15);
		tnom.setBounds(30,25 , 90, 20);
		getContentPane().add(tnom);
		tnom.setVisible(false);
		
		pre = new JLabel("prenom");
		pre.setBounds(30,30 , 90, 20);
		getContentPane().add(pre);
		pre.setVisible(false);
		tpre = new JTextField(15);
		tpre.setBounds(30,45 , 90, 20);
		getContentPane().add(tpre);
		tpre.setVisible(false);
		
		adr = new JLabel("adresse");
		adr.setBounds(30,60 , 90, 20);
		getContentPane().add(adr);
		adr.setVisible(false);
		tadr = new JTextField(15);
		tadr.setBounds(30,75 , 90, 20);
		getContentPane().add(tadr);
		tadr.setVisible(false);
		
		credit = new JLabel("crédit");
		credit.setBounds(30,90 , 90, 20);
		getContentPane().add(credit);
		credit.setVisible(false);
		tcredit = new JTextField(15);
		tcredit.setBounds(30,105 , 90, 20);
		getContentPane().add(tcredit);
		tcredit.setVisible(false);
		
		info = new JLabel("information");
		info.setBounds(30,125 , 90, 20);
		getContentPane().add(info);
		info.setVisible(false);
		tinfo = new JTextField(15);
		tinfo.setBounds(30,140 , 90, 20);
		getContentPane().add(tinfo);
		tinfo.setVisible(false);
				 
		cpcheque = new JCheckBox("compte cheque",false);
		getContentPane().add(cpcheque); cpcheque.setVisible(false);
		cpcarnet = new JCheckBox("compte sur carnet",false);
		getContentPane().add(cpcarnet); cpcarnet.setVisible(false);
		devdh = new JCheckBox("devise DH's",false);
		getContentPane().add(devdh); devdh.setVisible(false);
	    devdev = new JCheckBox("devise brute",false);
		getContentPane().add(devdev); devdev.setVisible(false);
		
		bb = new JButton("valider");
		bb.setBounds(50,80 , 90, 40);
		bb.addMouseListener(this);
		getContentPane().add(bb); bb.setVisible(false);
		
		prev = new JButton("retour");
		prev.addMouseListener(this);
		getContentPane().add(prev,BorderLayout.SOUTH); prev.setVisible(false);
		getContentPane().add(icnn);
		icnn.setVisible(false);

	}
	 public void mouseClicked(MouseEvent ev) 
	  { 
		 String l=null,m=null;
		 System.out.println(" x : "+ev.getX() + " y : "+ev.getY());
		 if(ev.getY()>220){ pstate--; stateprog();}
		 if(ev.getSource() == b)
		 {
			 l = log.getText(); 
			 m = mdp.getText();
			 l = l.trim();
			 m = m.trim();
			 l = l.toLowerCase();
			 m = m.toLowerCase();
			 System.out.println(" "+ l+" " +m);
			 if(l.equals("admin") && m.equals("admin"))
			 { //repaint();
				// System.out.println(" gotchaaa "+ l+" " +m);
				 log.setVisible(false);
				 mdp.setVisible(false);
				 logl.setVisible(false);
				 mdpl.setVisible(false);
				 b.setVisible(false);
				 ajj.setVisible(true);
				 mod.setVisible(true);
				 supp.setVisible(true);
				 aff.setVisible(true);
				 trans.setVisible(true);
				 pstate=1;
				 setSize(250,350);
				 icnn.setVisible(true);

			 }
			 else			javax.swing.JOptionPane.showMessageDialog(null,"utilisateur ou mot de passe incorrect");

		 }
		 if(ev.getSource() == ajj)
		 {
			 log.setVisible(false);
			 mdp.setVisible(false);
			 b.setVisible(false);
			 ajj.setVisible(false);	 mod.setVisible(false);
			 supp.setVisible(false); aff.setVisible(false);
			 trans.setVisible(false);
			 
			 code.setVisible(true); tcode.setVisible(true);
			 nom.setVisible(true); tnom.setVisible(true);
			 pre.setVisible(true); tpre.setVisible(true);
			 adr.setVisible(true); tadr.setVisible(true);
			 credit.setVisible(true); tcredit.setVisible(true);
			 info.setVisible(true); tinfo.setVisible(true);
			 cpcheque.setVisible(true); cpcarnet.setVisible(true);
			 devdh.setVisible(true); devdev.setVisible(true);
			 bb.setVisible(true);
			 state = 1;
			 pstate = 2;
			 setSize(250,500);

		 }
		 if(ev.getSource() == mod)
		 {
			 log.setVisible(false);
			 mdp.setVisible(false);
			 b.setVisible(false);
			 ajj.setVisible(false);	 mod.setVisible(false);
			 supp.setVisible(false); aff.setVisible(false);
			 trans.setVisible(false);
			 
			 code.setVisible(true); tcode.setVisible(true);
			 nom.setVisible(true); tnom.setVisible(true);
			 pre.setVisible(true); tpre.setVisible(true);
			 adr.setVisible(true); tadr.setVisible(true);
			 credit.setVisible(true); tcredit.setVisible(true);
			 info.setVisible(true); tinfo.setVisible(true);
			 cpcheque.setVisible(true); cpcarnet.setVisible(true);
			 devdh.setVisible(true); devdev.setVisible(true);
			 bb.setVisible(true);
			 state = 2;
			 pstate = 2;
			 setSize(250,500);

		 }
		 if(ev.getSource() == supp)
		 {
			 log.setVisible(false);
			 mdp.setVisible(false);
			 b.setVisible(false);
			 ajj.setVisible(false);	 mod.setVisible(false);
			 supp.setVisible(false); aff.setVisible(false);
			 trans.setVisible(false);
			 
			 code.setVisible(true); tcode.setVisible(true);
			 nom.setVisible(true); tnom.setVisible(true);
			 pre.setVisible(true); tpre.setVisible(true);
			 bb.setVisible(true);
			 state = 3;
			 pstate = 2;
		 }
		 if(ev.getSource()==trans)
		 {
			 transi s = new transi();
		 }
		 
		 if(ev.getSource() == bb)
		 {   
			 try
			 {
				 if(state == 1 || state == 2)
				 {
				 int cred = 0;
				 int ch,car,ddh,dd;
			 String cd = tcode.getText();
			 String nm = tnom.getText();
			 String pren = tpre.getText();
			 String adre = tadr.getText();
			 String crd = tcredit.getText();
			 cred = Integer.parseInt(crd);
			 String inf = tinfo.getText(); 
			 if(cpcheque.isSelected()) ch = 1; else ch=0;
			 if(cpcarnet.isSelected()) car=1; else car=0; 
			 if(devdh.isSelected()) ddh=1; else ddh=0; 
			 if(devdev.isSelected()) dd=1; else dd=0;
			 boolean nosp = nospace(cd,nm,pren,adre,inf); 
			 cd=cd.trim(); nm=nm.trim(); pren=pren.trim();adre=adre.trim();inf=inf.trim();
			 System.out.println("-"+cd+"-"+nm+"-"+pren+"-"+adre+"-"+crd+"are"+cred+"-"+inf+"-");
			 if(!cd.equals("") && !nm.equals("") && !pren.equals("") && !adre.equals("") && !inf.equals("") && nosp == false)
			 {
			if(state == 1)   d.ajoutcl(cd, nm, pren, adre, cred, inf,ch,car,ddh,dd);
			if(state == 2)   d.modifcl(cd, nm, pren, adre, cred, inf,ch,car,ddh,dd);
			 }
			 else
			 {
	      System.out.println(" eror ajout / mod"); 
			javax.swing.JOptionPane.showMessageDialog(null,"vérifier les champs des données !!!");

			 }
				 }
		if(state == 3)
		{
			String cd = tcode.getText();
			 String nm = tnom.getText();
			 String pren = tpre.getText();
			if(cd != null && nm != null && pren != null)
			{
			 d.suppfcl(cd, nm, pren);
			 }
			else  System.out.println(" eror supp "); 
	
		}
		
		if(state ==4){}		
		
			}
			 catch(Exception e)
				{
					javax.swing.JOptionPane.showMessageDialog(null,"verifier les données !!"); 
				}
		 }
		 if(ev.getSource()== aff)
		 {
			 log.setVisible(false);
			 mdp.setVisible(false);
			 b.setVisible(false);
			 ajj.setVisible(false);	 mod.setVisible(false);
			 supp.setVisible(false); aff.setVisible(false);
			 trans.setVisible(false); 
			 pstate = 2;
			 spn.setVisible(true); 
			 tab = d.affichall();
			spn.setViewportView(tab);
			 this.setSize(600, 570);
			 this.getContentPane().add(spn);		 
				getContentPane().add(icnn);
		}
	  }
	  public void mousePressed (MouseEvent ev) {}    
	  public void mouseReleased(MouseEvent ev) {}    
	  public void mouseEntered (MouseEvent ev) {}
	  public void mouseExited  (MouseEvent ev) {}
	  public boolean nospace(String cd,String nm,String pren,String adre,String inf)
	  {
		  int c=0,n=0,p=0,ad=0,in=0;
		  for(int i=0;i<cd.length();i++)
		  {
			  if(cd.charAt(i)==' ') c++;
		  }
		  for(int i=0;i<nm.length();i++)
		  {
			  if(nm.charAt(i)==' ') n++;
		  }
		  for(int i=0;i<pren.length();i++)
		  {
			  if(pren.charAt(i)==' ') p++;
		  }
		  for(int i=0;i<adre.length();i++)
		  {
			  if(adre.charAt(i)==' ') ad++;
		  }
		  for(int i=0;i<inf.length();i++)
		  {
			  if(inf.charAt(i)==' ') in++;
		  }
		  
		  if(c == cd.length() || n == nm.length() || p==pren.length() || ad==adre.length() || in==inf.length() )
		  return true;
		 else return false;  
	  }
	  public void stateprog()
	  {
		  if(pstate <= 0)
		  {
			 ajj.setVisible(false);	 mod.setVisible(false);
			 supp.setVisible(false); aff.setVisible(false);
			 trans.setVisible(false);
		
			 logl.setVisible(true); log.setVisible(true);
				mdpl.setVisible(true); mdp.setVisible(true);
				 b.setVisible(true);
				 icnn.setVisible(false);
					setSize(250,300);
			  pstate = 0;
			//  repaint();
	//	revalidate();	


		  }
		  if(pstate == 1)
		  {
			  log.setVisible(false);
				 mdp.setVisible(false);
				 b.setVisible(false);
				 
				 code.setVisible(false); tcode.setVisible(false);
				 nom.setVisible(false); tnom.setVisible(false);
				 pre.setVisible(false); tpre.setVisible(false);
				 adr.setVisible(false); tadr.setVisible(false);
				 credit.setVisible(false); tcredit.setVisible(false);
				 info.setVisible(false); tinfo.setVisible(false);
				 cpcheque.setVisible(false); cpcarnet.setVisible(false);
				 devdh.setVisible(false); devdev.setVisible(false);
				 
				 tcode.setText(null);tnom.setText(null); tpre.setText(null);
				 tadr.setText(null);tcredit.setText(null);tinfo.setText(null);
				 
				 cpcheque.setSelected(false); cpcarnet.setSelected(false);
				 devdh.setSelected(false); devdev.setSelected(false);
				 bb.setVisible(false);
				 tab.setVisible(false);
				 ajj.setVisible(true);	 mod.setVisible(true);
				 supp.setVisible(true); aff.setVisible(true);
				 trans.setVisible(true);
				 spn.setVisible(false);
				 setSize(250,350);
				 // repaint(); revalidate();

		  }
	  }
	  public void run()
	  { 
		  while(true)
		  {
			  try{
				  d.maj();
				  Thread.sleep(3000);
			  }
				catch(Exception e){}
		  }
	  } 
/*	public void paint(Graphics g)
	{
		//g.setColor(Color.blue);
		//g.fillRect(0, 0, 600, 600);
		g.drawImage(img.getImage(),0,0, null);
	}*/
}

 
class datab
{
	public datab()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			m_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
			m_statement = m_connection.createStatement();
			m_state = m_connection.createStatement();
		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e); 
		}
	}

	public void ajoutcl(String co, String nom,String pr,String adr,int cre,String inf,int ch,int car,int ddh,int dd)
	{
          
            int i = 0,j=0;
		try
		{
			m_resultSet = m_statement.executeQuery("select * from client");
			while(m_resultSet.next())
			{
				String nmo = m_resultSet.getString("nom");
				String pro = m_resultSet.getString("prenom");
				String cc = m_resultSet.getString("code");
				if((nmo.equals(nom) && pro.equals(pr)) || cc.equals(co)) i=1;
			}
		if(i==0)
		{
		int st = m_statement.executeUpdate("INSERT INTO client VALUES ('"+co+"', '"+nom+"', '"+pr+"','"+adr+"', "+cre+",'"+inf+"')");
		int stt = m_statement.executeUpdate("INSERT INTO compte VALUES ('"+co+"', "+ch+","+car+", "+ddh+","+dd+")");
		javax.swing.JOptionPane.showMessageDialog(null,"ajouté !!");

		}
		else
//			System.out.println("données existant !!");
		javax.swing.JOptionPane.showMessageDialog(null,"données existants !!!");
 
		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
		}
               
                  

	}
       
  
	
	public void modifcl(String co, String nom,String pr,String adr,int cre,String inf, int ch,int car,int ddh,int dd)
	{
          
            int i = 0,j=0;
		try
		{
			m_resultSet = m_statement.executeQuery("select * from client");
			while(m_resultSet.next())
			{
				String nmo = m_resultSet.getString("nom");
				String pro = m_resultSet.getString("prenom");
				String cc = m_resultSet.getString("code");
				if(nmo.equals(nom) && cc.equals(co) && pro.equals(pr) ) i=1;
			}
		if(i==1)
		{ // update 
		//int st = m_statement.executeUpdate("UPDATE client SET  ('"+co+"', '"+nom+"', '"+pr+"','"+adr+"', "+cre+",'"+inf+"')");
			PreparedStatement up = m_connection.prepareStatement("UPDATE client SET code = ?, nom = ?, prenom = ?, adress = ?, credit = ?, info = ? WHERE code = ? AND nom = ? AND prenom = ?");
			up.setString(1, co);
			up.setString(2, nom);
			up.setString(3, pr);
			up.setString(4, adr);
			up.setInt(5, cre);
			up.setString(6, inf);
			up.setString(7, co);
			up.setString(8, nom);
			up.setString(9, pr);
			up.executeUpdate();
			PreparedStatement upp = m_connection.prepareStatement("UPDATE compte SET code = ?, cheque = ?, carnet = ?, deviseDH = ?, devise = ? WHERE code = ?");
			upp.setString(1, co);
			upp.setInt(2, ch);
			upp.setInt(3, car);
			upp.setInt(4, ddh);
			upp.setInt(5, dd);
			upp.setString(6, co);
			upp.executeUpdate();
			
	javax.swing.JOptionPane.showMessageDialog(null,"modifié !!");

		}
		else 
			//System.out.println("client non existant !!");
	javax.swing.JOptionPane.showMessageDialog(null,"client non existant");

		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
		}
               
	}
      
	
	public void suppfcl(String co, String nom,String pr)
	{
          
            int i = 0,j=0;
		try
		{
			m_resultSet = m_statement.executeQuery("select * from client");
			while(m_resultSet.next())
			{
				String nmo = m_resultSet.getString("nom");
				String pro = m_resultSet.getString("prenom");
				String cc = m_resultSet.getString("code");
				if(nmo.equals(nom) && cc.equals(co) && pro.equals(pr)) i=1;
			}
		if(i==1)
		{ // update 
		//int st = m_statement.executeUpdate("UPDATE client SET  ('"+co+"', '"+nom+"', '"+pr+"','"+adr+"', "+cre+",'"+inf+"')");
			PreparedStatement up = m_connection.prepareStatement("DELETE FROM client WHERE code = ? AND nom = ? AND prenom = ?");
			up.setString(1, co);
			up.setString(2, nom);
			up.setString(3, pr);

			up.executeUpdate();
			javax.swing.JOptionPane.showMessageDialog(null,"supprimé !!");

		}
		else
			javax.swing.JOptionPane.showMessageDialog(null,"client non existant");

		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
		}
               
	}
     
	public void transact(String c,String nm, String pr, int mnt,int bool)
	{
		
	     int i = 0,j=0,mont=0;
			try
			{
				m_resultSet = m_statement.executeQuery("select * from client");
				while(m_resultSet.next())
				{
					String nmo = m_resultSet.getString("nom");
					String pro = m_resultSet.getString("prenom");
					String cc = m_resultSet.getString("code");
					if(nmo.equals(nm) && cc.equals(c) && pro.equals(pr) )
					{ 
						i=1;
						mont = m_resultSet.getInt("credit");
					}
				}
				
			if(i==1)
			{ // update 
			//int st = m_statement.executeUpdate("UPDATE client SET  ('"+co+"', '"+nom+"', '"+pr+"','"+adr+"', "+cre+",'"+inf+"')");
				if(bool == 0)
				{
					mont+=mnt;
				PreparedStatement up = m_connection.prepareStatement("UPDATE client SET credit = ? WHERE code = ? AND nom = ? AND prenom = ?");
				up.setInt(1, mont);
				up.setString(2, c);
				up.setString(3, nm);
				up.setString(4, pr);
				up.executeUpdate();
				
		javax.swing.JOptionPane.showMessageDialog(null,"transaction success !!");
				}
				if(bool==1)
				{
					mont-=mnt;
			
					PreparedStatement up = m_connection.prepareStatement("UPDATE client SET credit = ? WHERE code = ? AND nom = ? AND prenom = ?");
					up.setInt(1, mont);
					up.setString(2, c);
					up.setString(3, nm);
					up.setString(4, pr);
					up.executeUpdate();
					
			javax.swing.JOptionPane.showMessageDialog(null,"transaction success !!");
			
				}

			}
			else 
				//System.out.println("client non existant !!");
		javax.swing.JOptionPane.showMessageDialog(null,"transaction non effectuée client non existant");

			}
			catch(Exception e)
			{
				javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
			}
	               		
	}
	
	
	public int nbtab()
	{
		int inb=0;
		try
		{	
			m_resultSet = m_statement.executeQuery("select * from client");
			while(m_resultSet.next())
			{
				inb++;
			}		
		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
		}
		
		return inb+1000;
	}
	  
	        
    public void maj()
    {
      try
      {
    	int ii=0,iii=0,n=0,nn=0; 
    	String t[] = new String[nbtab()];
    	String tt[] = new String[nbtab()];
    	m_rest = m_statement.executeQuery("select * from compte");
    	m_resultSet = m_state.executeQuery("select * from client");
		while(m_rest.next()){t[n] = m_rest.getString("code"); n++;}
		while(m_resultSet.next()){tt[nn] = m_resultSet.getString("code"); nn++;}

		for(int i=0;i<n;i++)
		{
			//System.out.println(" pr : "+ t[i]);
			ii=0;
			for(int j=0; j<nn; j++)
			{
			//String cc = m_resultSet.getString("code");
		//	System.out.println(" deux : "+tt[j]);
				if(t[i].equals(tt[j])) ii=1;
			} 
			if(ii==0)
			{
				PreparedStatement up = m_connection.prepareStatement("DELETE FROM compte WHERE code = ?");
				up.setString(1, t[i]);
				up.executeUpdate();
				System.out.println("maj modified");
			}
			else System.out.print("--");
		}
		// -----------------------
		
		for(int i=0;i<nn;i++)
		{
			//System.out.println(" pr : "+ t[i]);
			iii=0;
			for(int j=0; j<n; j++)
			{
			//String cc = m_resultSet.getString("code");
		//	System.out.println(" deux : "+tt[j]);
				if(tt[i].equals(t[j])) iii=1;
			} 
			if(iii==0)
			{
				PreparedStatement up = m_connection.prepareStatement("DELETE FROM client WHERE code = ?");
				up.setString(1, tt[i]);
				up.executeUpdate();
				System.out.println("maj modified 2");
			}
			else System.out.print("--");
		}
		//--------------------------
		
		
      }
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"Exception thrown ," + e);
		}
      System.out.print("\n");
    }
	
    public JTable affichall()
    {
    	JTable tab = null;
    	String che,car,dvdh,dvdv;
    	int i=0,cr,dt;
    	String title[]={"code","nom","prenom","adresse","credit","debit","info","c.cheque","c.carnet","c.devise DH","c.devise"};
    	Object data[][] = new Object[nbtab()][11];
    	try
    	{    		
    	m_rest = m_statement.executeQuery("select * from compte");
    	m_resultSet = m_state.executeQuery("select * from client"); 
    	while(m_rest.next() && m_resultSet.next())
    	{
    		if(m_rest.getInt("cheque")==1) che = "oui"; else che="non"; 
    		if(m_rest.getInt("carnet")==1) car = "oui"; else car="non"; 
    		if(m_rest.getInt("deviseDH")==1) dvdh = "oui"; else dvdh="non"; 
    		if(m_rest.getInt("devise")==1) dvdv = "oui"; else dvdv="non"; 
    		
    		if(m_resultSet.getInt("credit")>0){cr = m_resultSet.getInt("credit"); dt = 0;}
    		else{cr = 0; dt = m_resultSet.getInt("credit");}
		
    		
    		System.out.print(m_resultSet.getString("code")+" "+m_resultSet.getString("nom")+" "+m_resultSet.getString("prenom")+" "+m_resultSet.getString("adress")+" "+m_resultSet.getInt("credit")+" "+m_resultSet.getString("info"));
    		System.out.println(" "+che+" "+car +" " +dvdh+" " +dvdv);
    		data[i][0]=m_resultSet.getString("code");
    		data[i][1]=m_resultSet.getString("nom");
    		data[i][2]=m_resultSet.getString("prenom");
    		data[i][3]=m_resultSet.getString("adress");
    		data[i][4]= cr;
    		data[i][5]= dt;
    		data[i][6]=m_resultSet.getString("info");
    		data[i][7]=che;
    		data[i][8]=car;
    		data[i][9]=dvdh;
    		data[i][10]=dvdv;
    		i++;
    	}
    	tab = new JTable(data,title);
    	
    	}
    	catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(null,"affichall Exception thrown ," + e);
		}
    	return tab;
    }
    
        int va = 1;
        int vb = 5;
	static int d = 0,ll =0,sp = 0;
        static boolean b = false;
        static int dist[] = new int[10];
	private Connection m_connection;
	private Statement m_statement, m_state;
	private ResultSet m_resultSet,m_rest;
}



class transi extends JFrame implements MouseListener
{
	private JButton vers,retire,valide;
	private JTextField codet,nomt,prent, montt;
	private JLabel code,nom,pren,mont;
	datab d;
	int tr;

	public transi()
	{
		setTitle("Transac't");
		setSize(200,100);
		setVisible(true);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d = new datab();
		getContentPane().setLayout(new FlowLayout());
		getContentPane().setBackground(new Color(0,148,0));
		this.addMouseListener(this);
		vers = new JButton("versement");
		vers.addMouseListener(this);
		getContentPane().add(vers);
		retire = new JButton("retrait");
		retire.addMouseListener(this);
		getContentPane().add(retire);
		
		code = new JLabel("code");
		getContentPane().add(code);
		code.setVisible(false);
		codet = new JTextField(10);
		getContentPane().add(codet);
		codet.setVisible(false);
		nom = new JLabel("nom");
		getContentPane().add(nom);
		nom.setVisible(false);
		nomt = new JTextField(10);
		getContentPane().add(nomt);
		nomt.setVisible(false);
		pren = new JLabel("prenom");
		getContentPane().add(pren);
		pren.setVisible(false);
		prent = new JTextField(10);
		getContentPane().add(prent);
		prent.setVisible(false);
		
		mont = new JLabel("montant");
		getContentPane().add(mont);
		mont.setVisible(false);
		montt = new JTextField(10);
		getContentPane().add(montt);
		montt.setVisible(false);
		
		valide = new JButton("valider");
		valide.addMouseListener(this);
		getContentPane().add(valide);
		valide.setVisible(false);
	}
	
	public void mouseClicked(MouseEvent ev) {
		if(ev.getY()>200)
		{
			code.setVisible(false);  codet.setVisible(false);
			nom.setVisible(false);  nomt.setVisible(false);
			pren.setVisible(false);  prent.setVisible(false);
			mont.setVisible(false);  montt.setVisible(false);
			codet.setText(null); nomt.setText(null);prent.setText(null); 
			montt.setText(null);

			valide.setVisible(false);
			vers.setVisible(true);
			retire.setVisible(true);
			setSize(200,100);
		  
		}
		
		if(ev.getSource()==vers)
		{
			vers.setVisible(false);
			retire.setVisible(false);
			code.setVisible(true);  codet.setVisible(true);
			nom.setVisible(true);  nomt.setVisible(true);
			pren.setVisible(true);  prent.setVisible(true);
			mont.setVisible(true);  montt.setVisible(true);
			valide.setVisible(true);
			tr=0;
			setSize(150,270);
		}
		if(ev.getSource() == retire)
		{
			vers.setVisible(false);
			retire.setVisible(false);
			code.setVisible(true);  codet.setVisible(true);
			nom.setVisible(true);  nomt.setVisible(true);
			pren.setVisible(true);  prent.setVisible(true);
			mont.setVisible(true);  montt.setVisible(true);
			valide.setVisible(true);
			tr=1;
			setSize(150,270);

		}
		if(ev.getSource()==valide)
		{
			try{
				int mnnt =0;
			String cod = codet.getText();
			String noo = nomt.getText();
			String prr = prent.getText();
			String mmot = montt.getText();
			mnnt = Integer.parseInt(mmot);
			if(cod != null && noo !=null &&prr != null && mnnt != 0)
			{
			if(tr==0) d.transact(cod, noo, prr, mnnt, 0);
			if(tr==1) d.transact(cod, noo, prr, mnnt, 1);
			}
			else System.out.println("error transaction");
			}
			catch(Exception e)
			{
				javax.swing.JOptionPane.showMessageDialog(null,"verifier les données de transaction !!"); 
			}
		}
	}

	public void mouseEntered(MouseEvent ev) {}
	public void mouseExited(MouseEvent ev) {}
	public void mousePressed(MouseEvent ev) {	}
	public void mouseReleased(MouseEvent ev) {}
	
}