/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;

import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Hritik
 */

//Class of ATTRIBUTES 
public final class Attributes {
        private final String value; // what is the name of the attribute  1. parameter name
        
         public Attributes(String value){  //So if u want to create a attribute from a single string "A" use this method
        this.value = value;
    }
        public static Set<Attributes> getSet(String names){  // this method takes a String as an input ans split that string with ';' delimiter
        if(names.equals("")){
            return new HashSet<>();  // return empty set
        }
        names = names.replaceAll("\\s+",""); //just to remove spaces
        return getSetU(names.split(","));  //now getSetU is a utility function which return a Set of attributes from a array of strings
    }                                      // names.split(",") do :    "A,B,C" is converted as  ["A","B","C"]          
        @Override
        public String toString(){
        return this.value;
    }
    // The utility function which takes input as Array of Strings and return a set of attributes
        // so "A,B,C" is passed as  ["A","B","C"] in this method
        public static Set<Attributes> getSetU(String[] text){
            Set<Attributes> attribute = new HashSet<>();
            
        for(String s : text){
            
            attribute.add(Attributes.of(s));
        }
       return attribute;
       
        }
        // to create a object of Attribute
        public static Attributes of(String name){
        return new Attributes(name);
    }
        
        //Compare 2 attribute object
          @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Attributes)){
            return false;
        }
        Attributes a = (Attributes)o;
        return a.value.equals(this.value);
    }
//This hashcode is used as we are using a HashSet<>() dataset so ,to preserve the order alphabetically
public int hashCode(){
        return this.value.hashCode(); // to retain the order of the values as Set does not guarantees it
    }
    
}
