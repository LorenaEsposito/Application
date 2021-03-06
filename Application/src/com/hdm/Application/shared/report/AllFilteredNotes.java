package com.hdm.Application.shared.report;

import java.io.Serializable;

import com.hdm.Application.shared.report.CompositeReport;

/**
 * Report, der alle Notizen nach Datum ausgibt. Vor, Nach oder zwischen den angegebenen Daten.
 * Die Klasse traegt keine weiteren Attribute- und Methoden-Implementierungen,
 * da alles Notwendige schon in den Superklassen vorliegt. Ihre Existenz ist 
 * dennoch wichtig, um bestimmte Typen von Reports deklarieren und mit ihnen 
 * objektorientiert umgehen zu koennen.
 * 
 * @author Thies
 * @author Weirich
 */
public class AllFilteredNotes 
	extends SimpleReport 
	implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}
