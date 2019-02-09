package org.c4c.tiny;

import java.net.URL;

public class Utils {
	public static boolean isValidUrl(String url) 
    { 
        /* Try creating a valid URL */
        try { 
            new URL(url).toURI(); 
            return true; 
        } 
          
        // If there was an Exception 
        // while creating URL object 
        catch (Exception e) { 
            return false; 
        } 
    } 
}
