package com.bilelaris;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GestionPersonne {

    // declaration d'un seul object connection quon va utiliser pour toutes les methodes
    // histoire d'eviter l'instansiation d'une nouvelle connexion apres chaque appel de methode
    // si on instancie une nouvelle connexion a chaque fois et quon avait beaucoup d'utilisateurs, le serveur va crasher

    public  Connection connection = JdbcConnection.createConnection();


    public void updatePerson(Person person) throws SQLException {

            String query = "UPDATE personne SET nom = ? ,prenom=? WHERE id= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, person.getLastname());
            statement.setString(2,person.getFirstname());
            statement.setInt(3,person.getId());
            statement.executeUpdate();

        }


    public Person getPersonByID(int id) throws SQLException {
        Person person = null;
        String query = "select * FROM personne WHERE id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1,id);
        ResultSet result = st.executeQuery();
        if(result.next()){
            person = new Person();
            person.setId(result.getInt("id"));
            person.setLastname(result.getString("nom"));
            person.setFirstname(result.getString("prenom"));
        }
        return person;
    }

    public  Person getPersonByName(String name) throws SQLException {
        Person person = new Person();
        String query = "select * FROM personne WHERE nom like ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        if(result.next()){

            person.setId(result.getInt("id"));
            person.setLastname(result.getString("nom"));
            person.setFirstname(result.getString("prenom"));
        }
     return person;
    }

    /*
    Statement.RETURN_GENERATED_KEYS


    nous permettent de retourner l'ID attribuer automatiquement par mysql à la personne
    il faut avoir activer le autoIncrement lors de la création de la table

     */

    public Person addPerson(Person person) throws SQLException {
        String query = "INSERT INTO personne (nom, prenom) values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,person.getLastname());
        statement.setString(2,person.getFirstname());
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        return new Person(rs.getInt(1),person.getLastname(),person.getFirstname());

    }

    /*
    lorsque on fait une requette avec "WHERE nom like %ex", mysql va retourner toutes lignes qui commencent avc ex

     */

    public List<Person> getPersons(String lastname, String firstname) throws SQLException {
        if(lastname == null) lastname = "";
        if(firstname == null) firstname = "";
        String query = "SELECT * FROM personne WHERE nom like ? and prenom like ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,lastname+"%");
        statement.setString(2,firstname+"%");
        ResultSet result = statement.executeQuery();
        List<Person> list = new ArrayList<>();
        while (result.next()) {
            Person person = new Person();
            person.setId(result.getInt("id"));
            person.setLastname(result.getString("nom"));
            person.setFirstname(result.getString("prenom"));
            list.add(person);
        }
        return list;
    }

    public  void deletebByID(int id) throws SQLException {
        String query = "DELETE FROM personne WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        statement.executeUpdate();

    }


}
