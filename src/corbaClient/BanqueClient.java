package corbaClient;

import javax.naming.Context;
import javax.naming.InitialContext;
import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            Context ctx = new InitialContext();
            Object ref = ctx.lookup("BK");
            IBanqueRemote stub = IBanqueRemoteHelper.narrow((org.omg.CORBA.Object) ref);

            stub.creerCompte(new Compte(1, 1000));
            stub.creerCompte(new Compte(2, 500));

            stub.verser(400, 1);
            Compte c1 = stub.getCompte(1);
            System.out.println("Compte " + c1.code + " : solde = " + c1.solde);

            stub.retirer(200, 2);
            Compte c2 = stub.getCompte(2);
            System.out.println("Compte " + c2.code + " : solde = " + c2.solde);

            System.out.println("--- Tous les comptes ---");
            for (Compte c : stub.getComptes()) {
                System.out.println("Compte " + c.code + " : solde = " + c.solde);
            }

            System.out.println("100 EUR = " + stub.conversion(100) + " DT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}