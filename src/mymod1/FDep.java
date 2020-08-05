/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Hritik
 */
//Class for functional Dependencies
//REPRESENTATION OF FDEP-->>> left-->right
public class FDep {
    //Two components of 
    
        public  Set<Attributes> left; 

        public  Set<Attributes> right;
     
// this Builder class creates a Functional Dependencies given the expression "X->Y" string
      public static class Builder{
        private Set<Attributes> left;
        private Set<Attributes> right;

        public Builder(){
            this.left = new HashSet<>();
            this.right = new HashSet<>();
        }

       
        public FDep build(){
            return new FDep(this.left, this.right);
        }

       
        public Builder left(Attributes... as){
            this.left.addAll(Arrays.asList(as));
            return this;
        }

      
        public Builder left(Set<Attributes> as){
            this.left.addAll(as);
            return this;
        }

        
        public Builder right(Attributes... as){
            this.right.addAll(Arrays.asList(as));
            return this;
        }

       
        public Builder right(Set<Attributes> as){
            this.right.addAll(as);
            return this;
        }
       

    }
        public FDep(Set<Attributes> left, Set<Attributes> right){
        this.left = new HashSet<>(left);
        this.right = new HashSet<>(right);
    }
        
        //Same implementation as the Attribute class
          public static Set<FDep> getSet(String exprs){
        if(exprs.equals("")){
            return new HashSet<>();
        }
        //exprs = exprs.replaceAll("\\s","");
        return getSet(exprs.split("\n")); 
    }
             public static Set<FDep> getSet(String[] exprs){
        Set<FDep> fds = new HashSet<>();
        for(String s : exprs){
            fds.add(FDep.of(s));
        }
        return fds;
    }
             
             // of method to convert "X->Y"
      public static FDep of(String expr){
        String[] halves = expr.split("->");
        return of(halves[0], halves[1]);
    }
      //Same Method Overloaded with 2 parameter input
 public static FDep of(String left, String right){
        left = left.replaceAll("\\s+","");
        right = right.replaceAll("\\s+","");
        String[] lefts = left.split(",");
        String[] rights = right.split(",");
        Builder bd = new Builder();
        for(String s : lefts){
            bd.left(Attributes.of(s));
        }
        for(String s : rights){
            bd.right(Attributes.of(s));
        }
        return bd.build();
    }
 public Set<Attributes> getLeft(){
        return new HashSet<>(this.left);
    }
  public Set<Attributes> getRight(){
        return new HashSet<>(this.right);
    }
  //Ignore the HashCode
   @Override
    public int hashCode(){
        int result = 17;
        for(Attributes at : this.left){
            result = 1000-(31 * result +at.hashCode());
        }
        for(Attributes at : this.right){
            result = 1000-(31 * result + at.hashCode());
        }
        return result;
    }
   
    
    //Overriding the toString() method to just print an FDep object as a string so 
      public String toString(){
        StringBuilder sb = new StringBuilder((this.left.size() + this.right.size()) * 10);
        for(Attributes at : this.left){
            sb.append(at.toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" -> ");
        for(Attributes at : this.right){
            sb.append(at.toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
  @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof FDep)){
            return false;
        }
        FDep fd = (FDep)o;
        return this.left.equals(fd.left) && this.right.equals(fd.right);
    }
}
