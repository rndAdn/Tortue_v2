package commande;


import liste.ListeVariables;

public class Down extends Commande {




    @Override
    public void execute(String commande, ListeVariables listeVariables) {
        if(!commande.equalsIgnoreCase("down")){
            getListeHistorique().addToList(commande,this.ErrorToString("1",commande.split(" ",2)[0]));
            return;
        }
        getCurseur().setD(270);
        getListeHistorique().addToList(commande,"");
    }
}