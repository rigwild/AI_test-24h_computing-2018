package org.kaleeis_bears.reseau_24h;

public enum Action {
	RIEN ("x"),

	BOUGER_NORD ("N"),
	BOUGER_SUD ("S"),
	BOUGER_EST ("E"),
	BOUGER_OUEST ("O"),

	CHANGER_OBJET ("P"),

	LANCER_NORD ("LN"),
	LANCER_SUD ("LS"),
	LANCER_EST ("LE"),
	LANCER_OUEST ("LO");

	public final String message;

	Action(String message) {
		this.message = message;
	}
}