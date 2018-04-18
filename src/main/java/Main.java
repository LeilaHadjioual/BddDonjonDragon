import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Connection conn = Connect.getInstance().ouvrir();
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver O.K.");
//            String url = "jdbc:mysql://localhost:3306/donjon_dragon";
//            String user = "root";
//            String passwd = "";
//            Connection conn = DriverManager.getConnection(url, user, passwd);
//            System.out.println("Connexion effective !");

            //objet statement fourni par l'objet Connection
            //Création d'un objet Statement qui permet d'exécuter les requêtes SQL
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

////------------------AFFICHER DES DONNEES en dur --------------------------------------------
//            L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = state.executeQuery("SELECT * FROM personnage");
//
//            //On récupère les MetaData
//            ResultSetMetaData resultMeta = result.getMetaData();
//            System.out.println("\n******************************************************************************************************");
//
//            //On affiche le nom des colonnes - boucle permettant de parcourir chaque ligne via la boucle for tant que l'objet ResultSet retourne des lignes de résultats
//            for (int i = 1; i <= resultMeta.getColumnCount(); i++)
//                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
//            System.out.println("\n******************************************************************************************************");
//
//            while (result.next()) { //La méthode next() permet de positionner l'objet sur la ligne suivante de la liste de résultats
//                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
//                    System.out.print("\t" + result.getObject(i).toString() + "\t |");
//                System.out.println("\n----------------------------------------------------------------------------------------");
//
////----------autre exemple lorsque l'on connait le type de données des colonnes créees-----------------------------------
////                System.out.print("\t" + result.getInt("cls_id") + "\t |");
////                System.out.print("\t" + result.getString("cls_nom") + "\t |");
////                System.out.println("\n---------------------------------");
//            }
//
////------------------MODIFIER DES DONNEES AVEC REQUETES PREPAREES-----------------------------
//            //requête préparée
//            //je crée la requête préparée
//            String query2 = " UPDATE  personnage SET nom =? WHERE nom = ?";
//            PreparedStatement prepare = conn.prepareStatement(query2);
//            //je remplace le 1er ? par le nom du personnage à créer
//            prepare.setString(1, "romain");
//            //je remplace le 2ème ? par le nom du personnage existant à enlever
//            prepare.setString(2, "test");
//            //j'exécute la requête
//            prepare.executeUpdate();
//
////---------------------INSERER DES DONNEES-----------------------------
////            String query3 = "INSERT INTO personnage VALUES (null, 'guerrier', 'test3', 9999, 500, 100, 'massue', 'oui')";
////            PreparedStatement inserer = conn.prepareStatement(query3);
//            state.executeUpdate("INSERT INTO personnage VALUES (null, 'guerrier', 'test3', 9999, 500, 100, 'massue', 'oui')");
//
////-----------------SUPPRIMER DES DONNEES--------------------------------
////            String query4 = "DELETE FROM personnage WHERE id = 6";
////            PreparedStatement supp = conn.prepareStatement(query4);
//            state.executeUpdate("DELETE FROM personnage WHERE id = 8");
//
//            //fermeture des objets
//            prepare.close();
////            inserer.close();
////            supp.close();
//            result.close();
//            state.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            //----------------------------via classe scanner---------------------------------------------------
            afficherPersonnage();

            System.out.println("Vous souhaitez \nafficher un personnage : tapez 1 \najouter un personnage : tapez 2\nmodifier un personnage : tapez 3 \nsupprimer un personnage : tapez 4");
            int choix = sc.nextInt();
            sc.nextLine();

            if (choix == 1) {
                System.out.println("Quel personnage voulez-vous afficher");
                int choixId = sc.nextInt();
                ResultSet afficherSelection = state.executeQuery("SELECT * FROM personnage WHERE id = " + choixId);
                ResultSetMetaData resultMeta2 = afficherSelection.getMetaData();
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM personnage WHERE id = ?");
                prep.setInt(1, choixId);
                afficherSelection.first();
                for (int i = 1; i <= resultMeta2.getColumnCount(); i++)
                    System.out.print("\t" + afficherSelection.getObject(i).toString() + "\t |");
                System.out.println("\n--------------------------------------------------------------------------");
                afficherSelection.close();
            } else if (choix == 2) {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO personnage VALUES (null,  ? ,?, ?,  ?,  ?,  ?,  ?)");
                System.out.println("Type de votre personnage : ");
                String type = sc.nextLine();
                insert.setString(1, type);
                System.out.println("Nom de votre personnage : ");
                String nom = sc.nextLine();
                insert.setString(2, nom);
                System.out.println("Image de votre personnage : ");
                String image = sc.nextLine();
                insert.setString(3, image);
                System.out.println("Niveau de vie de votre personnage :");
                int vie = sc.nextInt();
                insert.setInt(4, vie);
                System.out.println("Force d'attaque de votre personnage : ");
                int attaque = sc.nextInt();
                sc.nextLine();
                insert.setInt(5, attaque);
                System.out.println("Arme/sort de  votre personnage : ");
                String arme = sc.nextLine();
                insert.setString(6, arme);
                System.out.println("Bouclier/philtre de votre personnage : ");
                String bouclier = sc.nextLine();
                insert.setString(7, bouclier);
                insert.executeUpdate();
                insert.close();
            } else if (choix == 3) {
                System.out.println("Quel personnage voulez-vous modifier ?");
//                afficherPerso();
                int choixId = sc.nextInt();
                sc.nextLine();
                ResultSet afficher = state.executeQuery("SELECT * FROM personnage WHERE id = " + choixId);
                ResultSetMetaData resultMeta2 = afficher.getMetaData();
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM personnage WHERE id = ?");
                prep.setInt(1, choixId);
                afficher.first();

                for (int i = 1; i <= resultMeta2.getColumnCount(); i++)
                    System.out.print("\t" + afficher.getObject(i).toString() + "\t |");
                System.out.println("\n--------------------------------------------------------------------------");

                String query2 = " UPDATE  personnage SET nom =? WHERE id = ?";
                System.out.println("Quel nom voulez-vous lui donner ?");
                String newName = sc.nextLine();
                PreparedStatement prepare = conn.prepareStatement(query2);
                prepare.setString(1, newName);
                prepare.setInt(2, choixId);
                prepare.executeUpdate();
                afficher.close();
                prepare.close();
            } else if (choix == 4) {
                System.out.println("Quel personnage voulez-vous supprimer ?");
                afficherPersonnage();
                int deletePerso = sc.nextInt();
                String query3 = "DELETE FROM personnage WHERE id = ?";
                PreparedStatement delete = conn.prepareStatement(query3);
                delete.setInt(1, deletePerso);
                delete.executeUpdate();
                delete.close();
            }
            result.close();
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void afficherPersonnage() {
        try {
            Connection conn = Connect.getInstance().ouvrir();
//            Class.forName("com.mysql.jdbc.Driver");
////            System.out.println("Driver O.K.");
//            String url = "jdbc:mysql://localhost:3306/donjon_dragon";
//            String user = "root";
//            String passwd = "";
//            Connection conn = DriverManager.getConnection(url, user, passwd);

            //Création d'un objet Statement pour modifier la BDD avec requête normale ou requête préparée
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = state.executeQuery("SELECT id, nom FROM personnage");

            //On récupère les MetaData
            ResultSetMetaData resultMeta = result.getMetaData();
            System.out.println("\n**************************************************************");
            //On affiche le nom des colonnes (les indices de colonnes SQL commencent à 1 contrairement aux indices de tableau)
            for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
            System.out.println("\n***************************************************************");
            while (result.next()) {
                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + result.getObject(i).toString() + "\t |");
                System.out.println("\n-----------------------------------------------------------");
                if (result.isLast())
                    System.out.println("\t\t* DERNIER RESULTAT !\n");
            }
            result.close();
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}