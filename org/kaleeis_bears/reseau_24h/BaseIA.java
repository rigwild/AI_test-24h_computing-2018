package org.kaleeis_bears.reseau_24h;

import java.io.BufferedReader;
import java.io.PrintWriter;

import org.kaleeis_bears.reseau_24h.Main;
import org.kaleeis_bears.reseau_24h.net.ClientReseau;

/**
 * Cette classe sert à fournir une interface entre le serveur et l'algorithme de l'IA.
 *
 * Elle doit :
 *  - Implémenter le protocole du serveur
 *  - Reproduire l'environnement indiqué serveur avec des objets qui resterons les mêmes pendant toute la partie, avec les modifications indiquées par le serveur.
 *  - Définir des méthodes abstraites qui représente les actions que l'IA doit réaliser (en fonction de ce que demande le serveur par exemple).
 *
 * @author Florentin Magniez
 * @version 1.0
 */
public abstract class BaseIA extends ClientReseau {

	public final boolean DEBUG;
	
	public BaseIA(String hote, int port, boolean debug) throws Exception {
		super(hote, port);
		this.DEBUG = debug;
	}

	/**
	 * Fourni un moyen d'afficher des messages dans la console uniquement lorsque le programme est en mode DÉBUG.
	 * Cela permet un légér gain de performance (si la partie se joue vite par exemple).
	 * NOTE : Un retour à la ligne est automatiquement ajouté.
	 * @param message Texte à écrire dans la console.
	 */
	protected void debug(String message) {
		if (this.DEBUG)
			System.err.println(message);
	}

	@Override
	protected void travailler()
	{
		// IMPLÉMENTER LE PROTOCOLE RÉSEAU ICI
		//
		// Pour communiquer avec les serveur, 2 attributs de ClientReseau sont fournis :
		//  - BufferedReader entree : lit les données en provenance du serveur.
		//  - PrintWriter sortie : écrire les données à destination du serveur (mêmes méthodes que pour « System.out »).

		String message;

		// Après l'ouverture de la connexion

		try
		{
			sortie.println(Main.NOM_EQUIPE);

			// entree.readLine();

			int numeroClient = new Integer(entree.readLine());

			String[] messages = new String[3];
			Action[] reponse;
			EtatJeu etat;

			while ((message = entree.readLine()) != null)
			{

				// A chaque message du serveur (où « message » contient le message du serveur)

				if (message.equals("FIN"))
					break;

				debug("MESSAGE : " + message);

				etat = new EtatJeu(message);

				reponse = this.jouer(etat);

				for (int i = 0; i < 3; i++)
					messages[i] = reponse[i].message;

				sortie.println(String.join("-", messages));
			}
		}
		catch (java.net.SocketException e) {
			System.err.println("ERREUR RÉSEAU : " + e);
		}
		catch (java.io.IOException e) {
			System.err.println("ERREUR RÉSEAU : " + e);
		}
		catch (Exception e) {
			System.err.println("ERREUR INATTENDUE : " + e);
			if (this.DEBUG)
				e.printStackTrace();
		}

		// Après la déconnexion
	}

	public abstract Action[] jouer(EtatJeu etat);
}