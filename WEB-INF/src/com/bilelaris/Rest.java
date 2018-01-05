package com.bilelaris;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


/*
Apres avoir lu des manuels sur les conventions de nommage de endpoint rest
J'ai trouvé que la majorité des developpeurs font de la manierre ci dessous
c'est a dire au lieu de nommer deux paths different getpersons et addperson ! on laisse un seul endpoint qui est /persons
et on annote par @GET pour la methode pr extraire les personnes et @POST pour la methode d'ajour

donc pour ajouter on fait à travers notre client un POST dans le endpoint /persons
et pour consulter la la liste on fait un GET /persons
la meme chose pour DELETE et PUT

pour getByID jai ajouté un endpoint special /persons/id
j'ai ajouté aussi un path /person/name , pour répondre au cahier de charge du tp
mais il ya une solution que j'ai implémenté une autre solution qui est répandue dans les services REST
c'est d'ajouter un queryParam dans la meme methode qui retourne toutes les personnes
lastname et firstname, si l'un des deux est fourni le filtrage est effectué sinon on retourne tout


j'ai gérer les cas d'exeption par des messages d'erreur HTTP

400 : mauvaise requette
500 : erreur interne du serveur
404 : ressource introuvable


pour ce qui du type de données retournées,

au lieu d'instancier a chaque fois l'objet gson de google comme vu dans le cours
jai inclu dans le classpath le module jersey-media-json-binding  qui vient avec jersey

il suffit juste de précisé dans le @Produces que c du json
et lors du .entity(mon_objet).build(); il va automatiquement le transformer en json meme si c'est un arraylist





 */
@Path("/persons")
public class Rest {

    private GestionPersonne gestionPersonne = new GestionPersonne();




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnPersons(@QueryParam("lastname") String lastname,
                                  @QueryParam("firstname") String firstname) {
        try {
            List<Person> listOfPersons = gestionPersonne.getPersons(lastname, firstname);
            return Response.status(200).entity(listOfPersons).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @GET
    @Path("{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnPersonById(@PathParam("id") int id) {
        try {
            Person person = gestionPersonne.getPersonByID(id);
            if(person == null){
                return Response.status(404).build();
            }
            return Response.status(200).entity(person).build();
        } catch (SQLException e) {
            return Response.status(400).build();
        }
    }


    @GET
    @Path("{name:[A-Z][a-z]+}")
    public Response returnPersonByName(@PathParam("name") String name) {
        try {
            Person person = gestionPersonne.getPersonByName(name);
            if(person == null){
                return Response.status(404).build();
            }
            return Response.status(200).entity(person).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person){
        try {
            Person newPerson = gestionPersonne.addPerson(person);
            return Response.status(200).entity(newPerson).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deletePerson(@PathParam("id") int id){
        try {
            gestionPersonne.deletebByID(id);
            return Response.status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }


    /*
    si le nom ou le prenom n'est pas fourni lors de la mise à jour, cette methode va copier les valeurs de l'ancien objet
     */

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person){
        try {
            Person newPerson = gestionPersonne.getPersonByID(person.getId());
            if (person.getLastname()==null) {person.setLastname(newPerson.getLastname());}
            if (person.getFirstname()==null) {person.setFirstname(newPerson.getFirstname());}
            if (newPerson==null) {return Response.status(404).entity(newPerson).build();}
            else {gestionPersonne.updatePerson(person);}
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
        return Response.status(200).build();}


}