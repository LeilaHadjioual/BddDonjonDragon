import java.sql.Connection;

import java.sql.DriverManager;

/*pattern singleton permet d'établir une seule connection
pour la pouvoir réutiliser autant de fois que l'on veut,
mais reste la même connection*/

public class Connect {

    private static Connect co;

    //objet Connection importé par java
    private Connection conn;

    //------------constructeur-----------------
    public Connection ouvrir() {
        return conn;
    }

    //------------méthode pour récupérer l'instanciation de l'objet Connect-------------------
    public static Connect getInstance() {
        if (co == null) {
            co = new Connect();
        }
        return co;
    }

    //----------------méthode qui permet la connection--------------------
    public Connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/donjon_dragon";
            String user = "root";
            String passwd = "";
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            System.out.println("problème BDD : veuillez contacter l'administrateur");
        }
    }
}