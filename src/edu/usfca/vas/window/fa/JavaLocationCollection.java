package edu.usfca.vas.window.fa;
public class JavaLocationCollection {
  private static JavaView[] defaultLocations =
    { new JavaView("trip1.png"),
	  new JavaView("trip2.png"),
	  new JavaView("trip3.png"),
	  new JavaView("trip4.png"),
	  new JavaView("trip5.png"),
	  new JavaView("trip6.png"),
	  new JavaView("trip7.png"),
	  new JavaView("s1.png"),
	  new JavaView("s2.png"),
	  new JavaView("s3.png"),
	  new JavaView("s4.png"),
	  new JavaView("s5.png"),
	  new JavaView("s11.png"),
	  new JavaView("s12.png"),
	  new JavaView("s21.png"),
	  new JavaView("s22.png"),
	  new JavaView("s23.png"),
	  new JavaView("s24.png"),
	  new JavaView("s31a.png"),
	  new JavaView("s31b.png"),
	  new JavaView("s31c.png"),
	  new JavaView("s31d.png"),
	  new JavaView("s211a.png"),
	  new JavaView("s211b.png"),
	  new JavaView("s221.png"),
	  new JavaView("s222.png"),
	  new JavaView("s223.png"),
	  new JavaView("s224.png"),
	  new JavaView("s225.png"),
	  new JavaView("s226.png"),
	  new JavaView("s227.png"),
	  new JavaView("s231.png"),
	  new JavaView("s232.png"),
	  new JavaView("w1.png"),
	  new JavaView("w2.png"),
    };

  private JavaView[] locations;
  //private int numCountries;

  public JavaLocationCollection(JavaView[] locations) {
    this.locations = locations;
  //  this.numCountries = countCountries(locations);
  }
  
  public JavaLocationCollection() {
    this(defaultLocations);
  }

  public JavaView[] getLocations() {
    return(locations);
  }

//  public int getNumCountries() {
//    return(numCountries);
//  }

  // Assumes the list is sorted by country name
  
//  private int countCountries(JavaView[] locations) {
//    int n = 0;
//    String currentCountry, previousCountry = "None";
//    for(int i=0;i<locations.length;i++) {
//      currentCountry = locations[i].getCountry();
//      if(!previousCountry.equals(currentCountry))
//        n = n + 1;
//      currentCountry = previousCountry;
//    }
//    return(n);
//  }
}