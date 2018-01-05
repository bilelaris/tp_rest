package com.bilelaris;


import java.util.Scanner;

public class GestionPersonneTest {


    private static void showUsage() {
        System.out.println("1- Ajouter une personne");
        System.out.println("2- Supprimer personne");
        System.out.println("3- Afficher personne par nom");
        System.out.println("4- Afficher personne par id");
        System.out.println("5- Afficher la liste des personnes");
        System.out.println("6- Mettre à jour personne");
        System.out.println("7- Quitter");
    }



    public static void main(String[] args) throws Exception {
        Integer id;
        String nom;
        String prenom;
        Person personne;
        GestionPersonne gestionPersonne = new GestionPersonne();
        Scanner scan = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            showUsage();
            switch (scan.nextInt()) {
                case 1:
                    System.out.println("Id ?: ");
                    id = scan.nextInt();
                    System.out.println("Nom ?: ");
                    nom = scan.next();
                    System.out.println("Prénom ?: ");
                    prenom = scan.next();
                    personne =  gestionPersonne.addPerson(new Person(id, nom, prenom));
                    System.out.println(personne);
                    break;
                case 2:
                    System.out.println("Id ?: ");
                    id = scan.nextInt();
                    gestionPersonne.deletebByID(id);
                    break;
                case 3:
                    System.out.println("Nom ?: ");
                    nom = scan.next();
                    personne = gestionPersonne.getPersonByName(nom);
                    System.out.println(personne);
                    break;
                case 4:
                    System.out.println("Id ?: ");
                    id = scan.nextInt();
                    personne = gestionPersonne.getPersonByID(id);
                    System.out.println(personne);
                    break;
                case 5:
                    System.out.println(gestionPersonne.getPersons(null,null));
                    break;
                case 6:
                    System.out.println("Id ?: ");
                    id = scan.nextInt();
                    System.out.println("Nom ?: ");
                    nom = scan.next();
                    System.out.println("Prénom ?: ");
                    prenom = scan.next();
                    gestionPersonne.updatePerson(new Person(id, nom, prenom));
                    break;



                case 7:
                    loop = false;
            }
        }
        System.out.println("Bye !");
    }


}
