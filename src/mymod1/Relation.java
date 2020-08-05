/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;

/**
 *
 * @author Hritik
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* This class is for a relation with an attribute set and FDs and Candidate key*/
public class Relation {
    //3 components
        public  Set<Attributes> At; 
        public  Set<FDep> fun;
        public  Set<Set<Attributes>> cKeys;
        
       //Constructer to intialize them you may ignore this method will explain on meet
        //You need to initialize a set if you declare them to HashSet, TreeSet etc but we will use only HashSet<>() //Interface wale chapter ka hai
        
        public Relation(){
        this.At= new HashSet<>();
        this.fun =new HashSet<>();
        this.cKeys = new HashSet<>();
            
        }
        
        //Just the String Representation
         @Override
         public String toString(){
             String display;
             StringBuilder sb= new StringBuilder();
             sb.append("Attributes-> ");
             sb.append(this.At);
             sb.append("\n");
             sb.append("Functional Dependencies:\n");
             for(FDep f : this.fun){
                 sb.append(f);
                 sb.append("\n");
             }
             sb.append("Candidate Keys\n");
             
             for(Set<Attributes> a : this.cKeys){
                 sb.append(a);
                 sb.append(",");
           
             }
             sb.deleteCharAt(sb.length()-1);
            
             sb.append("\n");
             sb.append("----------------------");
             display =sb.toString();
             
             return display;
         
         }
     
        
    
    
}
