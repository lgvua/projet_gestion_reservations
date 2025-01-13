package App;


import java.util.ArrayList;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.text.ParseException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static javax.swing.JOptionPane.showMessageDialog;


import com.github.lgooddatepicker.components.DateTimePicker;

import java.io.*;

public class Menu {

	public Menu()
	{	
		varcharmax=50;
		insert_id_client = -1;
		insert_id_advisor = -1;
		insert_str_client="";
		insert_str_advisor="";
		mode=0;
		
		ArrayList<String> temp=loadArrayListFromTextFile("save.dat");

		if(temp == null || temp.size() !=15)
		{
			save= new ArrayList<String>();
			for (int i = 0; i< 15; i++) {
			    if(i==2 || i==4 || i==11)
			    	save.add("0");
			    else
			    	save.add("");
			}
		}
		else
		{
			save=temp;
		}
		
		dbm = new DBManager();
		
		// Initialisation des énumerations 		
		dbm.getConnection();
		enumStatutReservation = dbm.getEnum("ENUM_STATUT_RESERVATION");
		enumTypePaiement = dbm.getEnum("ENUM_TYPE_PAIEMENT");
		enumStatutVol = dbm.getEnum("ENUM_STATUT_VOL");
		enumTypeVol = dbm.getEnum("ENUM_TYPE_VOL");
		listCompany = dbm.getDistinctCompany();
		
		model = dbm.getAll();
		cmodel = dbm.getClient();
		amodel= dbm.getAdvisor();
		fbmodel = dbm.getFB();
		
		
        table = new MTable(model);
        
		dbm.closeConnection();
	}

	public void close()
	{
		dbm.closeConnection();
		System.exit(0);
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public void addBooking()
	{
		// Créer la nouvelle fenêtre pour la réservation
		JFrame frame = new JFrame("Menu de Réservation");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
        GridBagLayout gb = new GridBagLayout();
        
        frame.setLayout(gb);
        int ySizeTextField = 10;
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.insets = new Insets(5, 0, 0, 0);
        gbc.weightx=1;
        gbc.weighty=1;
        
        Label before_LB = new Label("        ");
    	frame.add(before_LB);
    	gbc.gridx =-1; gbc.gridy = 1; gb.setConstraints(before_LB, gbc);
    	
    	Label after_LB = new Label("        ");
    	frame.add(after_LB);
    	gbc.gridx =20; gbc.gridy = 1; gb.setConstraints(after_LB, gbc);
        
// ------------------------------------------  Clients  ---------------------------------------------------
        // Label        
	        	JLabel CL_client_LB = new JLabel("Client : ");
	        	frame.add(CL_client_LB);
	        	gbc.gridx =1; gbc.gridy = 2; gb.setConstraints(CL_client_LB, gbc);
	        	
	        	JLabel CL_email_LB = new JLabel("Email");
	        	frame.add(CL_email_LB);
	        	gbc.gridx =2; gbc.gridy = 1; gb.setConstraints(CL_email_LB, gbc);
	        	
        // TextField	
		        JTextField CL_email_TF = new JTextField(save.get(0), ySizeTextField);
		        frame.add(CL_email_TF);
		        gbc.gridx =2; gbc.gridy = 2; gb.setConstraints(CL_email_TF, gbc);
		        
		// Button
				JButton selectClientButton = new JButton("Sélectionner client");
				frame.add(selectClientButton);
				gbc.gridx =3; gbc.gridy = 2; gb.setConstraints(selectClientButton, gbc);

//
//// ------------------------------ Conseiller -----------------------------------------
	        // JLabel        
		        JLabel CO_conseiller_LB = new JLabel("Conseiller : ");
	        	frame.add(CO_conseiller_LB);
	        	gbc.gridx =1; gbc.gridy = 4; gb.setConstraints(CO_conseiller_LB, gbc);
	        	
	        	JLabel CO_email_LB = new JLabel("Email");
	        	frame.add(CO_email_LB);
	        	gbc.gridx =2; gbc.gridy = 3; gb.setConstraints(CO_email_LB, gbc);
	        	
	        
	        	
        	// TextField	
		        JTextField CO_email_TF = new JTextField(save.get(1), ySizeTextField);
		        frame.add(CO_email_TF);
		        gbc.gridx =2; gbc.gridy = 4; gb.setConstraints(CO_email_TF, gbc);
	       
				// Buton
				JButton selectAdvisorButton = new JButton("Sélectionner conseiller");
				frame.add(selectAdvisorButton);
				gbc.gridx =3; gbc.gridy = 4; gb.setConstraints(selectAdvisorButton, gbc);
				
				
				JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		        Color col = new Color(125, 125, 125);
		        sep.setForeground(col);
		        gbc.gridy = 5;
		        gbc.gridx = 0;
		        gbc.gridwidth = GridBagConstraints.REMAINDER; // This makes the separator span all columns
		        gbc.fill = GridBagConstraints.HORIZONTAL; // This makes the separator fill the horizontal space
		        frame.add(sep, gbc);
		        
		        gbc.gridwidth = GridBagConstraints.BOTH;
		        gbc.fill = GridBagConstraints.BOTH;
// -------------------------------- Vols ---------------------------------------------------
		    // JLabel 
			    JLabel VO_vol_LB = new JLabel("Vol : ");
	        	frame.add(VO_vol_LB);
	        	gbc.gridx =1; gbc.gridy = 7; gb.setConstraints(VO_vol_LB, gbc);
	
	        	
	        	JLabel VO_dhDepart_LB = new JLabel("Date/heure de départ");
	        	frame.add(VO_dhDepart_LB);
	        	gbc.gridx =2; gbc.gridy = 6; gb.setConstraints(VO_dhDepart_LB, gbc);
	        	
	        	JLabel VO_lieuDepart_LB = new JLabel("Lieu de départ");
	        	frame.add(VO_lieuDepart_LB);
	        	gbc.gridx =3; gbc.gridy = 6; gb.setConstraints(VO_lieuDepart_LB, gbc);
		        
	        	JLabel VO_dhArrivee_LB = new JLabel("Date/heure de d'arrivée");
	        	frame.add(VO_dhArrivee_LB);
	        	gbc.gridx =4; gbc.gridy = 6; gb.setConstraints(VO_dhArrivee_LB, gbc);
	        	
	        	JLabel VO_lieuArrivee_LB = new JLabel("Lieu d'arrivée");
	        	frame.add(VO_lieuArrivee_LB);
	        	gbc.gridx =5; gbc.gridy = 6; gb.setConstraints(VO_lieuArrivee_LB, gbc);
	        	
	        	JLabel VO_compagnie_LB = new JLabel("Compagnie (Éditable)");
		        frame.add(VO_compagnie_LB);
		        gbc.gridx =6; gbc.gridy = 6; gb.setConstraints(VO_compagnie_LB, gbc);
		        
	        	JLabel VO_typeVol_LB = new JLabel("Type de vol");
	        	frame.add(VO_typeVol_LB);
	        	gbc.gridx =7; gbc.gridy = 6; gb.setConstraints(VO_typeVol_LB, gbc);
	        	
	        // JTextField
	        	DateTimePicker VO_dhDepart_DTP = new DateTimePicker();
	        	try {
	        		if(!save.get(2).isEmpty())
	        		{
	        			LocalDateTime localDateTime = LocalDateTime.parse(save.get(2));
	        			VO_dhDepart_DTP.setDateTimeStrict(localDateTime);
	        		}
	            } catch (DateTimeParseException e) {
	                System.out.println("Failed to parse date: " + save.get(2));
	            }

	        	frame.add(VO_dhDepart_DTP);
			    gbc.gridx =2; gbc.gridy = 7; gb.setConstraints(VO_dhDepart_DTP, gbc);
		        
			    
			    JTextField VO_lieuDepart_TF = new JTextField(save.get(3), ySizeTextField);
			    frame.add(VO_lieuDepart_TF);
			    gbc.gridx =3; gbc.gridy = 7; gb.setConstraints(VO_lieuDepart_TF, gbc);
			            
			    DateTimePicker VO_dhArrivee_DTP = new DateTimePicker();
		        frame.add(VO_dhArrivee_DTP);
		        gbc.gridx =4; gbc.gridy = 7; gb.setConstraints(VO_dhArrivee_DTP, gbc);
		        
		        try {
	        		if(!save.get(4).isEmpty())
	        		{
	        			LocalDateTime localDateTime = LocalDateTime.parse(save.get(4));
	        			VO_dhArrivee_DTP.setDateTimeStrict(localDateTime);
	        		}
	            } catch (DateTimeParseException e) {
	                System.out.println("Failed to parse date: " + save.get(4));
	            }
		   
		        JTextField VO_lieuArrivee_TF = new JTextField(save.get(5), ySizeTextField);
		        frame.add(VO_lieuArrivee_TF);
		        gbc.gridx =5; gbc.gridy = 7; gb.setConstraints(VO_lieuArrivee_TF, gbc);
		        
		        
		        JComboBox<String> VO_compagnie_CC = new JComboBox<String>();
		        for(String company : listCompany)
		        {
		        	VO_compagnie_CC.addItem(company);
		        }
		        frame.add(VO_compagnie_CC);
		        gbc.gridx =6; gbc.gridy = 7; gb.setConstraints(VO_compagnie_CC, gbc);
		        VO_compagnie_CC.setEditable(true);
		        VO_compagnie_CC.setSelectedItem(save.get(6));
		        
		        
		  // Choice
		        JComboBox<String> VO_typeVol_CC = new JComboBox<String>();
		        for(String type : enumTypeVol)
		        {
		        	VO_typeVol_CC.addItem(type);
		        }
		        
		        if(!save.get(7).isBlank() && isNumeric(save.get(7)))
		        	VO_typeVol_CC.setSelectedIndex(Integer.parseInt(save.get(7)));
		        frame.add(VO_typeVol_CC);
		        gbc.gridx =7; gbc.gridy = 7; gb.setConstraints(VO_typeVol_CC, gbc);
		        
		        
// Personne 
		    // JLabel        
	        	JLabel PR_client_LB = new JLabel("Personne ayant réservé : ");
	        	frame.add(PR_client_LB);
	        	gbc.gridx =1; gbc.gridy = 9; gb.setConstraints(PR_client_LB, gbc);
	        	
	        	JLabel PR_email_LB = new JLabel("Email");
	        	frame.add(PR_email_LB);
	        	gbc.gridx =2; gbc.gridy = 8; gb.setConstraints(PR_email_LB, gbc);
	        	
	        	JLabel PR_lastName_LB = new JLabel("Nom");
	        	frame.add(PR_lastName_LB);
	        	gbc.gridx =3; gbc.gridy = 8; gb.setConstraints(PR_lastName_LB, gbc);
	        	
	        	JLabel PR_firstName_LB = new JLabel("Prénom");
	        	frame.add(PR_firstName_LB);
	        	gbc.gridx =4; gbc.gridy = 8; gb.setConstraints(PR_firstName_LB, gbc);
	        	
	        	JLabel PR_phoneNumber_LB = new JLabel("Numéro de téléphone");
	        	frame.add(PR_phoneNumber_LB);
	        	gbc.gridx =5; gbc.gridy = 8; gb.setConstraints(PR_phoneNumber_LB, gbc);
        	
        // JTextField	
		        JTextField PR_email_TF = new JTextField(save.get(8), ySizeTextField);
		        frame.add(PR_email_TF);
		        gbc.gridx =2; gbc.gridy = 9; gb.setConstraints(PR_email_TF, gbc);
	        
		    
		        JTextField PR_lastName_TF = new JTextField(save.get(9), ySizeTextField);
		        frame.add(PR_lastName_TF);
		        gbc.gridx =3; gbc.gridy = 9; gb.setConstraints(PR_lastName_TF, gbc);
		        
		      
		        JTextField PR_firstName_TF = new JTextField(save.get(10), ySizeTextField);
		        frame.add(PR_firstName_TF);
		        gbc.gridx =4; gbc.gridy = 9; gb.setConstraints(PR_firstName_TF, gbc);
		        
		   
		        JTextField PR_phoneNumber_TF = new JTextField(save.get(11), ySizeTextField);
		        frame.add(PR_phoneNumber_TF);
		        gbc.gridx =5; gbc.gridy = 9; gb.setConstraints(PR_phoneNumber_TF, gbc);
		        
// -------------------------------- PAIEMENT --------------------------------------
		    // JLabel
		        JLabel PA_paiement_LB = new JLabel("Paiement : ");
	        	frame.add(PA_paiement_LB);
	        	gbc.gridx =1; gbc.gridy = 11; gb.setConstraints(PA_paiement_LB, gbc);
	
	        	JLabel PA_dhPaiement_LB = new JLabel("Date/heure de paiement");
	        	frame.add(PA_dhPaiement_LB);
	        	gbc.gridx =2; gbc.gridy = 10; gb.setConstraints(PA_dhPaiement_LB, gbc);
	        	
	        	JLabel PA_typePaiement_LB = new JLabel("Type de paiement");
	        	frame.add(PA_typePaiement_LB);
	        	gbc.gridx =3; gbc.gridy = 10; gb.setConstraints(PA_typePaiement_LB, gbc);
	        // JTextField
	        	DateTimePicker PA_dhPaiement_DTP = new DateTimePicker();
	        	
	        	try {
	        		if(!save.get(12).isEmpty())
	        		{
	        			LocalDateTime localDateTime = LocalDateTime.parse(save.get(12));
	        			PA_dhPaiement_DTP.setDateTimeStrict(localDateTime);
	        		}
	            } catch (DateTimeParseException e) {
	                System.out.println("Failed to parse date: " + save.get(12));
	            }
	        	
		        frame.add(PA_dhPaiement_DTP);
		        gbc.gridx =2; gbc.gridy = 11; gb.setConstraints(PA_dhPaiement_DTP, gbc);
		        
		        JComboBox<String> PA_typePaiement_CC = new JComboBox<String>();
		        for(String type : enumTypePaiement)
		        {
		        	PA_typePaiement_CC.addItem(type);
		        }
		        
		        if(!save.get(13).isBlank() && isNumeric(save.get(13)))
		        	PA_typePaiement_CC.setSelectedIndex(Integer.parseInt(save.get(13)));
		        frame.add(PA_typePaiement_CC);
		        gbc.gridx =3; gbc.gridy = 11; gb.setConstraints(PA_typePaiement_CC, gbc);
		 
// ---------------------------------- Réservation ----------------------------------- //
		    // JLabel
		        JLabel RE_reservation_LB = new JLabel("Réservation : ");
	        	frame.add(RE_reservation_LB);
	        	gbc.gridx =1; gbc.gridy = 13; gb.setConstraints(RE_reservation_LB, gbc);
	        	
	        	JLabel RE_blank_LB = new JLabel("        ");
	        	frame.add(RE_blank_LB);
	        	gbc.gridx =1; gbc.gridy = 16; gb.setConstraints(RE_blank_LB, gbc);
		        
		        JLabel RE_statut_reservation_LB = new JLabel("Statut");
	        	frame.add(RE_statut_reservation_LB);
	        	gbc.gridx =2; gbc.gridy = 12; gb.setConstraints(RE_statut_reservation_LB, gbc);

	        // JTextField
		        
	        	JComboBox<String> RE_statut_reservation_CC = new JComboBox<String>();
		        for(String type : enumStatutReservation)
		        {
		        	RE_statut_reservation_CC.addItem(type);
		        }
		        
		        if(!save.get(14).isBlank() && isNumeric(save.get(14)))
		        	RE_statut_reservation_CC.setSelectedIndex(Integer.parseInt(save.get(14)));
		        frame.add(RE_statut_reservation_CC);
		        gbc.gridx =2; gbc.gridy = 13; gb.setConstraints(RE_statut_reservation_CC, gbc);

		        JLabel client_LB = new JLabel("Client : ");
	        	frame.add(client_LB);
	        	gbc.gridx =2; gbc.gridy = 14; gb.setConstraints(client_LB, gbc);
		        
	        	
	        	JLabel advisor_LB = new JLabel("Conseiller : ");
	        	frame.add(advisor_LB);
	        	gbc.gridx =3; gbc.gridy = 14; gb.setConstraints(advisor_LB, gbc);
		        
		        JLabel selected_LB = new JLabel("Sélection : ");
	        	frame.add(selected_LB);
	        	gbc.gridx =1; gbc.gridy = 15; gb.setConstraints(selected_LB, gbc);
	    
		        JLabel selectedClient_LB = new JLabel(insert_str_client);
	        	frame.add(selectedClient_LB);
	        	gbc.gridx =2; gbc.gridy = 15; gb.setConstraints(selectedClient_LB, gbc);
	        	
	        	JLabel selectedAdvisor_LB = new JLabel(insert_str_advisor);
	        	frame.add(selectedAdvisor_LB);
	        	gbc.gridx =3; gbc.gridy = 15; gb.setConstraints(selectedAdvisor_LB, gbc);
		        
		        
		        JButton startButton = new JButton("Quitter la fenêtre");
		        frame.add(startButton);
				gbc.gridx =2; gbc.gridy = 17; gb.setConstraints(startButton, gbc);
		        
		        JButton clearButton = new JButton("Vider les champs");
		        frame.add(clearButton);
				gbc.gridx =6; gbc.gridy = 17; gb.setConstraints(clearButton, gbc);
		        
				JButton sendButton = new JButton("Soumettre");
				frame.add(sendButton);
				gbc.gridx =7; gbc.gridy = 17; gb.setConstraints(sendButton, gbc);
				
				selectClientButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String email =  CL_email_TF.getText();
		            	if(!email.isEmpty() && email.length()<=varcharmax)
		            	{
				            	dbm.getConnection();
				            	
				            	ArrayList<String> result = dbm.getClientByEmail(email);
				            	if(result!=null)
				            	{
				            		insert_id_client=Integer.parseInt(result.get(0));
				            		insert_str_client=result.get(2)+" "+result.get(1);
				            		selectedClient_LB.setText(insert_str_client);
				            		showMessageDialog(frame, "Client sélectionné","Client sélectionné",JOptionPane.INFORMATION_MESSAGE);
				            	}
				            	else
				            		showMessageDialog(frame,"L'email n'est associée à aucun utilisateur","Email introuvable",JOptionPane.ERROR_MESSAGE);
				            	
				            	dbm.closeConnection();
		            	}
		            	else
		            		showMessageDialog(frame, "L'email ne doit pas être vide et ne doit pas dépasser "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
		             }
		        });
				
				selectAdvisorButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String email =  CO_email_TF.getText();
		            	if(!email.isEmpty() && email.length()<=varcharmax)
		            	{
				            	dbm.getConnection();
				            	
				            	ArrayList<String> result = dbm.getAdvisorByEmail(email);
				            	if(result!=null)
				            	{
				            		insert_id_advisor=Integer.parseInt(result.get(0));
				            		insert_str_advisor=result.get(2)+" "+result.get(1);
				            		selectedAdvisor_LB.setText(insert_str_advisor);
				            		showMessageDialog(frame, "Conseiller sélectionné","Conseiller sélectionné",JOptionPane.INFORMATION_MESSAGE);

				            	}
				            	else
				            		showMessageDialog(frame,"L'email n'est associée à aucun conseiller","Email introuvable",JOptionPane.ERROR_MESSAGE);
				            	
				            	dbm.closeConnection();
		            	}
		            	else
		            		showMessageDialog(frame,"L'email ne doit pas être vide et ne doit pas dépasser "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
		             }
		        });
				
				
			
				startButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	save(frame);
		            	frame.dispose();
		             }
		          });
				
				sendButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	
		            	if(insert_id_client != -1)
		            	{
		            		if(insert_id_advisor != -1)
		            		{
		            			// Traitement du temps
		            			String NULLDATETIME = "NULL";
		            			String dhPaiement= NULLDATETIME;
		            			String dhArrivee= NULLDATETIME;
		            			String dhDepart = NULLDATETIME;
		            			
		            			LocalDateTime localDateTimePayment = PA_dhPaiement_DTP.getDateTimeStrict();
		            			LocalDateTime localDateTimeArrival = VO_dhArrivee_DTP.getDateTimeStrict();
		            			LocalDateTime localDateTimeDeparture = VO_dhDepart_DTP.getDateTimeStrict();
		            			

		            			if(localDateTimePayment !=null)
		            			{
		            				dhPaiement= "'"+localDateTimePayment.toString()+"'";
		            			}

		            			if(localDateTimeArrival != null)
		            			{
		            				dhArrivee = "'"+localDateTimeArrival.toString()+"'";
		            			}

		            		    if(localDateTimeDeparture != null)
		            			{
		            		    	dhDepart="'"+localDateTimeDeparture.toString()+"'";
		            			}
		            				
		            			dbm.getConnection();
		            			dbm.insertBooking(insert_id_client,insert_id_advisor, dhDepart, VO_lieuDepart_TF.getText(), dhArrivee, VO_lieuArrivee_TF.getText(), VO_compagnie_CC.getSelectedItem().toString(), VO_typeVol_CC.getSelectedItem().toString(), PR_email_TF.getText(), PR_lastName_TF.getText(), PR_firstName_TF.getText(), PR_phoneNumber_TF.getText(), dhPaiement, PA_typePaiement_CC.getSelectedItem().toString(), RE_statut_reservation_CC.getSelectedItem().toString());
		            			model=dbm.getAll();
		            			fbmodel=dbm.getFB();
		            			dbm.closeConnection();
		            			
		            			if(mode == 0)
		            			{
			            			table.setModel(model); table.style(dbm.gLocalDateTimeIndexTable);
		            			}
		            			else if(mode==3)
		            			{
		            				table.setModel(fbmodel); table.style(dbm.FBLocalDateTimeIndexTable);
		            			}
		            			
		            			clear(frame);
		            			save(frame);
		            			frame.dispose();
		            			showMessageDialog(frame,"Réservation créée avec succès","Réservation créée avec succès",JOptionPane.INFORMATION_MESSAGE);
		            		}
		            		else
		            			showMessageDialog(frame,"Veuilez sélectionner un conseiller (via le bouton sélectionner dans la section conseiller)","Sélection invalide",JOptionPane.ERROR_MESSAGE);
		            	}
		            	else
		            		showMessageDialog(frame,"Veuilez sélectionner un client (via le bouton sélectionner dans la section client)","Sélection invalide",JOptionPane.ERROR_MESSAGE);
		             }
		          });
				
				clearButton.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	clear(frame);
			             }
			         
				});

        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	save(frame);
            	frame.dispose();
            }
        });

        // Paramètres de la fenêtre de réservation
        frame.setBackground(new Color(189, 189, 211));
        frame.setVisible(true);   // Afficher la fenêtre de réservation
	}
	
	public void clear (JFrame frame)
	{
		// Effacer les valeurs des champs de saisie et mettre à 0 les choix
        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
            else if (component instanceof JComboBox) {
          	  if(!((JComboBox<?>) component).isEditable())
          		  ((JComboBox<?>) component).setSelectedIndex(0);
          	  else
          		  ((JComboBox<?>) component).setSelectedItem("");
            }
            else if (component instanceof DateTimePicker)
            {
          	  ((DateTimePicker) component).clear();
            }
        }
        
        	insert_id_client = -1;
        	insert_id_advisor = -1;
			insert_str_client="";
			insert_str_advisor="";
	}
	
	public void save (JFrame frame)
	{
		Component[] components = frame.getContentPane().getComponents();
		int i=0;
        for (Component component : components) {
            if (component instanceof JTextField) {
            	save.set(i,((JTextField) component).getText());
                i++;
            }
            
            if (component instanceof JComboBox) {
          	  if(!((JComboBox<?>) component).isEditable())
          	  {
          		save.set(i,Integer.toString(((JComboBox<?>) component).getSelectedIndex()));
          	  }
          	  else
          	  {
          		save.set(i,((JComboBox<?>) component).getSelectedItem().toString());
          	  }
          	  

          	  i++;
            }
            
            if (component instanceof DateTimePicker)
            {
            	LocalDateTime localDateTime = ((DateTimePicker) component).getDateTimePermissive();
            	if(localDateTime != null)
            	{
            		save.set(i,localDateTime.toString());
            	}
            	else
            	{
            		save.set(i,"");
            	}
            		
            	i++;
            }

        }
        saveArrayListToTextFile(save, "save.dat");
	}

    public static void saveArrayListToTextFile(ArrayList<String> list, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            if(list != null)
            {
	        	for (String item : list) {
	                writer.write(item);
	                writer.newLine();
	            }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Erreur de sauvegarde dans "+filename+" : " + e.getMessage(),"Erreur de sauvegarde locale",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<String> loadArrayListFromTextFile(String filename) {
        ArrayList<String> list = new ArrayList<>();
        
        File file = new File(filename);

        if (!file.exists()) {
        	try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Erreur de création de "+filename+" : " + e.getMessage(),"Erreur de fichier",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Erreur de lecture de "+filename+" : " + e.getMessage(),"Erreur de lecture de fichier",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return list;
    }

    
    public static boolean isValidTimestamp(String str) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setLenient(false);  // Set lenient to false to avoid interpreting invalid timestamps

      try {
    	  sdf.parse(str);  // Try parsing the string
          return true;
      } catch (ParseException e) {
                return false;  // If parsing fails, it's not a valid timestamp
            }
    }
     
    public void start()
    {
        JFrame frame = new JFrame("Données de réservations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            	close();
            }
        });
        

        // Tableau 
        table.style(dbm.gLocalDateTimeIndexTable);
        
        JScrollPane scrollPane = new JScrollPane(table);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
         
         table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        frame.add(northPanel, BorderLayout.NORTH);
	        
        JPanel group1 = new JPanel();
        group1.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JPanel group2 = new JPanel();
        group2.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        northPanel.add(group1);
        northPanel.add(Box.createVerticalStrut(10));
        northPanel.add(group2);
        
	        JButton excelButton = new JButton("Excel");
	        group1.add(excelButton);
	       

	        JButton clientButton = new JButton("Client");
	        group1.add(clientButton);
	       
	        	

	        JButton advisorButton = new JButton("Conseiller");
	        group1.add(advisorButton);
	       
	        
	        JButton fbButton = new JButton("Réservation-Vol");
	        group1.add(fbButton);
	        
	    
	        
	        // Bouton ajouter 
	        JButton addButton = new JButton("Ajouter réservation");
	        group2.add(addButton);
	        addButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	switch(mode)
            		{
	            		case 0:
	    	                addBooking();
	            			break;
	            			
	            		case 1:
	            			addClient(frame);
	            			break;
	            			
	            		case 2:
	            			addAdvisor(frame);
	            			break;
	            			
	            		case 3:
	            			addBooking();
	            			break;
            		}
	            }
	        });
	        

	        // Bouton de modification
	        JButton modButton = new JButton("Modifier réservation");
	        group2.add(modButton);
	        modButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(table.getSelectedRow() != -1)
	            	{
	            	int row = table.getSelectedRow();
	            	int column=0;
	            		switch(mode)
	            		{
		            		case 0:
		            			modBooking(frame,table.getValueAt(row,column).toString(),row,column);
		            			break;
		            			
		            		case 1:
		            			modClient(frame,table.getValueAt(row,column).toString(),row,column);
		            			break;
		            			
		            		case 2:
		            			modAdvisor(frame,table.getValueAt(row,column).toString(),row,column);
		            			break;
		            			
		            		case 3:
		            			modBooking(frame,table.getValueAt(row,column).toString(),row,column);
		            			break;
	            		}

              
	               
	            	}
	            	else
	            	{
	            		if(table.getRowCount() != 0)
	            			showMessageDialog(frame,"Veuillez sélectionner une ligne","La modification ne peut pas être effectuée",JOptionPane.ERROR_MESSAGE);
	            		else
	            			showMessageDialog(frame,"Veuillez ajouter une réservation","La modification ne peut pas être effectuée",JOptionPane.ERROR_MESSAGE);
	            	}
	            }
	        });
	        
	        
	        
	        JButton deleteButton = new JButton("Supprimer réservation");
	        group2.add(deleteButton);
	        deleteButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(table.getSelectedRow() != -1)
	            	{
	            	int row = table.getSelectedRow();
	            	int column=0;
	    
	            	
	            	dbm.getConnection();
	            		switch(mode)
	            		{
		            		case 0:
		            			dbm.deleteBooking(table.getValueAt(row,column).toString());
		            			model=dbm.getAll();
		            			fbmodel=dbm.getFB();
		            			table.setModel(model);
		            			table.style(dbm.gLocalDateTimeIndexTable);
		            			break;
		            			
		            		case 1:

		    	            	int result = JOptionPane.showConfirmDialog(frame, "Souhaitez-vous supprimer un client ? Sachez que toutes les réservations et les vols de ce client seront supprimés.","Confirmation", JOptionPane.YES_OPTION);
		    	            	if(result  == JOptionPane.YES_OPTION)
		    	            	{
		            			dbm.deleteClient(table.getValueAt(row,column).toString());
		            			cmodel=dbm.getClient();
		            			model=dbm.getAll();
		            			table.setModel(cmodel);
		    	            	}
		            			break;
		            			
		            		case 2:
		            			int resultb = JOptionPane.showConfirmDialog(frame, "Souhaitez-vous supprimer un conseiller ? Sachez que toutes les réservations et les vols de ce conseiller seront supprimés.","Confirmation", JOptionPane.YES_OPTION);
		            			if(resultb  == JOptionPane.YES_OPTION)
		    	            	{
			            			dbm.deleteAdvisor(table.getValueAt(row,column).toString());
			            			amodel=dbm.getAdvisor();
			            			model=dbm.getAll();
			            			table.setModel(amodel);
		    	            	}
		            			break;
		            			
		            		case 3:
		            			dbm.deleteBooking(table.getValueAt(row,column).toString());
		            			model=dbm.getAll();
		            			fbmodel=dbm.getFB();
		            			table.setModel(fbmodel);
		            			table.style(dbm.FBLocalDateTimeIndexTable);
		            			break;
	            		}
	            	dbm.closeConnection();
	              
	            	}
	            	else
	            	{
	            		if(table.getRowCount() != 0)
	            			showMessageDialog(frame,"Veuillez sélectionner une ligne","La suppression ne peut pas être effectuée",JOptionPane.ERROR_MESSAGE);
	            		else
	            			showMessageDialog(frame,"Il n'y a aucune réservation à supprimer.","La suppression ne peut pas être effectuée",JOptionPane.ERROR_MESSAGE);
	            	}
	            }
	        });
	        
	        excelButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mode=0;
	            	table.setModel(model);
	            	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            	table.style(dbm.gLocalDateTimeIndexTable);
	            	addButton.setText("Ajouter réservation");
	            	modButton.setText("Modifier réservation");
	            	deleteButton.setText("Supprimer réservation");
	            }
	        });


	        
	        clientButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mode=1;
	            	table.setModel(cmodel);
	            	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	            	addButton.setText("Ajouter client");
	            	modButton.setText("Modifier client");
	            	deleteButton.setText("Supprimer client");
	            }
	        });
	        
	        advisorButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mode=2;
	            	table.setModel(amodel);
	            	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	            	addButton.setText("Ajouter conseiller");
	            	modButton.setText("Modifier conseiller");
	            	deleteButton.setText("Supprimer conseiller");
	            }
	        });
	        
	        fbButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mode=3;
	            	table.setModel(fbmodel);
	            	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            	table.style(dbm.FBLocalDateTimeIndexTable);
	            	addButton.setText("Ajouter réservation");
	            	modButton.setText("Modifier réservation");
	            	deleteButton.setText("Supprimer réservation");
	            }
	        });
	        
	        JButton quitButton = new JButton("Quitter");
	        group2.add(quitButton);
	        quitButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                frame.dispose();
	                close();
	            }
	        });
    	 
         frame.add(scrollPane, BorderLayout.CENTER);
         
         frame.setVisible(true);
    }

    
   public void addClient(JFrame mainFrame)
   {
	JFrame frame = new JFrame("Ajouter un client");
		
       GridBagLayout gb = new GridBagLayout();
       
       frame.setLayout(gb);
       int ySizeTextField = 10;
       
       GridBagConstraints gbc = new GridBagConstraints();
       

       gbc.fill = GridBagConstraints.BOTH;
       gbc.insets = new Insets(5, 5, 5, 5);
       gbc.weightx=1;
       gbc.weighty=1;
       
       Label before_LB = new Label("        ");
   	frame.add(before_LB);
   	gbc.gridx =-1; gbc.gridy = 1; gb.setConstraints(before_LB, gbc);
   	
   	Label after_LB = new Label("        ");
   	frame.add(after_LB);
   	gbc.gridx =20; gbc.gridy = 1; gb.setConstraints(after_LB, gbc);
       
//------------------------------------------  Clients  ---------------------------------------------------
       // Label        
	        	JLabel CL_client_LB = new JLabel("Client : ");
	        	frame.add(CL_client_LB);
	        	gbc.gridx =1; gbc.gridy = 2; gb.setConstraints(CL_client_LB, gbc);
	        	
	        	JLabel CL_email_LB = new JLabel("Email");
	        	frame.add(CL_email_LB);
	        	gbc.gridx =2; gbc.gridy = 1; gb.setConstraints(CL_email_LB, gbc);
	        	
	        	JLabel CL_lastName_LB = new JLabel("Nom");
	        	frame.add(CL_lastName_LB);
	        	gbc.gridx =3; gbc.gridy = 1; gb.setConstraints(CL_lastName_LB, gbc);
	
	        	JLabel CL_firstName_LB = new JLabel("Prénom");
	        	frame.add(CL_firstName_LB);
	        	gbc.gridx =4; gbc.gridy = 1; gb.setConstraints(CL_firstName_LB, gbc);

	        	
	        	JLabel CL_phoneNumber_LB = new JLabel("Numéro de téléphone");
	        	frame.add(CL_phoneNumber_LB);
	        	gbc.gridx =5; gbc.gridy = 1; gb.setConstraints(CL_phoneNumber_LB, gbc);

	        	
	        	JLabel CL_adress_LB = new JLabel("Adresse");
	        	frame.add(CL_adress_LB);
	        	gbc.gridx =6; gbc.gridy = 1; gb.setConstraints(CL_adress_LB, gbc);

       	
       // TextField	
		        JTextField CL_email_TF = new JTextField(ySizeTextField);
		        frame.add(CL_email_TF);
		        gbc.gridx =2; gbc.gridy = 2; gb.setConstraints(CL_email_TF, gbc);
	        
		    
		        JTextField CL_lastName_TF = new JTextField(ySizeTextField);
		        frame.add(CL_lastName_TF);
		        gbc.gridx =3; gbc.gridy = 2; gb.setConstraints(CL_lastName_TF, gbc);
	
		      
		        JTextField CL_firstName_TF = new JTextField(ySizeTextField);
		        frame.add(CL_firstName_TF);
		        gbc.gridx =4; gbc.gridy = 2; gb.setConstraints(CL_firstName_TF, gbc);
		
		   
		        JTextField CL_phoneNumber_TF = new JTextField(ySizeTextField);
		        frame.add(CL_phoneNumber_TF);
		        gbc.gridx =5; gbc.gridy = 2; gb.setConstraints(CL_phoneNumber_TF, gbc);
		  
		        
		        JTextField CL_adress_TF =  new JTextField(ySizeTextField);
		        frame.add(CL_adress_TF);
		        gbc.gridx =6; gbc.gridy = 2; gb.setConstraints(CL_adress_TF, gbc);

				JButton addClientButton = new JButton("Ajouter client");
				frame.add(addClientButton);
				gbc.gridx =8; gbc.gridy = 2; gb.setConstraints(addClientButton, gbc);
				
				addClientButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String email =  CL_email_TF.getText();
		            	String nom = CL_lastName_TF.getText();
		            	String prenom = CL_firstName_TF.getText();
		            	String numero_telephone = CL_phoneNumber_TF.getText();
		            	String adresse = CL_adress_TF.getText();
		            	
		            	if(!email.isEmpty() && email.length()<=varcharmax &&  !nom.isEmpty() && nom.length() <= varcharmax && !prenom.isEmpty() && prenom.length()<=varcharmax && adresse.length()<=varcharmax && numero_telephone.length()<=varcharmax && adresse.length()<=varcharmax)
		            	{
				            	dbm.getConnection();
				            	int update= dbm.insertClient(email,nom,prenom,numero_telephone,adresse);
				            	switch(update)
				            	{
					            	case 0:
					            		showMessageDialog(frame,"Erreur d'insertion","Erreur d'insertion",JOptionPane.ERROR_MESSAGE);
					            		break;
					            		
					            	case -1:
					            		showMessageDialog(frame,"Email déjà pris par un client","Email déjà pris par un client",JOptionPane.ERROR_MESSAGE);
					            		break;
					            		
					            	default:
					            		frame.dispose();
					            		cmodel=dbm.getClient();
					            		if(mode==1)
					            		{
					            			table.setModel(cmodel);
					            		}
					            		showMessageDialog(frame,"Client ajoutée","Client ajoutée",JOptionPane.INFORMATION_MESSAGE);
					            		
				            	}
				            	dbm.closeConnection();
		            	}
		            	else
		            		showMessageDialog(frame,"Les champs email, nom et prénom ne peuvent pas être vides. Aucun champ ne doit dépasser la longueur maximale autorisée de "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
		            }
		            
    			});
				
				frame.pack();
		        frame.setLocationRelativeTo(mainFrame);
				frame.setBackground(new Color(189, 189, 211));
		        frame.setVisible(true); 
   }
    
    public void addAdvisor(JFrame mainFrame)
    {
    	JFrame frame = new JFrame("Ajouter un client");
		
        GridBagLayout gb = new GridBagLayout();
        
        frame.setLayout(gb);
        int ySizeTextField = 10;
        
        GridBagConstraints gbc = new GridBagConstraints();
        

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx=1;
        gbc.weighty=1;
        
        Label before_LB = new Label("        ");
    	frame.add(before_LB);
    	gbc.gridx =-1; gbc.gridy = 1; gb.setConstraints(before_LB, gbc);
    	
    	Label after_LB = new Label("        ");
    	frame.add(after_LB);
    	gbc.gridx =20; gbc.gridy = 1; gb.setConstraints(after_LB, gbc);
        
    	
    	
    	   // JLabel        
        JLabel CO_conseiller_LB = new JLabel("Conseiller : ");
    	frame.add(CO_conseiller_LB);
    	gbc.gridx =1; gbc.gridy = 4; gb.setConstraints(CO_conseiller_LB, gbc);
    	
    	JLabel CO_lastName_LB = new JLabel("Nom");
    	frame.add(CO_lastName_LB);
    	gbc.gridx =3; gbc.gridy = 3; gb.setConstraints(CO_lastName_LB, gbc);
    	
    	JLabel CO_firstName_LB = new JLabel("Prénom");
    	frame.add(CO_firstName_LB);
    	gbc.gridx =4; gbc.gridy = 3; gb.setConstraints(CO_firstName_LB, gbc);
    	
    	JLabel CO_email_LB = new JLabel("Email");
    	frame.add(CO_email_LB);
    	gbc.gridx =2; gbc.gridy = 3; gb.setConstraints(CO_email_LB, gbc);
    	
    	JLabel CO_phoneNumber_LB = new JLabel("Numéro de téléphone");
    	frame.add(CO_phoneNumber_LB);
    	gbc.gridx =5; gbc.gridy = 3; gb.setConstraints(CO_phoneNumber_LB, gbc);
    	
	// TextField	
        JTextField CO_email_TF = new JTextField(ySizeTextField);
        frame.add(CO_email_TF);
        gbc.gridx =2; gbc.gridy = 4; gb.setConstraints(CO_email_TF, gbc);
    

        JTextField CO_lastName_TF = new JTextField(ySizeTextField);
        frame.add(CO_lastName_TF);
        gbc.gridx =3; gbc.gridy = 4; gb.setConstraints(CO_lastName_TF, gbc);
        

        JTextField CO_firstName_TF = new JTextField(ySizeTextField);
        frame.add(CO_firstName_TF);
        gbc.gridx =4; gbc.gridy = 4; gb.setConstraints(CO_firstName_TF, gbc);
        
        JTextField CO_phoneNumber_TF =  new JTextField(ySizeTextField);
        frame.add(CO_phoneNumber_TF);
        gbc.gridx =5; gbc.gridy = 4; gb.setConstraints(CO_phoneNumber_TF, gbc);

				
		JButton addAdvisorButton = new JButton("Ajouter conseiller");
		frame.add(addAdvisorButton);
		gbc.gridx =6; gbc.gridy = 4; gb.setConstraints(addAdvisorButton, gbc);
		
		
		addAdvisorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String email =  CO_email_TF.getText();
            	String nom = CO_lastName_TF.getText();
            	String prenom = CO_firstName_TF.getText();
            	String numero_telephone = CO_phoneNumber_TF.getText();
            	
            	if(!email.isEmpty() && email.length()<=varcharmax &&  !nom.isEmpty() && nom.length() <= varcharmax && !prenom.isEmpty() && prenom.length()<=varcharmax && numero_telephone.length()<=varcharmax)
            	{
		            	dbm.getConnection();
		            	int update=dbm.insertAdvisor(email,nom,prenom,numero_telephone);
		            	switch(update)
		            	{
			            	case 0:
			            		showMessageDialog(frame,"Erreur d'insertion","Erreur d'insertion",JOptionPane.ERROR_MESSAGE);
			            		break;
			            		
			            	case -1:
			            		showMessageDialog(frame,"Email déjà pris par un conseiller","Email déjà pris par un conseiler",JOptionPane.ERROR_MESSAGE);
			            		break;
			            		
			            	default:
			            		frame.dispose();
			            		amodel=dbm.getAdvisor();
			            		if(mode==2)
			            		{
			            			table.setModel(amodel);
			            		}
			            		showMessageDialog(frame,"Conseiller ajouté","Conseiller ajouté",JOptionPane.INFORMATION_MESSAGE);
		            	}
		            	dbm.closeConnection();
            	}
            	else
            		showMessageDialog(frame,"Les champs email, nom et prénom ne peuvent pas être vides. Aucun champ ne doit dépasser la longueur maximale autorisée de "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
            }
            
		});
		
		frame.pack();
        frame.setLocationRelativeTo(mainFrame);
		frame.setBackground(new Color(189, 189, 211));
        frame.setVisible(true); 
    }
    
    
    
    
    
    
   public void modClient(JFrame mainFrame,final String id_client,int row, int column)
   {
	   dbm.getConnection();
	   ArrayList<String> val = dbm.getClientById(id_client);
	   dbm.closeConnection();
	   if(val==null)
	   {
		   val=new  ArrayList<String>();
		   for(int i=0; i<5; i++)
		   {
			   val.add("");
		   }
		   
	   }
	   
	   JFrame frame = new JFrame("Ajouter un client");
		
       GridBagLayout gb = new GridBagLayout();
       
       frame.setLayout(gb);
       int ySizeTextField = 10;
       
       GridBagConstraints gbc = new GridBagConstraints();
       

       gbc.fill = GridBagConstraints.BOTH;
       gbc.insets = new Insets(5, 5, 5, 5);
       gbc.weightx=1;
       gbc.weighty=1;
       
       Label before_LB = new Label("        ");
   	frame.add(before_LB);
   	gbc.gridx =-1; gbc.gridy = 1; gb.setConstraints(before_LB, gbc);
   	
   	Label after_LB = new Label("        ");
   	frame.add(after_LB);
   	gbc.gridx =20; gbc.gridy = 1; gb.setConstraints(after_LB, gbc);
       
//------------------------------------------  Clients  ---------------------------------------------------
       // Label        
	        	JLabel CL_client_LB = new JLabel("Client : ");
	        	frame.add(CL_client_LB);
	        	gbc.gridx =1; gbc.gridy = 2; gb.setConstraints(CL_client_LB, gbc);
	        	
	        	JLabel CL_lastName_LB = new JLabel("Nom");
	        	frame.add(CL_lastName_LB);
	        	gbc.gridx =2; gbc.gridy = 1; gb.setConstraints(CL_lastName_LB, gbc);
	
	        	JLabel CL_firstName_LB = new JLabel("Prénom");
	        	frame.add(CL_firstName_LB);
	        	gbc.gridx =3; gbc.gridy = 1; gb.setConstraints(CL_firstName_LB, gbc);
	        	
	        	
	        	JLabel CL_email_LB = new JLabel("Email");
	        	frame.add(CL_email_LB);
	        	gbc.gridx =4; gbc.gridy = 1; gb.setConstraints(CL_email_LB, gbc);

	        	
	        	JLabel CL_phoneNumber_LB = new JLabel("Numéro de téléphone");
	        	frame.add(CL_phoneNumber_LB);
	        	gbc.gridx =5; gbc.gridy = 1; gb.setConstraints(CL_phoneNumber_LB, gbc);

	        	
	        	JLabel CL_adress_LB = new JLabel("Adresse");
	        	frame.add(CL_adress_LB);
	        	gbc.gridx =6; gbc.gridy = 1; gb.setConstraints(CL_adress_LB, gbc);

       	
       // TextField	
		    
		    
		        JTextField CL_lastName_TF = new JTextField(val.get(0),ySizeTextField);
		        frame.add(CL_lastName_TF);
		        gbc.gridx =2; gbc.gridy = 2; gb.setConstraints(CL_lastName_TF, gbc);
	
		      
		        JTextField CL_firstName_TF = new JTextField(val.get(1),ySizeTextField);
		        frame.add(CL_firstName_TF);
		        gbc.gridx =3; gbc.gridy = 2; gb.setConstraints(CL_firstName_TF, gbc);
		        
		        JTextField CL_email_TF = new JTextField(val.get(2),ySizeTextField);
		        frame.add(CL_email_TF);
		        gbc.gridx =4; gbc.gridy = 2; gb.setConstraints(CL_email_TF, gbc);
	        
		        JTextField CL_phoneNumber_TF = new JTextField(val.get(3),ySizeTextField);
		        frame.add(CL_phoneNumber_TF);
		        gbc.gridx =5; gbc.gridy = 2; gb.setConstraints(CL_phoneNumber_TF, gbc);
		  
		        
		        JTextField CL_adress_TF =  new JTextField(val.get(4),ySizeTextField);
		        frame.add(CL_adress_TF);
		        gbc.gridx =6; gbc.gridy = 2; gb.setConstraints(CL_adress_TF, gbc);

				JButton addClientButton = new JButton("Modifier client");
				frame.add(addClientButton);
				gbc.gridx =8; gbc.gridy = 2; gb.setConstraints(addClientButton, gbc);
				
				addClientButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String email =  CL_email_TF.getText();
		            	String nom = CL_lastName_TF.getText();
		            	String prenom = CL_firstName_TF.getText();
		            	String numero_telephone = CL_phoneNumber_TF.getText();
		            	String adresse = CL_adress_TF.getText();
		            	
		            	if(!email.isEmpty() && email.length()<=varcharmax &&  !nom.isEmpty() && nom.length() <= varcharmax && !prenom.isEmpty() && prenom.length()<=varcharmax && adresse.length()<=varcharmax && numero_telephone.length()<=varcharmax && adresse.length()<=varcharmax)
		            	{
				            	dbm.getConnection();
				            	dbm.updateClient(id_client,nom,prenom,email,numero_telephone,adresse);
				            	cmodel=dbm.getClient();
				            	dbm.closeConnection();
				            	if(mode==1)
				            	{
				            		table.setModel(cmodel);
				            	}
				            	frame.dispose();
		            	}
		            	else
		            		showMessageDialog(frame,"Les champs email, nom et prénom ne peuvent pas être vides. Aucun champ ne doit dépasser la longueur maximale autorisée de "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
		            }
		            
    			});
				
				frame.pack();
		        frame.setLocationRelativeTo(mainFrame);
				frame.setBackground(new Color(189, 189, 211));
		        frame.setVisible(true); 
   }
   
   public void modAdvisor(JFrame mainFrame,final String id_advisor,int row, int column)
   {
	   dbm.getConnection();
	   ArrayList<String> val = dbm.getAdvisorById(id_advisor);
	   dbm.closeConnection();
	   if(val==null)
	   {
		   val=new  ArrayList<String>();
		   for(int i=0; i<4; i++)
		   {
			   val.add("");
		   }
		   
	   }
	   
	   
	   JFrame frame = new JFrame("Ajouter un client");
		
       GridBagLayout gb = new GridBagLayout();
       
       frame.setLayout(gb);
       int ySizeTextField = 10;
       
       GridBagConstraints gbc = new GridBagConstraints();
       

       gbc.fill = GridBagConstraints.BOTH;
       gbc.insets = new Insets(5, 5, 5, 5);
       gbc.weightx=1;
       gbc.weighty=1;
       
       Label before_LB = new Label("        ");
   	frame.add(before_LB);
   	gbc.gridx =-1; gbc.gridy = 1; gb.setConstraints(before_LB, gbc);
   	
   	Label after_LB = new Label("        ");
   	frame.add(after_LB);
   	gbc.gridx =20; gbc.gridy = 1; gb.setConstraints(after_LB, gbc);
       
   	
   	
   	   // JLabel        
       JLabel CO_conseiller_LB = new JLabel("Conseiller : ");
   	frame.add(CO_conseiller_LB);
   	gbc.gridx =1; gbc.gridy = 4; gb.setConstraints(CO_conseiller_LB, gbc);
   	
   	JLabel CO_lastName_LB = new JLabel("Nom");
   	frame.add(CO_lastName_LB);
   	gbc.gridx =2; gbc.gridy = 3; gb.setConstraints(CO_lastName_LB, gbc);
   	
   	JLabel CO_firstName_LB = new JLabel("Prénom");
   	frame.add(CO_firstName_LB);
   	gbc.gridx =3; gbc.gridy = 3; gb.setConstraints(CO_firstName_LB, gbc);
   	
   	JLabel CO_email_LB = new JLabel("Email");
   	frame.add(CO_email_LB);
   	gbc.gridx =4; gbc.gridy = 3; gb.setConstraints(CO_email_LB, gbc);
   	
   	JLabel CO_phoneNumber_LB = new JLabel("Numéro de téléphone");
   	frame.add(CO_phoneNumber_LB);
   	gbc.gridx =5; gbc.gridy = 3; gb.setConstraints(CO_phoneNumber_LB, gbc);
   	
	// TextField	
       JTextField CO_lastName_TF = new JTextField(val.get(0),ySizeTextField);
       frame.add(CO_lastName_TF);
       gbc.gridx =2; gbc.gridy = 4; gb.setConstraints(CO_lastName_TF, gbc);
       

       JTextField CO_firstName_TF = new JTextField(val.get(1),ySizeTextField);
       frame.add(CO_firstName_TF);
       gbc.gridx =3; gbc.gridy = 4; gb.setConstraints(CO_firstName_TF, gbc);
       
       JTextField CO_email_TF = new JTextField(val.get(2),ySizeTextField);
       frame.add(CO_email_TF);
       gbc.gridx =4; gbc.gridy = 4; gb.setConstraints(CO_email_TF, gbc);
       
       JTextField CO_phoneNumber_TF =  new JTextField(val.get(3),ySizeTextField);
       frame.add(CO_phoneNumber_TF);
       gbc.gridx =5; gbc.gridy = 4; gb.setConstraints(CO_phoneNumber_TF, gbc);

				
		JButton addAdvisorButton = new JButton("Modifier conseiller");
		frame.add(addAdvisorButton);
		gbc.gridx =6; gbc.gridy = 4; gb.setConstraints(addAdvisorButton, gbc);
		
		
		addAdvisorButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
           	String email =  CO_email_TF.getText();
           	String nom = CO_lastName_TF.getText();
           	String prenom = CO_firstName_TF.getText();
           	String numero_telephone = CO_phoneNumber_TF.getText();
           	
           	if(!email.isEmpty() && email.length()<=varcharmax &&  !nom.isEmpty() && nom.length() <= varcharmax && !prenom.isEmpty() && prenom.length()<=varcharmax && numero_telephone.length()<=varcharmax)
           	{
		            	dbm.getConnection();
		            	dbm.updateAdvisor(id_advisor, nom,prenom,email,numero_telephone);
		            	amodel=dbm.getAdvisor();
		            	if(mode==2)
		            	{
		            		table.setModel(amodel);
		            	}
		            	dbm.closeConnection();
		            	frame.dispose();
           	}
           	else
           		showMessageDialog(frame,"Les champs email, nom et prénom ne peuvent pas être vides. Aucun champ ne doit dépasser la longueur maximale autorisée de "+Integer.toString(varcharmax)+" caractères.","Saisie invalide",JOptionPane.ERROR_MESSAGE);
           }
           
		});
		
		frame.pack();
       frame.setLocationRelativeTo(mainFrame);
		frame.setBackground(new Color(189, 189, 211));
       frame.setVisible(true); 
   }

    public void modBooking(JFrame mainFrame,final String id_booking,int row, int column)
    {
    	// Nous appellons la base de données et non le tableau pour avoir plus de flexibiité sur les variables
    	dbm.getConnection();
    	ArrayList<String> content = dbm.getBookingById(id_booking);
    	dbm.closeConnection();
    	
    	// Créer la nouvelle fenêtre pour la réservationd
    			JFrame modFrame = new JFrame("Menu de Réservation");
    			modFrame.setSize(row, column);
    			
    			
    	        GridBagLayout gb = new GridBagLayout();
    	        
    	        modFrame.setLayout(gb);
    	        int ySizeTextField = 15;
    	        
    	        GridBagConstraints gbc = new GridBagConstraints();
    	        
    	        gbc.fill = GridBagConstraints.BOTH;
    	        //gbc.insets = new Insets(5, 0, 0, 0);


    	// -------------------------------- Vols ---------------------------------------------------
    			    // JLabel 
    				    JLabel  MOD_VO_vol_LB = new JLabel("Vol : ");
    		        	modFrame.add( MOD_VO_vol_LB);
    		        	gbc.gridx =1; gbc.gridy = 1; gb.setConstraints( MOD_VO_vol_LB, gbc);
    		
    		        	
    		        	JLabel  MOD_VO_dhDepart_LB = new JLabel("Date/heure de départ");
    		        	modFrame.add( MOD_VO_dhDepart_LB);
    		        	gbc.gridx =2; gbc.gridy = 0; gb.setConstraints( MOD_VO_dhDepart_LB, gbc);
    		        	
    		        	JLabel  MOD_VO_lieuDepart_LB = new JLabel("Lieu de départ");
    		        	modFrame.add( MOD_VO_lieuDepart_LB);
    		        	gbc.gridx =3; gbc.gridy = 0; gb.setConstraints( MOD_VO_lieuDepart_LB, gbc);
    			        
    		        	JLabel  MOD_VO_dhArrivee_LB = new JLabel("Date/heure de d'arrivée");
    		        	modFrame.add( MOD_VO_dhArrivee_LB);
    		        	gbc.gridx =4; gbc.gridy = 0; gb.setConstraints( MOD_VO_dhArrivee_LB, gbc);
    		        	
    		        	JLabel  MOD_VO_lieuArrivee_LB = new JLabel("Lieu d'arrivée");
    		        	modFrame.add( MOD_VO_lieuArrivee_LB);
    		        	gbc.gridx =5; gbc.gridy = 0; gb.setConstraints( MOD_VO_lieuArrivee_LB, gbc);
    		        	
    		        	JLabel  MOD_VO_compagnie_LB = new JLabel("Compagnie (Éditable)");
    			        modFrame.add( MOD_VO_compagnie_LB);
    			        gbc.gridx =6; gbc.gridy = 0; gb.setConstraints( MOD_VO_compagnie_LB, gbc);
    			        
    		        	JLabel  MOD_VO_typeVol_LB = new JLabel("Type de vol");
    		        	modFrame.add( MOD_VO_typeVol_LB);
    		        	gbc.gridx =7; gbc.gridy = 0; gb.setConstraints( MOD_VO_typeVol_LB, gbc);
    		        	
    		        // JTextField
    		        	DateTimePicker  MOD_VO_dhDepart_DTP = new DateTimePicker();
    		        	try {
    		        		if(content.get(7) != null)
    		        		{
    		        			LocalDateTime localDateTime = LocalDateTime.parse(content.get(7));
    		        			 MOD_VO_dhDepart_DTP.setDateTimeStrict(localDateTime);
    		        		}
    		            } catch (DateTimeParseException e) {
    		                System.out.println("Failed to parse date: " + content.get(7));
    		            }

    		        	modFrame.add( MOD_VO_dhDepart_DTP);
    				    gbc.gridx =2; gbc.gridy = 1; gb.setConstraints( MOD_VO_dhDepart_DTP, gbc);
    			        
    				    
    				    JTextField  MOD_VO_lieuDepart_TF = new JTextField(content.get(8), ySizeTextField);
    				    modFrame.add( MOD_VO_lieuDepart_TF);
    				    gbc.gridx =3; gbc.gridy = 1; gb.setConstraints( MOD_VO_lieuDepart_TF, gbc);
    				            
    				    DateTimePicker  MOD_VO_dhArrivee_DTP = new DateTimePicker();
    			        modFrame.add( MOD_VO_dhArrivee_DTP);
    			        gbc.gridx =4; gbc.gridy = 1; gb.setConstraints( MOD_VO_dhArrivee_DTP, gbc);
    			        
    			        try {
    		        		if(content.get(9) != null)
    		        		{
    		        			LocalDateTime localDateTime = LocalDateTime.parse(content.get(9));
    		        			 MOD_VO_dhArrivee_DTP.setDateTimeStrict(localDateTime);
    		        		}
    		            } catch (DateTimeParseException e) {
    		                System.out.println("Failed to parse date: " + content.get(9));
    		            }
    			   
    			        JTextField  MOD_VO_lieuArrivee_TF = new JTextField(content.get(10), ySizeTextField);
    			        modFrame.add( MOD_VO_lieuArrivee_TF);
    			        gbc.gridx =5; gbc.gridy = 1; gb.setConstraints( MOD_VO_lieuArrivee_TF, gbc);
    			        
    			        
    			        JComboBox<String>  MOD_VO_compagnie_CC = new JComboBox<String>();
    			        for(String company : listCompany)
    			        {
    			        	 MOD_VO_compagnie_CC.addItem(company);
    			        }
    			        modFrame.add( MOD_VO_compagnie_CC);
    			        gbc.gridx =6; gbc.gridy = 1; gb.setConstraints( MOD_VO_compagnie_CC, gbc);
    			         MOD_VO_compagnie_CC.setEditable(true);
    			         MOD_VO_compagnie_CC.setSelectedItem(content.get(12));
    			        
    			        
    			  // Choice
    			        JComboBox<String>  MOD_VO_typeVol_CC = new JComboBox<String>();
    			        for(String type : enumTypeVol)
    			        {
    			        	 MOD_VO_typeVol_CC.addItem(type);
    			        }

    			         MOD_VO_typeVol_CC.setSelectedItem(content.get(11));
    			        modFrame.add( MOD_VO_typeVol_CC);
    			        gbc.gridx =7; gbc.gridy = 1; gb.setConstraints( MOD_VO_typeVol_CC, gbc);
    			        
    			        
    	// Personne 
    			    // JLabel        
    		        	JLabel  MOD_PR_client_LB = new JLabel("Personne ayant réservé : ");
    		        	modFrame.add( MOD_PR_client_LB);
    		        	gbc.gridx =1; gbc.gridy = 3; gb.setConstraints( MOD_PR_client_LB, gbc);
    		        	
    		        	JLabel  MOD_PR_email_LB = new JLabel("Email");
    		        	modFrame.add( MOD_PR_email_LB);
    		        	gbc.gridx =2; gbc.gridy = 2; gb.setConstraints( MOD_PR_email_LB, gbc);
    		        	
    		        	JLabel  MOD_PR_lastName_LB = new JLabel("Nom");
    		        	modFrame.add( MOD_PR_lastName_LB);
    		        	gbc.gridx =3; gbc.gridy = 2; gb.setConstraints( MOD_PR_lastName_LB, gbc);
    		        
    		        	JLabel  MOD_PR_firstName_LB = new JLabel("Prénom");
    		        	modFrame.add( MOD_PR_firstName_LB);
    		        	gbc.gridx =4; gbc.gridy = 2; gb.setConstraints( MOD_PR_firstName_LB, gbc);
    		        	
    		        	JLabel  MOD_PR_phoneNumber_LB = new JLabel("Numéro de téléphone");
    		        	modFrame.add( MOD_PR_phoneNumber_LB);
    		        	gbc.gridx =5; gbc.gridy = 2; gb.setConstraints( MOD_PR_phoneNumber_LB, gbc);
    	        	
    	        // JTextField	
    			        JTextField  MOD_PR_email_TF = new JTextField(content.get(5), ySizeTextField);
    			        modFrame.add( MOD_PR_email_TF);
    			        gbc.gridx =2; gbc.gridy = 3; gb.setConstraints( MOD_PR_email_TF, gbc);
    		        
    			    
    			        JTextField  MOD_PR_lastName_TF = new JTextField(content.get(3), ySizeTextField);
    			        modFrame.add( MOD_PR_lastName_TF);
    			        gbc.gridx =3; gbc.gridy = 3; gb.setConstraints( MOD_PR_lastName_TF, gbc);
    			        
    			      
    			        JTextField  MOD_PR_firstName_TF = new JTextField(content.get(4), ySizeTextField);
    			        modFrame.add( MOD_PR_firstName_TF);
    			        gbc.gridx =4; gbc.gridy = 3; gb.setConstraints( MOD_PR_firstName_TF, gbc);
    			        
    			   
    			        JTextField  MOD_PR_phoneNumber_TF = new JTextField(content.get(6), ySizeTextField);
    			        modFrame.add( MOD_PR_phoneNumber_TF);
    			        gbc.gridx =5; gbc.gridy = 3; gb.setConstraints( MOD_PR_phoneNumber_TF, gbc);
    			        
    	// -------------------------------- PAIEMENT --------------------------------------
    			    // JLabel
    			        JLabel  MOD_PA_paiement_LB = new JLabel("Paiement : ");
    		        	modFrame.add( MOD_PA_paiement_LB);
    		        	gbc.gridx =1; gbc.gridy = 5; gb.setConstraints( MOD_PA_paiement_LB, gbc);
    		
    		        	JLabel  MOD_PA_dhPaiement_LB = new JLabel("Date/heure de paiement");
    		        	modFrame.add( MOD_PA_dhPaiement_LB);
    		        	gbc.gridx =2; gbc.gridy = 4; gb.setConstraints( MOD_PA_dhPaiement_LB, gbc);
    		        	
    		        	JLabel  MOD_PA_typePaiement_LB = new JLabel("Type de paiement");
    		        	modFrame.add( MOD_PA_typePaiement_LB);
    		        	gbc.gridx =3; gbc.gridy = 4; gb.setConstraints( MOD_PA_typePaiement_LB, gbc);
    		        // JTextField
    		        	DateTimePicker  MOD_PA_dhPaiement_DTP = new DateTimePicker();
    		        	
    		        	try {
    		        		if(content.get(1)  != null)
    		        		{
    		        			LocalDateTime localDateTime = LocalDateTime.parse(content.get(1));
    		        			 MOD_PA_dhPaiement_DTP.setDateTimeStrict(localDateTime);
    		        		}
    		            } catch (DateTimeParseException e) {
    		                System.out.println("Failed to parse date: " + content.get(1));
    		            }
    		        	
    			        modFrame.add( MOD_PA_dhPaiement_DTP);
    			        gbc.gridx =2; gbc.gridy = 5; gb.setConstraints( MOD_PA_dhPaiement_DTP, gbc);
    			        
    			        JComboBox<String>  MOD_PA_typePaiement_CC = new JComboBox<String>();
    			        for(String type : enumTypePaiement)
    			        {
    			        	 MOD_PA_typePaiement_CC.addItem(type);
    			        }
    			         MOD_PA_typePaiement_CC.setSelectedItem(content.get(2));
    			        modFrame.add( MOD_PA_typePaiement_CC);
    			        gbc.gridx =3; gbc.gridy = 5; gb.setConstraints( MOD_PA_typePaiement_CC, gbc);
    			 
    	// ---------------------------------- Réservation ----------------------------------- //
    			    // JLabel
    			        JLabel  MOD_RE_reservation_LB = new JLabel("Réservation : ");
    		        	modFrame.add( MOD_RE_reservation_LB);
    		        	gbc.gridx =1; gbc.gridy = 8; gb.setConstraints( MOD_RE_reservation_LB, gbc);
    		        	
    		        	JLabel  MOD_RE_blank_LB = new JLabel("        ");
    		        	modFrame.add( MOD_RE_blank_LB);
    		        	gbc.gridx =1; gbc.gridy = 9; gb.setConstraints( MOD_RE_blank_LB, gbc);
    			        
    			        JLabel  MOD_RE_statut_reservation_LB = new JLabel("Statut");
    		        	modFrame.add( MOD_RE_statut_reservation_LB);
    		        	gbc.gridx =2; gbc.gridy = 7; gb.setConstraints( MOD_RE_statut_reservation_LB, gbc);

    		        // JTextField
    			        
    		        	JComboBox<String>  MOD_RE_statut_reservation_CC = new JComboBox<String>();
    			        for(String type : enumStatutReservation)
    			        {
    			        	 MOD_RE_statut_reservation_CC.addItem(type);
    			        }
    			         MOD_RE_statut_reservation_CC.setSelectedItem(content.get(0));
    			        modFrame.add( MOD_RE_statut_reservation_CC);
    			        gbc.gridx =2; gbc.gridy = 8; gb.setConstraints( MOD_RE_statut_reservation_CC, gbc);
    			        
    			        
    			        JButton startButton = new JButton("Quitter la fenêtre");
    			        modFrame.add(startButton);
    					gbc.gridx =2; gbc.gridy = 10; gb.setConstraints(startButton, gbc);
    			        
    			        JButton clearButton = new JButton("Vider les champs");
    			        modFrame.add(clearButton);
    					gbc.gridx =6; gbc.gridy = 10; gb.setConstraints(clearButton, gbc);
    			        
    					JButton sendButton = new JButton("Soumettre");
    					modFrame.add(sendButton);
    					gbc.gridx =7; gbc.gridy = 10; gb.setConstraints(sendButton, gbc);
    					
    					startButton.addActionListener(new ActionListener() {
    			            public void actionPerformed(ActionEvent e) {
    			            	modFrame.dispose();
    			             }
    			          });
    					
    					sendButton.addActionListener(new ActionListener() {
    			            public void actionPerformed(ActionEvent e) {
    			            	// Traitement du temps
    			            	String NULLDATETIME = "NULL";
    			            	String dhPaiement= NULLDATETIME;
    			            	String dhArrivee= NULLDATETIME;
    			            	String dhDepart = NULLDATETIME;
    			            			
    			            	LocalDateTime localDateTimePayment =  MOD_PA_dhPaiement_DTP.getDateTimeStrict();
    			            	LocalDateTime localDateTimeArrival =  MOD_VO_dhArrivee_DTP.getDateTimeStrict();
    			            	LocalDateTime localDateTimeDeparture =  MOD_VO_dhDepart_DTP.getDateTimeStrict();
    			            			

    			            	if(localDateTimePayment !=null)
    			            	{
    			            		dhPaiement= "'"+localDateTimePayment.toString()+"'";
    			            	}

    			            	if(localDateTimeArrival != null)
    			            	{
    			            		dhArrivee =  "'"+localDateTimeArrival.toString()+"'";
    			            	}

    			            	if(localDateTimeDeparture != null)
    			            	{
    			            		  dhDepart= "'"+localDateTimeDeparture.toString()+"'";
    			            	}
    			            				
    			            	dbm.getConnection();
    			            	dbm.updateBooking(id_booking, dhDepart,  MOD_VO_lieuDepart_TF.getText(), dhArrivee,  MOD_VO_lieuArrivee_TF.getText(),  MOD_VO_compagnie_CC.getSelectedItem().toString(),  MOD_VO_typeVol_CC.getSelectedItem().toString(),  MOD_PR_email_TF.getText(),  MOD_PR_lastName_TF.getText(),  MOD_PR_firstName_TF.getText(),  MOD_PR_phoneNumber_TF.getText(), dhPaiement,  MOD_PA_typePaiement_CC.getSelectedItem().toString(),  MOD_RE_statut_reservation_CC.getSelectedItem().toString());
    			            	model=dbm.getAll();
		            			fbmodel=dbm.getFB();
		            			dbm.closeConnection();
		            			if(mode==0)
		            			{
		            				table.setModel(model); table.style(dbm.gLocalDateTimeIndexTable);
		            			}
		            			else if(mode==3)
		            			{
		            				table.setModel(fbmodel); table.style(dbm.FBLocalDateTimeIndexTable);
		            			}
		            	
    			            	
    			            	modFrame.dispose();
    			            	showMessageDialog(modFrame,"Réservation modifiée avec succès","Réservation modifée avec succès",JOptionPane.INFORMATION_MESSAGE);
    			            		
    			             }
    			          });
    					
    					clearButton.addActionListener(new ActionListener() {
    				            public void actionPerformed(ActionEvent e) {
    				            	clear(modFrame);
    				             }
    				         
    					});

    	        
    	        modFrame.addWindowListener(new WindowAdapter() {
    	            public void windowClosing(WindowEvent we) {
    	            	modFrame.dispose();
    	            }
    	        });

    			modFrame.pack();
    			modFrame.setLocationRelativeTo(mainFrame);
    	        // Paramètres de la fenêtre de réservation
    	        modFrame.setBackground(new Color(189, 189, 211));
    	        modFrame.setVisible(true);   // Afficher la fenêtre de réservation
    }
    
    public void deleteBooking(final String id_booking)
    {
    	dbm.getConnection();
    	dbm.deleteBooking(id_booking);
    	table.setModel(dbm.getAll());
    	table.style(dbm.gLocalDateTimeIndexTable);
    	dbm.closeConnection();
    }
	
	// Ces variables correspondent aux énumérations dans la base de données
	public ArrayList<String> enumStatutReservation;
	public ArrayList<String> enumTypePaiement;
	public ArrayList<String> enumStatutVol;
	public ArrayList<String> enumTypeVol;
	public ArrayList<String> listCompany;
	private ArrayList<String> save;
	private MTable table;
	private DefaultTableModel model;
	private DefaultTableModel cmodel;
	private DefaultTableModel amodel;;
	private DefaultTableModel fbmodel;
	private int mode;
	
	public final int varcharmax;
	private int insert_id_client;
	private int insert_id_advisor;
	private String insert_str_client;
	private String insert_str_advisor;
	
	// Objet qui permet d'interagir avec la base de données
	public DBManager dbm;
}
