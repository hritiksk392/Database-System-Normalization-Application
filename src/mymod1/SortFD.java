/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymod1;
import java.util.*;

/**
 *
 * @author Hritik
 */
public class SortFD implements Comparator<FDep>{
    @Override
    public int compare(FDep f1, FDep f2) {
        return f1.left.size() -(f1.right.size());
    }

    

    
}
