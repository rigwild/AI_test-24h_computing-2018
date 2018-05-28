package org.kaleeis_bears.reseau_24h;

import org.kaleeis_bears.reseau_24h.versions_ia.*;

public class Main {

	static public final String NOM_EQUIPE = "Kaleeis Bears";

	static public final String IA_PAR_DEFAUT = "IA_Noob";
	static public final boolean IA_FONCTIONNEMENT_SYNCHRONE = true;

	static boolean debug, synchrone;
	static String hote, nomIA = IA_PAR_DEFAUT;
	static int port;

	public static void main(String[] args) {
		debug = false;
		if (!lireArguments(args))
		{
			aide();
			System.exit(2);
		}

		BaseIA ia = null;

		try
		{
			ia = creer(nomIA, hote, port, debug);
		}
		catch (java.lang.reflect.InvocationTargetException itex)
		{
			Throwable ex = itex.getCause();
			if (ex instanceof java.net.SocketException)
				System.out.println("ERREUR RÉSEAU : La connexion avec le serveur a échouée : " + ex);
			else
			{
				System.err.print("EXCEPTION NON GÉRÉE : Une exception est survenue lors de la création de l'IA : ");
				ex.printStackTrace();
			}
		}
		catch (ClassNotFoundException cnfex) {
			System.err.println("ERREUR ARGUMENT : L'IA « " + nomIA + " » n'existe pas (classe introuvable).");
		}
		catch (NoSuchMethodException nsmex) {
			System.err.println("ERREUR CODE : La classe IA « " + nomIA + " » n'a pas de constructeur publique dont les paramètres sont (String, int, boolean).");
		}
		catch (IllegalAccessException iaex) {
			System.err.println("ERREUR CODE : La classe IA « " + nomIA + " » doit être publique.");
		}
		catch (Exception ex) {
			System.err.println("ERREUR INATTENDUE : Impossible de créer l'IA : " + ex);
		}

		if (ia != null)
		{
			System.out.println("CONNECTÉ AU SERVEUR. DEBUT DE LA PARTIE.");
			if (!IA_FONCTIONNEMENT_SYNCHRONE)
				Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						System.err.print("EXCEPTION NON GÉRÉE : Une exception est survenue lors de l'éxécution de l'IA : ");
						e.printStackTrace();
					}
				});
			try
			{
				ia.lancer(IA_FONCTIONNEMENT_SYNCHRONE);
				if (!IA_FONCTIONNEMENT_SYNCHRONE)
					while (System.in.read() != -1);
				else
					System.out.println("PARTIE TERMINÉE.");
				return;
			}
			catch (Exception ex) {
				if (IA_FONCTIONNEMENT_SYNCHRONE)
				{
					System.err.print("EXCEPTION NON GÉRÉE : Une exception est survenue lors de l'éxécution de l'IA : ");
					ex.printStackTrace();
				}
				else
					System.err.println("ERREUR INATTENDUE : Impossible de lancer l'IA : " + ex);
			}
		}
		System.exit(1);
	}

	/**********/

	// CHARGEMENT DYNAMIQUE DE LA CLASSE DE L'IA À UTILISER

	static BaseIA creer(String nom, String hote, int port, boolean debug) throws IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, java.lang.reflect.InvocationTargetException {
		return (BaseIA)Class
			.forName("org.kaleeis_bears.reseau_24h.versions_ia." + nom)
			.getConstructor(String.class, int.class, boolean.class)
			.newInstance(hote, port, debug);
	}

	/**********/

	// LECTURE DES PARAMETRES + MESSAGE D'AIDE

	static void aide() {
		System.out.println("Epreuve reseau - 24H DUT INFO 25/05/18 - Kaleeis Bears");
		System.out.println();
		System.out.println("Syntaxe : ./epreuve_reseau <options> [hote] [port] <version_ia>");
		System.out.println();
		System.out.println("Où [hote] est le nom d'hote ou l'IP du serveur et [port] le port où est hébergé le serveur.");
		System.out.println();
		System.out.println("Options possibles :");
		System.out.println(" -d, --debug : Active le mode DÉBOGAGE (plus de messages dans la console).");
		System.out.println();
		System.out.println("Si <version_ia> est omit, la classe « " + IA_PAR_DEFAUT + " » est utilisée par défaut.");
	}

	static boolean lireArguments(String[] args) {
		int noValeur = 0, nbValeursRequises = 2;
		String arg;
		for (int i = 0; i < args.length; i++) {
			arg = args[i];
			if (arg.startsWith("-"))
				if (arg.startsWith("--"))
					switch (arg.substring(2))
					{
						case "debug":
							debug = true;
							break;

						default:
							System.err.println("ERREUR ARGUMENT : Option « " + arg + " » non reconnue.");
							System.err.println();
							return false;
					}
				else
					for (int j = 1; j < arg.length(); j++)
						switch (arg.charAt(j))
						{
							case 'd':
								debug = true;
								break;

							default:
								System.err.println("ERREUR ARGUMENT : Option « -" + arg.charAt(j) + " » non reconnue.");
								System.err.println();
								return false;
						}
			else
			{
				switch (noValeur)
				{
					case 0:
						hote = arg;
						break;
					case 1:
						try
						{
							port = Integer.parseInt(arg);
							if (port > 0 && port <= 65535)
								break;
							else
							{
								System.err.println("ERREUR SYNTAXE : le numéro du port doit être compris entre 1 et 65535 (inclus).");
								System.err.println();
							}
						}
						catch (Exception ex) {
							System.err.println("ERREUR SYNTAXE : le numéro du port doit être un nombre.");
							System.err.println();
						}
						return false;

					case 2:
						nomIA = arg;
						break;

					default:
						System.err.println("ERREUR ARGUMENT : L'argument « " + arg + " » est en trop.");
						System.err.println();
						return false;
				}
				noValeur++;
			}
		}
		if (noValeur < nbValeursRequises)
		{
			System.err.println("ERREUR : " + (nbValeursRequises - noValeur) + " argument manquant.");
			System.err.println();
		}
		return (noValeur >= nbValeursRequises);
	}
	
}