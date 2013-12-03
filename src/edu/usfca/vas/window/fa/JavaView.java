package edu.usfca.vas.window.fa;
/** Simple data structure with three properties: country,
 *  comment, and flagFile. All are strings, and they are
 *  intended to represent a country that has a city or
 *  province named "Java", a comment about a more
 *  specific location within the country, and a path
 *  specifying an image file containing the country's flag.
 *  Used in examples illustrating custom models and cell
 *  renderers for JLists.
 *
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JavaView {
  private String fileName;

  public JavaView(String fileName) {

    setFileName(fileName);
  }

  /** String representation used in printouts and in JLists */
  
  public String toString() {
    return(fileName);
  }
  
  /** Return path to image file of country flag. */
  
  public String getFileName() {
    return(fileName);
  }

  /** Specify path to image file of country flag. */
  
  public void setFileName(String flagFile) {
    this.fileName = flagFile;
  }
}