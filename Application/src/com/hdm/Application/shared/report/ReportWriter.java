package com.hdm.Application.shared.report;

import com.hdm.Application.shared.report.AllNotesFromUser;
import com.hdm.Application.shared.report.AllFilteredNotes;
import com.hdm.Application.shared.report.*;


/**
 * <p>
 * Diese Klasse wird ben�tigt, um auf dem Client die ihm vom Server zur
 * Verf�gung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu �berf�hren.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat �berf�hrten Information wird den Subklassen �berlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die f�r die
 * Prozessierung der Quellinformation zust�ndig sind.
 * </p>
 * 
 * @author Thies
 * @author Weirich
 */
public abstract class ReportWriter {

  /**
   * Übersetzen eines <code>AllNotesFromUser</code> in das
   * Zielformat.
   * 
   * @param r der zu übersetzende Report
   */
  public abstract void process(AllNotesFromUser r);

  /**
   * Übersetzen eines <code>AllFilteredNotes</code> in das
   * Zielformat.
   * 
   * @param r der zu übersetzende Report
   */
  public abstract void process(AllFilteredNotes r);
  
  /**
   * Übersetzen eines <code>AllNotes</code> in das
   * Zielformat.
   * 
   * @param r der zu übersetzende Report
   */
  public abstract void process(AllNotes r);
  


}