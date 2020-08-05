/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Hritik
 */

//This Class Contains the Different functions to find things asked
public class FinalMethods {
    //To get Closure
       public static Set<Attributes> getClosure(Set<Attributes> attrs, Set<FDep> fds){
        Set<Attributes> result = new HashSet<>(attrs);
        boolean found = true;
        while(found){
            found = false;
            for(FDep fd : fds){
                if(result.containsAll(fd.left) && !result.containsAll(fd.right)){
                    result.addAll(fd.right);
                    found = true;
                }
            }
        }

        return result;
    }
       //Same Function Overloaded by taking another parameter FD notF to ignore the notF FD while taking closure
       //Used to find Minimal Set
        public static Set<Attributes> getClosure(Set<Attributes> attrs, Set<FDep> fds,FDep notF){
        Set<Attributes> result = new HashSet<>(attrs);
        boolean found = true;
        while(found){
            found = false;
            for(FDep fd : fds){
                if(!fd.equals(notF)){
                if(result.containsAll(fd.left) && !result.containsAll(fd.right)){
                    result.addAll(fd.right);
                    found = true;
                }
            }
            }
        }

        return result;
    }
     
     public static Set<Attributes> getClosure(Set<Attributes> attrs, Set<FDep> fds,Set<FDep> notF){
        Set<Attributes> result = new HashSet<>(attrs);
        boolean found = true;
        while(found){
            found = false;
            for(FDep fd : fds){
                if(!notF.contains(fd)){
                if(result.containsAll(fd.left) && !result.containsAll(fd.right)){
                    result.addAll(fd.right);
                    found = true;
                }
            }
            }
        }

        return result;
    }    
        
        //This functions creates a powerSet of a given Set
            public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
       
      
        return sets;
    }
            
     //removing null value in powerSet
             public static <T> Set<Set<T>> reducedPowerSet(Set<T> originalSet){
        Set<Set<T>> result = powerSet(originalSet);
        result.remove(new HashSet<T>());
        return result;
    }
     
             //Function to find SuperKeys
        public static Set<Set<Attributes>> superKeys(Set<Attributes> attrs, Set<FDep> fds){
        Set<Set<Attributes>> keys = new HashSet<>();
        if(attrs.isEmpty()){ //if attribute empty then  the fds are taken as attributes
            for(FDep fd : fds){
                attrs.addAll(fd.left);
                attrs.addAll(fd.right);
            }
        }
        
        Set<Set<Attributes>> powerset = reducedPowerSet(attrs); 
        for(Set<Attributes> sa : powerset){ //brute force
           
            if(getClosure(sa, fds).equals(attrs)){
                keys.add(sa);
            }
        }
        return keys;
    }
        //Candidate Keys
        public static Set<Set<Attributes>> candidatekeys(Set<Attributes> attrs, Set<FDep> fds){
        Set<Set<Attributes>> superkeys = superKeys(attrs, fds);
        Set<Set<Attributes>> toRemove = new HashSet<>(); // Remove those which have a subset in the SuperKey set
        for(Set<Attributes> key : superkeys){ //Take all super Keys and remove those which have a proper subset in Superkey relation
            for(Attributes a : key){
                Set<Attributes> remaining = new HashSet<>(key);
                remaining.remove(a); //creating subset by deleting one attribute from the key
                if(superkeys.contains(remaining)){ //if includes
                    toRemove.add(key);//add key to keys to be removed
                    break;
                }
            }
        }
        superkeys.removeAll(toRemove);
        return superkeys;
    }
       //gives the prime attributes
        public static Set<Attributes> getPrime(Set<Attributes> attrs, Set<FDep> fds){
            Set<Set<Attributes>> candy= candidatekeys(attrs,fds);
            Set<Attributes> prime= new HashSet<>();
            
            for(Set<Attributes> set : candy){
                   for(Attributes a : attrs){
                     if(set.contains(a)) prime.add(a);
                   
                   
                   }
            
            
            }
            
        return prime;
        
        }
         //gives the non-prime attributes
      public static Set<Attributes> getnPrime(Set<Attributes> attrs, Set<FDep> fds){
            Set<Set<Attributes>> candy= candidatekeys(attrs,fds);
            Set<Attributes> prime = getPrime(attrs,fds);
            Set<Attributes> nprime= new HashSet<>();
            
            
            for(Attributes r : attrs){
                if(!prime.contains(r)) nprime.add(r);
            
            
            }
            
          
            
        return nprime;
        
        }
      
      //Gives the mininal Set to remove redundant Functional Dependencies
        public static Set<FDep> canonicalCover(Set<Attributes> attrs, Set<FDep> fds){
           Set<FDep> fdummy= new HashSet<>(); 
           //Seperate right side
           for(FDep f : fds){
               for(Attributes a : f.right){
                   Set<Attributes> rt  =new HashSet<>();
                   rt.add(a);
                   FDep df  = new FDep(f.left,rt);
                   fdummy.add(df);
                
               }
           }
           //Remove redundant FD's
         
           Set<FDep> currentFD = new HashSet<>(fdummy);
           for(FDep fr : fdummy){
               
               Set<Attributes> C1 = getClosure(fr.left,currentFD);
               Set<Attributes> CW = getClosure(fr.left,currentFD,fr);
               
               if(C1.equals(CW)){
                   currentFD.remove(fr);
               }
           
           }
             Set <FDep> notReq = new HashSet<>(currentFD);
           
           //Remove leftside redudancy
           for(FDep f : currentFD){
               if(f.left.size()>1){
                   Set<Attributes> C1 = getClosure(f.left,currentFD);
                   Set<Set<Attributes>> pset = FinalMethods.reducedPowerSet(f.left);
                   
                   for( Set<Attributes> e : pset){
                     //  Set<Attributes> dum = new HashSet<>();
                      // dum.add(e);
                       Set<Attributes> eachCl = getClosure(e,currentFD);
                       if(eachCl.equals(C1)){
                           f.left.clear();
                           f.left.addAll(e);
                           break;
                       }
                   }            
               }                    
           }
             // Join Them;
             Set<FDep> finalFD = new HashSet<>(currentFD);
             Set<FDep> cc = new HashSet<>();
             Set<Set<Attributes>> checked = new HashSet<>();
             System.out.println("CurrentFD");
             
            for(FDep f : finalFD){
                if(!checked.contains(f.left)){
                
                for(FDep d : currentFD){
                    if((d.left.equals(f.left))){
                    f.right.addAll(d.right);
                    
                    }               
                }                      
            cc.add(f);
            checked.add(f.left);
                }
            }
           
           return cc;
       
    
        }
      }
