# Database-System-Normalization-Application

## Overview
A Java based GUI application that for a given set of Attributes and Functional Dependencies can identify candidate keys and check the highest normal form it satisfies.
If the highest normal form is X it can decompose the given relation to satisfy X+1 Normal Form upto **BCNF Normal Form**.

The application has been made using NetBeans IDE 8.2 and written entirely in Java (v. 1.8.0_231).The source code for the project can be found in the src/mymod1 directory. The source code has been appropriately commented wherever found necessary. 

## Team Members
* Hritik Singh Kushwah
* Bhavish Pahwa (https://github.com/bp-high)
* Shubham Asopa (https://github.com/sammyasopa)
* Smiket Barodia ()

## Preview:
Below is the preview of different functionalities of the application.
### Instructions for Inputs
* All the input Attributes should be seperated by a comma ','.
* For each functional dependency the left attributes and the right attributes should be seperated using a single arrow '->'.
* New functional dependencies should be added from a new line ("Press Enter").

![Home](/Preview/prev.png)

### Candidate Keys
The candidate keys can be obtained using the **KEYS** button.
![Keys](/Preview/cKeys.png)

### Highest Normal Form
To obtain the Highest Normal Form use the **HNF** button.
![Keys](/Preview/hnf.png)

### Decomposition to X+1 Form
For decomposing the given relation to X+1 form use **Decompose** button. Only applicable upto to BCNF form.
![Keys](/Preview/decompose.png)

### Closures
For closure of all possible combinations of the given attributes can be obtained from **Closure** button.
![Keys](/Preview/closure.png)



