package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// Classe chargé de la connection et des interaion avec a base de donné
public class DBManager {
	
	// Déclaration des informations de connexion à la base de données PostgreSQL : 
	// Les variables sont déclarées avec le mot-clé 'static' car elles n'ont pas besoin de changer.
	public final String jdbcURL = "jdbc:postgresql://localhost:5432/projet_isn_lkm";
	public final String username = "lkm"; // Nom d'utilisateur de la base de données
	public final String password = "abc"; // Mot de passe de l'utilisateur
	
	public int[] gLocalDateTimeIndexTable;
	public int[] flightLocalDateTimeIndexTable;
	public int[] bookingLocalDateTimeIndexTable;
	public int[] FBLocalDateTimeIndexTable;
	
	public Connection connection;

	public DBManager()
	{
	
        connection = null;
		gLocalDateTimeIndexTable = new int[]{3,4,10,12};
		flightLocalDateTimeIndexTable =new int[]{2,4};
		bookingLocalDateTimeIndexTable = new int[]{2,3};
		FBLocalDateTimeIndexTable = new int[]{2,4,9,15};
		
        // Test de la connexion avec la base de données
        getConnection();
        closeConnection();
		
	}	
	
	public boolean getConnection()
	{
		try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            return true;
        } catch (Exception e) {
            // Gestion des exceptions
        	System.out.println("Échec de la connexion");
        	JOptionPane.showMessageDialog(null,"Erreur de connection : " + e.getMessage(),"Erreur de connection",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
	}
	
	private static void handleSQLException(SQLException e) {
		JOptionPane.showMessageDialog(null,"Erreur : " + e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
    }
	
	public boolean closeConnection()
	{
		// Fermeture de la connexion
        try {
            if (connection != null) {
                connection.close(); connection = null;
                return true;
            }
            return false;
        } catch (Exception e) {
        	System.out.println("Erreur de déconnection");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Erreur de déconnection : " + e.getMessage(),"Erreur de déconnection",JOptionPane.ERROR_MESSAGE);
            return false;
        }
	}
	
	private ArrayList<String> getRow(ResultSet resultSet)
	{
        ArrayList<String> row = new ArrayList<String>(); 
     	
		try {
			// Récupération du nombre de colonnes
			ResultSetMetaData metadata = resultSet.getMetaData();  
	        int numberOfColumns;
			numberOfColumns = metadata.getColumnCount();
			
	        // Parcours de toutes les colonnes
	        for (int i = 1; i <= numberOfColumns; i++) {
	            row.add(resultSet.getString(i));
	        }
		
		} catch (SQLException e) {
			e.printStackTrace();
			handleSQLException(e);
		} 



        return row;
	}

	
	public ArrayList<String> getClientByEmail(String email)
	{
		ArrayList<String> row = null;
		    
		 // Requête SQL pour récupérer les valeurs
		 String query = "SELECT pk_id_client,nom_client,prenom_client,numero_telephone_client,adresse_client FROM CLIENTS where email_client='"+email+"'";
		    
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
			 // Si il y a un element
			 if (resultSet.next() ) {    
				    row=getRow(resultSet);
			 } 
		 // Parcours des résultats
		
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		    
		 return row;
	}
	
	public ArrayList<String> getAdvisorByEmail(String email)
	{
		ArrayList<String> row = null;
		    
		 // Requête SQL pour récupérer les valeurs
		 String query = "SELECT  pk_id_conseiller,nom_conseiller,prenom_conseiller,numero_telephone_conseiller FROM CONSEILLERS where email_conseiller='"+email+"'";
		    
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
			 // Si il y a un element
			 if (resultSet.next() ) {    
				    row=getRow(resultSet);
			 } 
		 // Parcours des résultats
		
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		    
		 return row;
	}
	
	public ArrayList<String> getClientById(String id_client)
	{
		ArrayList<String> row = null;
		    
		 // Requête SQL pour récupérer les valeurs
		 String query = "SELECT nom_client,prenom_client,email_client,numero_telephone_client,adresse_client FROM CLIENTS where pk_id_client="+id_client;
		    
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
			 // Si il y a un element
			 if (resultSet.next() ) {    
				    row=getRow(resultSet);
			 } 
		 // Parcours des résultats
		
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		    
		 return row;
	}
	
	public int updateClient(String id_client,String lastName,String firstName, String email, String phoneNumber, String adress)
	{
		String query = "UPDATE CLIENTS SET"
		 		+ " (nom_client,prenom_client,email_client,numero_telephone_client, adresse_client)"
		 		+ " = ('"+lastName+"','"+firstName+"','"+email+"','"+phoneNumber+"','"+adress+"')"
		 		+ " WHERE pk_id_client="+id_client;
		
		try (Statement statement = connection.createStatement();)  { 
			 return statement.executeUpdate(query);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 	return 0;
		 }
	}
	
	public int updateAdvisor(String id_advisor,String lastName,String firstName, String email, String phoneNumber)
	{
		String query = "UPDATE CONSEILLERS SET"
		 		+ " (nom_conseiller,prenom_conseiller,email_conseiller,numero_telephone_conseiller)"
		 		+ " = ('"+lastName+"','"+firstName+"','"+email+"','"+phoneNumber+"')"
		 		+ " WHERE pk_id_conseiller="+id_advisor;
		
		try (Statement statement = connection.createStatement();)  { 
			 return statement.executeUpdate(query);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 	return 0;
		 }
	}
	
	public ArrayList<String> getAdvisorById(String id_advisor)
	{
		ArrayList<String> row = null;
	    
		 // Requête SQL pour récupérer les valeurs
		 String query = "SELECT  nom_conseiller,prenom_conseiller,email_conseiller,numero_telephone_conseiller FROM CONSEILLERS where pk_id_conseiller="+id_advisor;
		    
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
			 // Si il y a un element
			 if (resultSet.next() ) {    
				    row=getRow(resultSet);
			 } 
		 // Parcours des résultats
		
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		    
		 return row;
	}
	
	// Insère les données relatives au client dans la BDD
	public int insertClient(String email, String lastName, String firstName, String phoneNumber, String adress)
	{
		 String verifQuery = "SELECT 1 FROM CLIENTS where email_client='"+email+"'";
		
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(verifQuery)) {
			 // Si il y a un element
			 if (resultSet.isBeforeFirst() ) {    
				 return -1;
			 } 
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		
		
		 // Requête SQL d'insertion
		 String query = "INSERT INTO CLIENTS(email_client,nom_client,prenom_client,numero_telephone_client,adresse_client) VALUES ('"+email+"','"+lastName+"','"+firstName+"','"+phoneNumber+"','"+adress+"') RETURNING pk_id_client;";
		  
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query))  { 
		 	 // Si un compte estt crée nous garons l'id
			 if (resultSet.next()) {
		            return resultSet.getInt("pk_id_client");
			 }
		 } catch (SQLException e) {
		 e.printStackTrace();
		 return 0;
		 }
		 
		 return 0;
	}
	
	// Insère les données relatives au client dans la BDD
	
	public int insertAdvisor(String email, String lastName, String firstName, String phoneNumber)
		{
			 String verifQuery = "SELECT 1 FROM CONSEILLERS where email_conseiller='"+email+"'";
			
			 // Exécution de la requête
			 try (Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery(verifQuery)) {
				 // Si il y a un element
				 if (resultSet.isBeforeFirst() ) {    
					 return -1;
				 } 
			 } catch (SQLException e) {
			 e.printStackTrace();
			 }
			
			
			 // Requête SQL d'insertion
			 String query = "INSERT INTO CONSEILLERS(email_conseiller,nom_conseiller,prenom_conseiller,numero_telephone_conseiller) VALUES ('"+email+"','"+lastName+"','"+firstName+"','"+phoneNumber+"') RETURNING pk_id_conseiller;";
			  
			 // Exécution de la requête
			 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query))  { 
			 	 // Si un compte estt crée nous garons l'id
				 if (resultSet.next()) {
			            return resultSet.getInt("pk_id_conseiller");
				 }
			 } catch (SQLException e) {
			 e.printStackTrace();
			 return 0;
			 }
			 
			 return 0;
		}
		
	public int updateBooking(String id_booking,
			String departureDateTime, String departureLocation, String arrivalDateTime,String arrivalLocation,
			String airline,String flightType,
			String email,String lastName,String firstName,String phoneNumber,
			String paymentDateTime,String paymentType, 
			String reservationStatus)
	{

		 String queryReservations = "UPDATE RESERVATIONS SET"
		 		+ " (dh_paiement,type_paiement,statut,email_reservateur,nom_reservateur,prenom_reservateur,numero_telephone_reservateur)"
		 		+ " = ("+paymentDateTime+",'"+paymentType+"','"+reservationStatus+"','"+email+"','"+lastName+"','"+firstName+"','"+phoneNumber+"')"
		 		+ " WHERE pk_id_reservation="+id_booking+" RETURNING id_vol;";
		  
		 int id_vol = 0;

		 // Exécution de la requête
		 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryReservations) )  { 
				 if(resultSet.next())
				 {
				     id_vol=resultSet.getInt("id_vol");
				 }
		 } catch (SQLException e) {
		 System.out.println(queryReservations);
		 e.printStackTrace();
		 return 0;
		 }
		
		 String queryVols = "UPDATE VOLS SET (dh_depart,lieu_depart,dh_arrivee,lieu_arrivee,compagnie,type_vol)"
		 		+ " = ("+departureDateTime+",'"+departureLocation+"',"+arrivalDateTime+",'"+arrivalLocation+"','"+airline+"','"+flightType+"') WHERE pk_id_vol="+id_vol;

		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();)  { 
		 	 return statement.executeUpdate(queryVols);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 return 0;
		 }
		 
	}
	
	public ArrayList<String> getBookingById(final String id)
	{
		ArrayList<String> content = null;
		
		String query = "SELECT statut, " // 0
			    + "to_char(dh_paiement, 'YYYY-MM-DD\"T\"HH24:MI:SS'), " // 1
			    + "type_paiement, " // 2
			    + "nom_reservateur, " // 3
			    + "prenom_reservateur, " // 4
			    + "email_reservateur, " // 5
			    + "numero_telephone_reservateur, " // 6
			    + "to_char(dh_depart, 'YYYY-MM-DD\"T\"HH24:MI:SS'), " // 7
			    + "lieu_depart, " // 8
			    + "to_char(dh_arrivee, 'YYYY-MM-DD\"T\"HH24:MI:SS'), " // 9
			    + "lieu_arrivee, " // 10
			    + "type_vol, " // 11
			    + "compagnie " // 12
			    + "FROM RESERVATIONS r "
			    + "INNER JOIN VOLS v ON r.id_vol = v.pk_id_vol "
			    + "WHERE pk_id_reservation="+id;
		
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
			 // Si il y a un element
			 if (resultSet.next() ) {    
				 content=getRow(resultSet);
			 } 
		
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		
		return content;
	}
		
	public DefaultTableModel getBooking()
	{
		 String query = "SELECT pk_id_reservation AS \"identifiant de la réservation\","
		 		+ " to_char(date_reservation, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de la réservation\","
		 		+ " to_char(dh_paiement, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de paiement\","
		 		+ " statut AS \"statut de la réservation\","
		 		+ " type_paiement AS \"type de paiement\","
		 		+ " nom_reservateur AS \"nom du réservateur\","
		 		+ " prenom_reservateur AS \"prénom du réservateur\","
		 		+ " email_reservateur AS \"email du réservateur\","
		 		+ " numero_telephone_reservateur AS \"numéro du réservateur\""
		 		+ " FROM RESERVATIONS r ORDER BY r.pk_id_reservation DESC LIMIT 1000";
		 
		 return getModelFromResult(query,bookingLocalDateTimeIndexTable);
	}
	
	public DefaultTableModel getAdvisor()
	{
		 // Requête SQL d'insertion
		 String query = "SELECT pk_id_conseiller AS \"identifiant du conseiller\",nom_conseiller AS \"nom du conseiller\",prenom_conseiller AS \"prénom du conseiller\",email_conseiller AS \"email du conseiller\",numero_telephone_conseiller AS \"numéro de téléphone du conseiller\" FROM CONSEILLERS c ORDER BY c.pk_id_conseiller DESC LIMIT 1000";
		 
		 return getModelFromResult(query,null);
	}
	
	public DefaultTableModel getClient()
	{
		 // Requête SQL d'insertion
		 String query = "SELECT pk_id_client AS \"identifiant du client\", nom_client AS \"nom du client\", prenom_client AS \"prénom du client\", email_client AS \"email du client\", numero_telephone_client AS \"numéro de téléphone du client\", adresse_client AS \"adresse du client\" FROM CLIENTS ORDER BY pk_id_client DESC LIMIT 1000";
		 
		 return getModelFromResult(query,null);
	}
	
	public DefaultTableModel getFlight()
	{
		String query = "SELECT pk_id_vol AS \"identifiant du vol\","
 		+ " to_char(dh_depart, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de départ\","
 		+ " lieu_depart AS \"lieu de départ\","
 		+ " to_char(dh_arrivee, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date d'arrivée\","
 		+ " lieu_arrivee AS \"lieu d'arrivée\","
 		+ " type_vol AS \"type de vol\","
 		+ " compagnie AS \"compagnie aérienne\""
 		+ " FROM VOLS ORDER BY pk_id_vol DESC LIMIT 1000";
		 
		 return getModelFromResult(query,flightLocalDateTimeIndexTable);
	}
	
	
	
	public DefaultTableModel getModelFromResult(String sql,int[] localDateTimeIndexTable)
	{
		 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql))  { 
			    ResultSetMetaData metaData = resultSet.getMetaData();
		        Vector<String> columnNames = new Vector<String>();
		        int columnCount = metaData.getColumnCount();
		        for (int column = 1; column <= columnCount; column++) {
		            columnNames.add(metaData.getColumnName(column));
		        }

		       
		        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		        while (resultSet.next()) {
		            Vector<Object> vector = new Vector<Object>();
		            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		            	if(contains(localDateTimeIndexTable,columnIndex))
		            	{
			            	if(resultSet.getObject(columnIndex) != null)
			            	{
			            		LocalDateTime localDateTime = LocalDateTime.parse(resultSet.getObject(columnIndex).toString());
			                	vector.add(localDateTime);
			            	}
		            		else
		            		{
		            			vector.add(null);
		            		}
		            	}
		            	else
		            	{
		            	vector.add(resultSet.getObject(columnIndex));
		            	}
		            }
		            data.add(vector);
		        }

		        return new DefaultTableModel(data, columnNames);
		        
		 } catch (SQLException e) {
			 e.printStackTrace();
			 	return null;
		 }
	}
	
	public DefaultTableModel getAll()
	{
		 // Requête SQL d'insertion
		 String query = "SELECT pk_id_reservation AS \"identifiant de la réservation\","
		 		+ " statut AS \"statut de la réservation\","
		 		+ " to_char(date_reservation, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de la réservation\","
		 		+ " to_char(dh_paiement, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de paiement\","
		 		+ " type_paiement AS \"type de paiement\","
		 		+ " nom_reservateur AS \"nom du réservateur\","
		 		+ " prenom_reservateur AS \"prénom du réservateur\","
		 		+ " email_reservateur AS \"email du réservateur\","
		 		+ " numero_telephone_reservateur AS \"numéro de téléphone du réservateur\","
		 		+ " to_char(dh_depart, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de départ\","
		 		+ " lieu_depart AS \"lieu de départ\","
		 		+ " to_char(dh_arrivee, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date d'arrivée\","
		 		+ " lieu_arrivee AS \"lieu d'arrivée\","
		 		+ " type_vol AS \"type de vol\","
		 		+ " compagnie AS \"compagnie aérienne\","
		 		+ " nom_conseiller AS \"nom du conseiller ayant validé la réservation\","
		 		+ " prenom_conseiller AS \"prénom du conseiller ayant validé la réservation\","
		 		+ " email_conseiller AS \"email du conseiller ayant validé la réservation\","
		 		+ " numero_telephone_conseiller AS \"numéro de téléphone du conseiller ayant validé la réservation\","
		 		+ " nom_client AS \"nom du client\","
		 		+ " prenom_client AS \"prénom du client\","
		 		+ " email_client AS \"email du client\","
		 		+ " numero_telephone_client AS \"numéro de téléphone du client\","
		 		+ " cl.pk_id_client AS \"identifiant du client\","
		 		+ " co.pk_id_conseiller AS \"identifiant du conseiller\","
		 		+ " v.pk_id_vol AS \"identifiant du vol\""
		 		+ "  FROM RESERVATIONS r INNER JOIN VOLS v ON r.id_vol = v.pk_id_vol"
		 		+ " INNER JOIN CONSEILLERS co ON r.id_conseiller = co.pk_id_conseiller"
		 		+ " INNER JOIN CLIENTS cl ON cl.pk_id_client=r.id_client ORDER BY r.pk_id_reservation DESC LIMIT 10000";
		 
 
		return getModelFromResult(query,gLocalDateTimeIndexTable);
	}
	
	public DefaultTableModel getFB()
	{
		// Requête SQL d'insertion
				 String query = "SELECT pk_id_reservation AS \"identifiant de la réservation\","
					 	+ " to_char(dh_depart, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de départ\","
					 	+ " lieu_depart AS \"lieu de départ\","
					 	+ " to_char(dh_arrivee, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date d'arrivée\","
					 	+ " lieu_arrivee AS \"lieu d'arrivée\","
					 	+ " type_vol AS \"type de vol\","
					 	+ " compagnie AS \"compagnie aérienne\","
				 		+ " statut AS \"statut de la réservation\","
				 		+ " to_char(dh_paiement, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de paiement\","
				 		+ " type_paiement AS \"type de paiement\","
				 		+ " nom_reservateur AS \"nom du réservateur\","
				 		+ " prenom_reservateur AS \"prénom du réservateur\","
				 		+ " email_reservateur AS \"email du réservateur\","
				 		+ " numero_telephone_reservateur AS \"numéro de téléphone du réservateur\","
				 		+ " to_char(date_reservation, 'YYYY-MM-DD\"T\"HH24:MI:SS') AS \"date de la réservation\""
				 		+ " FROM RESERVATIONS r INNER JOIN VOLS v ON r.id_vol = v.pk_id_vol"
				 		+ " ORDER BY r.pk_id_reservation DESC LIMIT 10000";
				 
		 
				return getModelFromResult(query,FBLocalDateTimeIndexTable);
	}
	
	public int deleteBooking(final String id_booking)
	{
		// Suupression de la réservation associée à l'id
		String queryRESERVATIONS = "DELETE FROM RESERVATIONS WHERE pk_id_reservation="+id_booking+" RETURNING id_vol";
		int id_vol;
		
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryRESERVATIONS))  { 
		 	 // Récupération de l'id de vo
			 if (resultSet.next()) {
		            id_vol=resultSet.getInt("id_vol");
			 }
			 else
				 return 0;
		 } catch (SQLException e) {
		 e.printStackTrace();
		 	return 0;
		 }
		
		String queryVOLS = "DELETE FROM VOLS WHERE pk_id_vol="+id_vol;
		
		 // Suppression du vol associée à la réservation
		try (Statement statement = connection.createStatement();)  { 
			 return statement.executeUpdate(queryVOLS);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 	return 0;
		 }
	}
	
	
	
	public int deleteClient(final String id_client)
	{
		String queryRESERVATIONS = "DELETE FROM RESERVATIONS WHERE id_client="+id_client+" RETURNING id_vol";
		int id_vol;
		
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryRESERVATIONS))  { 
		 	 // Récupération de l'id de vol
			 while (resultSet.isAfterLast()) {
					 resultSet.next();
			         id_vol=resultSet.getInt("id_vol");
			            
			         String queryVOLS = "DELETE FROM VOLS WHERE pk_id_vol="+id_vol;
			            
			         if(statement.executeUpdate(queryVOLS)==0)
			            return 0;
			}

		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		
		String queryCLIENTS = "DELETE FROM CLIENTS WHERE pk_id_client="+id_client;
		
		 // Suppression du vol associée à la réservation
		try (Statement statement = connection.createStatement();)  { 
			 return statement.executeUpdate(queryCLIENTS);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 	return 0;
		 }
	}
	
	public int deleteAdvisor(final String id_advisor)
	{
		String queryRESERVATIONS = "DELETE FROM RESERVATIONS WHERE id_conseiller="+id_advisor+" RETURNING id_vol";
		int id_vol;
		
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryRESERVATIONS))  { 
		 	 // Récupération de l'id de vol
			 while (resultSet.isAfterLast()) {
				 	resultSet.next();
		            id_vol=resultSet.getInt("id_vol");
		            
		            String queryVOLS = "DELETE FROM VOLS WHERE pk_id_vol="+id_vol;
		            
		            if(statement.executeUpdate(queryVOLS)==0)
		            	return 0;
			 }

		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 }
		
		String queryCONSEILLERS = "DELETE FROM CONSEILLERS WHERE pk_id_conseiller="+id_advisor;
		
		 // Suppression du vol associée à la réservation
		try (Statement statement = connection.createStatement();)  { 
			 return statement.executeUpdate(queryCONSEILLERS);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 	return 0;
		 }
	}
	
	public static boolean contains(int[] array, int value) {
		if(array !=null)
		{
			for (int i : array) {
				if (i == value) {
					return true;
				}
			}
		}
	return false;
	}
		
	public int insertBooking(int id_client,int id_advisor,
			String departureDateTime, String departureLocation, String arrivalDateTime,String arrivalLocation,
			String airline,String flightType,
			String email,String lastName,String firstName,String phoneNumber,
			String paymentDateTime,String paymentType, 
			String reservationStatus)
	{
		 
		 String queryVols = "INSERT INTO VOLS(dh_depart,lieu_depart,dh_arrivee,lieu_arrivee,compagnie,type_vol) VALUES ("+departureDateTime+",'"+departureLocation+"',"+arrivalDateTime+",'"+arrivalLocation+"','"+airline+"','"+flightType+"') RETURNING pk_id_vol;";

		 int id_vol;
		 
		 // Exécution de la requête
		 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryVols))  { 
		 	 // Si un compte estt crée nous garons l'id
			 if (resultSet.next()) {
		            id_vol=resultSet.getInt("pk_id_vol");
			 }
			 else
				 return -1;
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 return -1;
		 }
		 
		 String queryReservations = "INSERT INTO RESERVATIONS(id_client,id_conseiller,id_vol,dh_paiement,type_paiement,statut,email_reservateur,nom_reservateur,prenom_reservateur,numero_telephone_reservateur) VALUES ('"+Integer.toString(id_client)+"','"+Integer.toString(id_advisor)+"','"+Integer.toString(id_vol)+"',"+paymentDateTime+",'"+paymentType+"','"+reservationStatus+"','"+email+"','"+lastName+"','"+firstName+"','"+phoneNumber+"') RETURNING pk_id_reservation;";
		  

		 // Exécution de la requête
		 try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(queryReservations) )  { 
				 if(resultSet.next())
				 {
					 return resultSet.getInt("pk_id_reservation");
				 }
				 else
				 {
					 return -1;
				 }
		 } catch (SQLException e) {
		 e.printStackTrace();
		 handleSQLException(e);
		 return -1;
		 }

	}
		
	// Renvoie sous forme de ArrayList les labels associés au type d'énumération dans la base du projet, 
	// sans avoir à connaître le nom de l'énumération
	public ArrayList<String> getEnum(final String ENUM_NAME) {
	    ArrayList<String> set = new ArrayList<String>();
	    
	    // Requête SQL pour récupérer les valeurs de l'énumération ; Spécifique à PostgreSQL
	    String query = "SELECT unnest(enum_range(NULL::" + ENUM_NAME + "))";
	    
	    // Exécution de la requête
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        // Parcours des résultats
	        while (resultSet.next()) {
	            String label = resultSet.getString(1); // Récupère la première colonne (les valeurs de l'énumération)
	            set.add(label);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        handleSQLException(e);
	    }
	    
	    return set;
	}
	
	public ArrayList<String> getDistinctCompany() {
		ArrayList<String> set = new ArrayList<String>();
		
		 String query = "SELECT DISTINCT compagnie FROM VOLS WHERE compagnie IS NOT NULL AND compagnie != '' LIMIT 100";
		    
		    // Exécution de la requête
		    try (Statement statement = connection.createStatement();
		         ResultSet resultSet = statement.executeQuery(query)) {

		        // Parcours des résultats
		        while (resultSet.next()) {
		            String label = resultSet.getString(1); // Récupère la première colonne (les valeurs de l'énumération)
		            set.add(label);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        handleSQLException(e);
		    }
		    
		    return set;
	}
}
