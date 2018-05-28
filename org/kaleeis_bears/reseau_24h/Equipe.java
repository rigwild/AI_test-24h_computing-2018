package org.kaleeis_bears.reseau_24h;

public class Equipe {
	public final String nomEquipe;
	public final int score;
	public final int[] fruits;
	public final int[][] casesDepart;
	public final Joueur[] joueurs;

	public Equipe(int longueur, String donnees) {
		String[] n0 = donnees.split(","), n1;
		this.nomEquipe = n0[0];
		this.joueurs = new Joueur[3];
		int i, x, y, inventaire;
		for (i = 2; i <= 4; i++)
		{
			n1 = n0[i].split(":");
			x = new Integer(n1[1]);
			y = new Integer(n1[2]);
			inventaire = n1[3].equals("x") ? -1 : new Integer(n1[3]);
			switch (n1[0])
			{
				case "P0":
					joueurs[i - 2] = new Quetcherback(y * longueur + x, inventaire);
					break;
				case "P1":
					joueurs[i - 2] = new Lanceur(y * longueur + x, inventaire);
					break;
				case "P2":
					joueurs[i - 2] = new Lanceur(y * longueur + x, inventaire);
					break;
			}
		}
		this.casesDepart = new int[3][2];
		for (i = 6; i <= 8; i++)
		{
			n1 = n0[i].split(":");
			casesDepart[i - 6][0] = new Integer(n1[1]);
			casesDepart[i - 6][1] = new Integer(n1[2]);
		}
		this.score = new Integer(n0[10]);
		this.fruits = new int[4];
		for (i = 12; i <= 15; i++)
		{
			n1 = n0[i].split(":");
			fruits[i - 12] = new Integer(n1[1]);
		}
	}
}