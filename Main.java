import java.util.ArrayList;

import java.util.Scanner;


public class Main {
    public static void main (String[]args){
        ArrayList <Argument> listeArgument = new ArrayList<Argument>();
        boolean boucle=true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Combien d'argument y a t il dans le débat");
        int nb = sc.nextInt();
        int i=0;
        while (i<nb){
            listeArgument.add(new Argument("A"+i)); // on crée les arguments et on les rentre dans la liste des arguments
            i++;
        }
        //System.out.println(listeArgument);

        while (boucle){
            System.out.println(listeArgument);
            System.out.println("1) Ajouter une contradiction \n 2) Fin");
            int choix = sc.nextInt(); // On sticke ici le choix de l'utilistateur
            if (choix == 1){ //Ajouter une contradiction

                System.out.println("Entrez l'argument cible puis l'argument qu'il contredit ex : A0 A1");
                String strDepart = sc.next();
                String strArrivee = sc.next();
                
                Argument argDepart=null;
                Argument argArrivee=null;
                
                for(i=0;i<listeArgument.size();i++){ //Dans cette boucle, on ajoute une contradiction entre les arguments donné par l'utilisateur
                    if (listeArgument.get(i).isEqual(strDepart)){ // on cherche quel argument est associé au titre rentreé
                        argDepart= listeArgument.get(i);
                    }
                    if (listeArgument.get(i).isEqual(strArrivee)){ // on cherche quel argument est associé au titre rentreé
                        argArrivee= listeArgument.get(i);
                    }
                    
                    
                }
                if (argArrivee!=null && argDepart!=null){//on crée une contradiction entre les titres rentrés

                    argDepart.addContradiciton(new Contradiction(argDepart, argArrivee));
                    argArrivee.addParent(argDepart);
                }
                else{
                    System.out.println("Les arguments rentrés n'existe pas");
                }
                
                
                
                
            }

            if (choix == 2 ){ //FIN
                boucle = false;
            }




        }

        boucle = true;
        while(boucle){
            System.out.println("1) Ajouter un argument \n 2)retirer un argument \n 3) Verifier la solution \n 4) fin");
            int choix = sc.nextInt();
            boolean ajout = false;
            String argu ;

            if (choix ==1){//ajouter un argument
                System.out.println(listeArgument);
            	System.out.println("Donnez l'argument que vous voulez ajouter :");
            	argu = sc.next();
            	for(int j = 0 ; j<listeArgument.size();j++) {
            		if (listeArgument.get(j).isEqual(argu)) {
            			ajout=true;
            	    }
                }
                if(ajout){ // Si ajout==true, alors argument deja ajouté a la liste
                    System.out.println("Argument déjà ajouté");}
                else {
                    listeArgument.add(new Argument(argu));	
                }
            System.out.println(listeArgument);
            }
            if (choix ==2){// supprimer un argument
                boolean refus = false;
                System.out.println(listeArgument);
            	System.out.println("Donnez l'argument que vous voulez supprimer :");
            	argu = sc.next();
            	for(int k = 0 ; k<listeArgument.size();k++) {
            		if (listeArgument.get(k).isEqual(argu)) {
                        refus = true;
            			listeArgument.remove(k);
            		
            	    } 
                }
                if(!refus){
                    System.out.println("Argument n'est pas dans la liste ou déjà supprimé");
                }

            System.out.println(listeArgument);
            }

            if (choix ==3){// Verifier la solution
                ArrayList<Argument> solAdmissible = new ArrayList<>(); //Stock les arguments admissibles
                int idxSol = 0;
                boolean incorrect = false;
                Argument premArg = null ;
                Argument deuxArg = null;

                //Si l'ensemble est vide => solution admissible
                if( listeArgument.isEmpty() ){
                    System.out.println("Solution admissible 1");
                }

                //Rechecher si 2 arguments se contredisent
                for( int idx1=0 ; idx1<listeArgument.size() ; idx1++ ){
                    for( int idx2=1 ; idx2<listeArgument.size() ; idx2++ ){
                        if( listeArgument.get(idx1).getContradiction(idx2)!=null && listeArgument.get(idx2).getContradiction(idx1)!=null ){
                            incorrect = true;
                            premArg = listeArgument.get(idx1);
                            deuxArg = listeArgument.get(idx2);
                        }
                        else { // Sinon on stock le 1er argument dans la solution admissible
                            while(idxSol<solAdmissible.size()){ // Chercher l'index pour insérer les arguments
                                idxSol++;
                            }
                            solAdmissible.add(idxSol, listeArgument.get(idx1));
                        }
                    }
                }
                if( incorrect )
                    System.out.println(premArg +" et "+ deuxArg + " se contredisent");
                    break; //Permet de sortir des boucles

                // Rechercher si un argument n'est pas défendu face à toutes ses contradictions
                for( int idx1=0 ; idx1<listeArgument.size() ; idx1++ ){ // idx1 = argument
                    for( int idx2=0 ; idx2<listeArgument.get(idx1).getlistContradiction().size() ; idx2++){ // idx2 = contradiction de idx1
                        if( listeArgument.get(idx1).getContradiction(idx2).getArrivee() ){
                            incorrect = true;
                            premArg = listeArgument.get(idx1);
                            break;
                        }
                    }
                    if(incorrect) break; //Sortie de boucle
                }               
                if(incorrect){
                    System.out.println(premArg + " non défendu");
                } 
                else{
                    System.out.println("Solution admissible 2");
                }

            }

            if (choix ==4){ //Fin
                boucle = false;
            }
            
      }sc.close();
        
    
    }
    
}
