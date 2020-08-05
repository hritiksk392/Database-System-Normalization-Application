/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;

import java.util.HashSet;
import java.util.Set;
import java.util.*; 


public class NormalForm {
      
  // This Class deals with all the Normal Form Methods
  //LEGEND FD-> functional dependencies and attr- attributes
    
    //Calculate the highest form and give out put an integer :[1,4] while 4 denoting BCNF
    
        public static int calcForm(Set<Attributes> attrs, Set<FDep> fds){
               int nform=4;
               Set<Set<Attributes>> superKeys= FinalMethods.superKeys(attrs, fds);
               Set<Attributes> prime = FinalMethods.getPrime(attrs, fds);
               Set <Attributes> nprime = FinalMethods.getnPrime(attrs, fds);

               for(FDep fd :fds){
                   if(superKeys.contains(fd.left)){
                       continue;
                   }
                   else{
                       if(prime.containsAll(fd.right)){
                       if(nform>=3)
                       nform =3;
                       
                       continue;
                       }
                       else{
                           
                           if(!(nprime.containsAll(fd.left))){  
                               nform=1;
                               break;
                              
                           
                           }
                           else{
                           nform =2;
                           }
                       
                       
                       }
                   
                   }
                
                           
                   
               }
               
               
               return nform;
        }
        //This function just Convert the form to string
        public static String getForm(int form){
                String sform;
                if(form == 4) sform = "BCNF";
                else if(form>0){
                sform = form + "NF";
                
                }
                else sform ="Error! form";
        
                return sform;
        } 
        public static Relation r = new Relation();
        
         public Set<Attributes> pre =new HashSet<>();
       //This is a utility method for Decompose 2nf to get Transitive Dependency from a partial dependency 
       public static void pseudoR(Set<Attributes> att,Set<FDep> fds,Set <Attributes> nprime){
                for(FDep f : fds){
                   
                    if(!fillDep.contains(f)){
                    if(att.containsAll(f.left)&&nprime.containsAll(f.left)){ //may have some compilcations
                        r.At.addAll(f.right);
                        r.fun.add(f);
                         fillDep.add(f);
                        System.out.println("pseudo" + " "+ f);
                        pseudoR(f.right,fds,nprime);
                       
                    }
                
                }
               }
       
       }
       public static Set<FDep> fillDep = new HashSet<>();// ignore this
       
       // for 2nf from 1nf
       //This function will return a Set of Relations and Class Relation is already defined in another file
       public static Set<Relation> decompose2NF(Set<Attributes> att,Set<FDep> fds){
           //Required Sets
           for(FDep k: fds) System.out.println("fund-"+k);
            Set<Relation> Rset = new HashSet<>(); //This will be returned
            Set<FDep> pfd = new HashSet<>(); //This will get all the partial depedencies
            
            Set<Set<Attributes>> superKeys= FinalMethods.superKeys(att, fds);
            Set<Set<Attributes>> canKeys= FinalMethods.candidatekeys(att, fds);
            Set<Attributes> ckey = canKeys.iterator().next();
            Set<Attributes> prime = FinalMethods.getPrime(att, fds);
            Set <Attributes> nprime = FinalMethods.getnPrime(att, fds);
            
            boolean cKeyChecker =false; // this flag is to check if candidate is already present in some relation so that we don't need to create another
              
              r=new Relation(); // This relation will contain those which do not violate 2NF
             
             System.out.println("pfds");
             for(FDep fd:fds){
                 if(!superKeys.contains(fd.left)&&!prime.containsAll(fd.right)&&!(nprime.containsAll(fd.left))){
             pfd.add(fd);
             System.out.println(fd);
                 }
             }
          
      
             //making the dummy variable numm again
             
             
             //This loop will take care of all Partial -Dep
               for( FDep pf : pfd){
                Relation dr = new Relation(); //dummy relation object to create a relation
                //Adding the attributes and FDs
                dr.At.addAll(pf.left);
                dr.At.addAll(pf.right);
                dr.fun.add(pf);
                fillDep.add(pf);
                dr.cKeys.add(pf.left);
                r= new Relation();
                pseudoR(pf.right,fds,nprime); //this pseudo checks for transitive dependencies in the new relation if any
                //and add them
                dr.At.addAll(r.At);
                dr.fun.addAll(r.fun);
                
                r= new Relation(); // ignore this 
                fillDep.addAll(dr.fun);
                //adding them to our final set of relation
                Rset.add(dr);
                  
             
             } 
                

    r=new Relation(); 
    for(FDep fd :fds){
    if(superKeys.contains(fd.left)||prime.containsAll(fd.right)||(nprime.containsAll(fd.left))){
        if(!fillDep.contains(fd)){
            r.At.addAll(fd.left);
            r.At.addAll(fd.right);                     
            r.fun.add(fd);
            fillDep.add(fd);
            }
 
    }
}
                 
               for(FDep fd :r.fun){
              if((fd.left).equals(ckey)){             
              cKeyChecker =true;
              } 
               }
             fillDep.addAll(r.fun);
             r.cKeys = FinalMethods.candidatekeys(r.At,r.fun);
             if(!r.At.isEmpty())
             Rset.add(r); //and Add them to our decomposition
             
             
               //Another relation only for candidate key of the Original relation
              r= new Relation();
              r.At.addAll(ckey);
              r.cKeys.add(ckey);
             if(!cKeyChecker)  Rset.add(r); //if candidate key is already not present add the new one else lite
        
             return Rset;
   
       }
       
       //Same analogy as above 2nf
       public static Set<Relation> decompose3NF(Set<Attributes> att, Set<FDep> fds){
            Set<Relation> Rset = new HashSet<>();
            Set<FDep> tfd = new HashSet<>(); //Tranitive ones
            Set<Set<Attributes>> superKeys= FinalMethods.superKeys(att, fds);
            Set<Set<Attributes>> canKeys= FinalMethods.candidatekeys(att, fds);
            Set<Attributes> ckey = canKeys.iterator().next();
            Set<Attributes> prime = FinalMethods.getPrime(att, fds);
            Set <Attributes> nprime = FinalMethods.getnPrime(att, fds);
            
            boolean cKeyChecker =false;
             r=new Relation();
             for(FDep fd :fds){
                   if(superKeys.contains(fd.left)){
                        
                        r.At.addAll(fd.left);
                       r.At.addAll(fd.right);
                       r.fun.add(fd);
                       
                       continue;
                   }
                   else{
                       if(prime.containsAll(fd.right)){
                          
                      r.At.addAll(fd.left);
                       r.At.addAll(fd.right);
                       r.fun.add(fd);
                       
                       continue;
                       }
                       else{
                           tfd.add(fd);
                                
                           }

                       }
                   
                   }
            
             
             r.cKeys = FinalMethods.candidatekeys(r.At,r.fun);
             
             if(!r.At.isEmpty())
             Rset.add(r);
             
             r=new Relation();
             
//Dealing with transitive ones just create a new Relation that's it             
            for(FDep f :tfd){
                     Relation dt = new Relation();
                    dt.At.addAll(f.left);
                    dt.At.addAll(f.right);
                    dt.fun.add(f);
                    dt.cKeys.add(f.left);
                    Rset.add(dt);
                 
                 }
            for(Relation rr: Rset){
            for(FDep f: rr.fun){
               if(f.left.equals(ckey)) cKeyChecker =true;
            
            }
            
            }
            r= new Relation();
            
              r.At.addAll(ckey);
               
             if(!cKeyChecker)  Rset.add(r);
                 
          return Rset;

       }
       
       
       static Set<Relation> Rset = new HashSet<>(); 
       
       public static Set<Relation> decomposeBNF(Set<Attributes> att, Set<FDep> fds){
           
            Set<Relation> result = new HashSet<>(); // Final returning set of Relation
            
            //Required Sets 
            Set<FDep> vfd = new HashSet<>(); // Violating functional Dependencies
            Set<Set<Attributes>> superKeys= FinalMethods.superKeys(att, fds); //superKeys
            Set<Set<Attributes>> canKeys= FinalMethods.candidatekeys(att, fds);

            Set<Attributes> prime = FinalMethods.getPrime(att, fds);
            Set <Attributes> nprime = FinalMethods.getnPrime(att, fds);

             r=new Relation();//Relation to Take those values which follows BCNF
             r.At.addAll(att);  //ADD THE ATTRIBUTES
             
             //THIS LOOP WILL CHECK THE VALIDATING FDs
             for(FDep fd :fds){
                 if(att.containsAll(fd.left)&&att.containsAll(fd.right)){ // This if Condition that att.contains(fd.left and fd.right) is check if a FD is lost while performing (R-(fd.right-fd.left)) decomposition
                   if(superKeys.contains(fd.left)){
                        
                       r.fun.add(fd);    
                       continue;
                   }
                   else{
                       // Add the violating
                      vfd.add(fd);
                   
                   }
             }
                }
             //put candidate keys
             r.cKeys = FinalMethods.candidatekeys(r.At, r.fun);
             
             //if there is no violating FDs just return the above formed r;
             if(vfd.isEmpty()){
                result.add(r);
                return result;             
             }
             
             
             //else get any violating FD and if it is X->Y THEN BREAK IT INTO (XuY) AND (R-(Y-X)) WHERE u IS UNION
             
            FDep vf1= vfd.iterator().next();//Picking up any violating FD
            
            

           Relation r1 =new Relation(); // FOR (XuY)
           Relation r2 =new Relation(); // FOR (R-(Y-X))
           
           //CREATING r1
             r1.At.addAll(vf1.left);
             r1.At.addAll(vf1.right);
             r1.fun.add(vf1);
             
            //Using Dummy Variables for Creating (R-(Y-X)) because if use the original parameters it can make change to the original ones also.
             Set <Attributes> r2Att = new HashSet<>(att); //Attributes :add R same as Original one's
             Set <FDep> r2fd = new HashSet<>(fds); //All Functional Dependency same as Original one's.
             r2fd.remove(vf1);//Remove the violating one
             
            //Just to make attribute set of R2 =R-(Y-X) 
             Set <Attributes> getRights = new HashSet(vf1.right);// get att(Y)
             getRights.removeAll(vf1.left); //get att(Y-X)               
             r2Att.removeAll(getRights); // get R-(Y-X)
             
             //Final put all those values in r2
             r2.At.addAll(r2Att);
             r2.fun.addAll(r2fd);
             
             //Now recursively add them to result and again call the function to check if there are not still in BCNF;
             //Pay attention if it's already in BCNF it will return the same Relation .. refer codeline;- 274-278
             result.addAll(decomposeBNF(r1.At,r1.fun));
             result.addAll(decomposeBNF(r2.At,r2.fun));
             
             //
             
             return result;
             
             
            
             
       
       }
      
       
   public static Set<FDep> covered = new HashSet<>();   
       public static Set<Relation> dec2NF(Set<Attributes> att, Set<FDep> fds){
           Set<Relation> R2nf = new HashSet<>();
           
             Set<FDep> pfd = new HashSet<>(); //This will get all the partial depedencies       
            Set<Set<Attributes>> superKeys= FinalMethods.superKeys(att, fds);
            Set<Set<Attributes>> canKeys= FinalMethods.candidatekeys(att, fds);
            Set<Attributes> ckey = canKeys.iterator().next();
            Set<Attributes> prime = FinalMethods.getPrime(att, fds);
            Set <Attributes> nprime = FinalMethods.getnPrime(att, fds);
             Set <FDep> FullD = new HashSet<>();
            FullD.addAll(fds);
            for(FDep fd:fds){
             if(!superKeys.contains(fd.left)&&!prime.containsAll(fd.right)&&!(nprime.containsAll(fd.left))){
             pfd.add(fd);
             System.out.println(fd);

             FullD.removeAll(pfd);
           }
         }
            
            
            System.out.println("Partial");
            for(FDep f: pfd){
            System.out.println(f);
            
            }
            System.out.println("Full");
            for(FDep f: FullD){
            System.out.println(f);
            
            }
       
       
            
            for(FDep pf : pfd){
                if(!covered.contains(pf)){
             Relation Ry = new Relation();
            
             Ry = closRel(pf.left,fds);            
            // pfd.removeAll(Ry.fun);
            covered.addAll(Ry.fun);
            Ry.cKeys = FinalMethods.candidatekeys(Ry.At, Ry.fun);
            if(!Ry.At.isEmpty())
             R2nf.add(Ry);
             //pfd.removeAll(Ry.fun);
             //if(pfd.isEmpty()) break;
                }
            }

          
             Relation Rk = new Relation();
             
            for(FDep fr: FullD){
                 if(!covered.contains(fr)){
                Rk.At.addAll(fr.left);
                Rk.At.addAll(fr.right);
                Rk.fun.add(fr);
                 }
            }
            Rk.cKeys= FinalMethods.candidatekeys(Rk.At, Rk.fun);
            if(!Rk.At.isEmpty())
            R2nf.add(Rk);
            boolean cKeyChecker = false;
            for(FDep fd :Rk.fun){
              if(canKeys.contains(fd.left)){             
              cKeyChecker =true;
              } 
               }
              r= new Relation();
              r.At.addAll(ckey);
              r.cKeys.add(ckey);
             if(!cKeyChecker) { 
                 if(!r.At.isEmpty())
                 R2nf.add(r);
             
             }//if candidate key is already not present add the new one else lite
        
             
             
       covered.clear();
       return R2nf;
       
       
       
       }
       
       public static Relation closRel(Set<Attributes> A,Set<FDep> fds){
         Relation Rg = new Relation();
        Set<Attributes> clos = FinalMethods.getClosure(A, fds,covered);
//        Rg.At.addAll(clos);
        for(FDep f: fds){
            if(!covered.contains(f)){
            if(clos.containsAll(f.left)&&!A.containsAll(f.left)||(A.equals(f.left))){
            Rg.fun.add(f);   
            Rg.At.addAll(f.left);
            Rg.At.addAll(f.right);
            
            }
            }
        }
       
       
       return Rg;
       }
//       public static Set<FDep> getPartial(Set<Attributes> A,Set<FDep> fds){
//           
//       
//       
//       }
       
           public static Set<Relation> decomposeBCNF(Set<Attributes> att, Set<FDep> fds){
           
            Set<Relation> result = new HashSet<>(); // Final returning set of Relation
            
            //Required Sets 
            Set<FDep> vfd = new HashSet<>(); // Violating functional Dependencies
            Set<Set<Attributes>> superKeys= FinalMethods.superKeys(att, fds); //superKeys
            Set<Set<Attributes>> canKeys= FinalMethods.candidatekeys(att, fds);

            Set<Attributes> prime = FinalMethods.getPrime(att, fds);
            Set <Attributes> nprime = FinalMethods.getnPrime(att, fds);

             r=new Relation();//Relation to Take those values which follows BCNF
             r.At.addAll(att);  //ADD THE ATTRIBUTES
             
             //THIS LOOP WILL CHECK THE VALIDATING FDs
             for(FDep fd :fds){
                 if(att.containsAll(fd.left)&&att.containsAll(fd.right)){ // This if Condition that att.contains(fd.left and fd.right) is check if a FD is lost while performing (R-(fd.right-fd.left)) decomposition
                   if(superKeys.contains(fd.left)){
                        
                       r.fun.add(fd);    
                       continue;
                   }
                   else{
                       // Add the violating
                      vfd.add(fd);
                   
                   }
             }
                }
             //put candidate keys
             r.cKeys = FinalMethods.candidatekeys(r.At, r.fun);
             
             //if there is no violating FDs just return the above formed r;
             if(vfd.isEmpty()){
                result.add(r);
                return result;             
             }
             
             
             //else get any violating FD and if it is X->Y THEN BREAK IT INTO (XuY) AND (R-(Y-X)) WHERE u IS UNION
             
            FDep vf1= vfd.iterator().next();//Picking up any violating FD
            
            

           Relation r1 =new Relation(); // FOR (XuY)
           Relation r2 =new Relation(); // FOR (R-(Y-X))
           
           //CREATING r1
           Set<Attributes> r1att = new HashSet<>(vf1.left);
           r1att.addAll(vf1.right);
            
             r1 = FDJoin(r1att,fds);
             r1.fun.add(vf1);
             
            //Using Dummy Variables for Creating (R-(Y-X)) because if use the original parameters it can make change to the original ones also.
             Set <Attributes> r2Att = new HashSet<>(att); //Attributes :add R same as Original one's
             Set <FDep> r2fd = new HashSet<>(fds); //All Functional Dependency same as Original one's.
             r2fd.remove(vf1);//Remove the violating one
             
            //Just to make attribute set of R2 =R-(Y-X) 
             Set <Attributes> getRights = new HashSet(vf1.right);// get att(Y)
             getRights.removeAll(vf1.left); //get att(Y-X)               
             r2Att.removeAll(getRights); // get R-(Y-X)
             r2 = FDJoin(r2Att,fds);
             //Final put all those values in r2
//             r2.At.addAll(r2Att);
//             r2.fun.addAll(r2fd);
//             
             //Now recursively add them to result and again call the function to check if there are not still in BCNF;
             //Pay attention if it's already in BCNF it will return the same Relation .. refer codeline;- 274-278
             result.addAll(decomposeBNF(r1.At,r1.fun));
             result.addAll(decomposeBNF(r2.At,r2.fun));
             
             //
             
             return result;
      
       }
           
           public static Relation FDJoin(Set<Attributes> att,Set <FDep> fds){
                Set<Attributes> OriAtt = new HashSet<>();
        for(FDep fd : fds){
             OriAtt.addAll(fd.left);
            OriAtt.addAll(fd.getRight());
        }
        if(att.containsAll(OriAtt)){
            Relation re = new Relation();
            re.At.addAll(att);
            re.fun.addAll(fds);
            re.cKeys =FinalMethods.candidatekeys(att, fds);
            return re;
        }
        Set<Attributes> notPresent = new HashSet<>(OriAtt);
        notPresent.removeAll(att);
        Set<Set<Attributes>> powerset = FinalMethods.reducedPowerSet(att);
        
         Relation re = new Relation();
         re.At.addAll(att);
        for(Set<Attributes> sa : powerset){
            Set<Attributes> closure = FinalMethods.getClosure(sa, fds);
            closure.removeAll(notPresent);
            //closure.removeAll(sa);
          FDep f = new FDep(sa,closure);
          re.fun.add(f);
          
            
           // result.add(new FuncDep.Builder().left(sa).right(closure).build());
        }
        //return result;
       Relation result = new Relation();
     result.At.addAll(att);
        result.fun=FinalMethods.canonicalCover(re.At,re.fun);
        result.cKeys=FinalMethods.candidatekeys(result.At, result.fun);
        return result;
          
           }
        
    
}
