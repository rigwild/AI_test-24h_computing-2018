package org.kaleeis_bears.reseau_24h.util;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.IntPredicate;

public final class Tableau {

	@FunctionalInterface
	public interface Mappeur {
		int map(int nombre);
	}
	@FunctionalInterface
	public interface MappeurAvecIndex {
		int map(int nombre, int index);
	}

	public static <T extends Number> int[] listeVersTableauNombre(List<T> liste) {
		int[] tableau = new int[liste.size()];
		int i = 0;
		for (T el : liste)
			tableau[i++] = el.intValue();
		return tableau;
	}
	public static List<Integer> tableauNombreVersListe(int[] tab) {
		Integer[] resultat = new Integer[tab.length];
		for (int i = 0; i < tab.length; i++)
			resultat[i] = tab[i];
		return Arrays.asList(resultat);
	}

	public static int position(int[] tab, int valeur) {
		int i = 0;
		for (; i < tab.length && tab[i] != valeur; i++);
		return (i == tab.length ? -1 : i);
	}
	public static int position(int[] tab, int depart, int valeur) {
		int i = depart;
		for (; i < tab.length && tab[i] != valeur; i++);
		return (i == tab.length ? -1 : i);
	}
	public static int[] positionTous(int[] tab, int valeur) {
		LinkedList<Integer> resultat = new LinkedList<>();
		for (int i = 0; i < tab.length; i++)
			if (tab[i] == valeur)
				resultat.add(i);
		return listeVersTableauNombre(resultat);
	}

	public static void remplir(int[] tab, int valeur) {
		Arrays.fill(tab, valeur);
	}
	public static void remplir(int[][] tab, int valeur) {
		for (int x = 0; x < tab.length; x++)
			for (int y = 0; y < tab.length; y++)
				tab[x][y] = valeur;
	}

	public static int chercher(int[] tab, IntPredicate critere) {
		int i = 0;
		for (int el : tab)
			if (critere.test(el))
				return i;
			else
				i++;
		return -1;
	}
	public static <T> int chercher(Iterable<T> tab, Predicate<T> critere) {
		int i = 0;
		for (T el : tab)
			if (critere.test(el))
				return i;
			else
				i++;
		return -1;
	}

	public static int[] chercherTous(int[] tab, IntPredicate critere) {
		LinkedList<Integer> resultat = new LinkedList<>();
		int i = 0;
		for (int el : tab)
		{
			if (critere.test(el))
				resultat.add(i);
			i++;
		}
		return listeVersTableauNombre(resultat);
	}
	public static <T> int[] chercherTous(Iterable<T> tab, Predicate<T> critere) {
		LinkedList<Integer> resultat = new LinkedList<>();
		int i = 0;
		for (T el : tab)
		{
			if (critere.test(el))
				resultat.add(i);
			i++;
		}
		return listeVersTableauNombre(resultat);
	}

	public final int[] tableau;

	public Tableau(int[] tableau) {
		this.tableau = tableau;
	}
	public Tableau(List<? extends Number> tableau) {
		this(Tableau.listeVersTableauNombre(tableau));
	}

	public Tableau filtrer(IntPredicate condition) {
		return new Tableau(Tableau.chercherTous(this.tableau, condition));
	}

	public Tableau map(Tableau.Mappeur mappeur) {
		return this.map((n, i) -> mappeur.map(n));
	}
	public Tableau map(Tableau.MappeurAvecIndex mappeur) {
		int[] nouveau = new int[this.tableau.length];
		for (int i = 0; i < this.tableau.length; i++)
			nouveau[i] = mappeur.map(this.tableau[i], i);
		return new Tableau(nouveau);
	}
}