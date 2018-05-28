package org.kaleeis_bears.reseau_24h.net;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gêre la connexion avec le serveur et la reception asynchrone des messages.
 */
public abstract class ClientReseau {

	private Socket socket;
	protected BufferedReader entree;
	protected PrintWriter sortie;

	private Thread thread;
	private boolean lance;

	public ClientReseau(String hote, int port) throws Exception {
		this.socket = new Socket(hote, port);
		this.sortie = new PrintWriter(this.socket.getOutputStream(), true);
		this.entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		this.thread = null;
		this.lance = false;
	}

	public boolean estLance() { return this.lance; }

	public void lancer(boolean synchrone) {
		if (!this.lance)
		{
			if (this.thread != null)
				throw new IllegalStateException("Cet objet a déjà été lancé. Veuillez en recréer pluôt que réutiliser celui-ci.");
			this.lance = true;
			if (!synchrone)
			{
				this.thread = new Thread(new Runnable() {
					@Override
					public void run() {
						receptionMessages();
					}
				});
				this.thread.setDaemon(true); // Evite que le thread bloque le programme quand on quitte le programme par exemple
				this.thread.start();
			}
			else
				receptionMessages();
		}
	}
	public void fermer() {
		if (this.lance)
		{
			try
			{
				this.sortie.close();
				this.entree.close();
				this.socket.close();
			}
			catch (Exception e) {
				System.out.println("Erreur fermeture client : " + e);
			}
			this.lance = false;
		}
	}

	private void receptionMessages() {
		this.travailler();
	}

	protected abstract void travailler();
}