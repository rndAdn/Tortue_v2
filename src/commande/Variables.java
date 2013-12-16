package commande;

import algo.Convert;
import algo.Verification;
import liste.ListeVariables;
import liste.ListeVariables.ObjetVariables;

import javax.swing.*;

public class Variables extends Commande {

    //TODO: Aojuter la suppression des variables
	
	private ListeVariables listeVariables;
	
	public Variables(ListeVariables listeVariables) {
		this.listeVariables = listeVariables;
	}
	
	@Override
	public String execute(String[] commande){
		if (commande[0].charAt(0) == '_') { //affectation
            String cmd="";
            for (String s : commande) cmd+=s;
			String tabArg[] = cmd.split("=");
            /*if( commande[1].equalsIgnoreCase("remove")) {
                listeFonctions.removeFonction(fonc);
            }*/
			if (tabArg.length!=2) {
				return "1";
			}
			ObjetVariables var = Convert.get_Variable(listeVariables,tabArg[0].substring(1).trim());
			if (var != null) {
				return affectation(var,tabArg[1].trim());
			}

		}
		else{ //VAR nom (Déclaration)
			// TODO: syntaxe des noms de variable
            ObjetVariables var = listeVariables.getVar(commande[1]);
            if (var != null)    {
                int option = JOptionPane.showConfirmDialog(null,
                        "La variable "+commande[1]+" éxiste déjà. Voulez-vous la remplacer?", "Tortue",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) affectation(var,"0");
                else return "";
            }
            else{
                declaration(commande[1]);
            }

		}
		return "";

	}

	private void declaration(String nom_Variable){
		this.listeVariables.add(nom_Variable);
	}

	private String affectation(ObjetVariables var, String sAffectation){
		int valeur = 0;
		if( sAffectation.charAt(0) != '('){
			try{
				valeur = Integer.parseInt(sAffectation);
				var.setValeur_Variable(sAffectation);

			}catch (NumberFormatException e1){
				return sAffectation+"n'est pas une affectation valide";
			}
		}
		else if(Verification.bienP(sAffectation)){
			boolean canContinue = true;
			while(Verification.parenthese(sAffectation) && canContinue){
				String ss = Convert.subParenthese(sAffectation);
				String subS[] = ss.split(" ");
				String cal = Convert.calculeTab(subS,listeVariables);
				try{
					int i = Integer.parseInt(cal);
					sAffectation = sAffectation.replace("("+ss+")",""+i);
				}catch (NumberFormatException e1){
					canContinue = false;
					System.out.println("Impossible de faire le calcul ("+ss+") n'est pas un nombre");
				}			
			}
			try{
				valeur = Integer.parseInt(sAffectation);
				var.setValeur_Variable(sAffectation);

			}catch (NumberFormatException e1){
				return sAffectation+"n'est pas une affectation valide";
			}
		}
		else {
			return sAffectation+"n'est pas une affectation valide";
		}
		return "";
	}
	
}
