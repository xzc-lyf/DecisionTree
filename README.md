
# Decision Tree 2024
The group project of CMSC5724 2024fall
>    Li Yifei 1155215544

>    Wang Yu 1155215635

>    Tang Jiayi 1155215625

>    Fu Tianxing 1155215550

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Dataset](#dataset)
- [Usage](#usage)
- [Program Structure](#program-structure)

## Overview
This project is a Java implementation of a decision tree classifier. The decision tree algorithm is widely used for both classification and regression tasks. This implementation can be applied to datasets to predict outcomes based on given input features.

## Features
**Decision Tree Creation**: Generates a decision tree based on the input dataset.<br>
**Classification**: Classifies new data points based on the trained decision tree.<br>
**Accuracy calculation**: Calculate the accuracy of the classification.

## Dataset Pre-processing

1. remove all the records containing '?'
2. remove the attribute "native-country"
3. Convert '<=50K' to 1, and '>50K' to -1

### Input
`adult.data`:
Trainning Set. We place this file in input folder.

`adult.test`:
Evaluation Set. We place this file in input folder.

### Output
`adult_preprocessed.csv`:
The processed trainning set(Note that `1` represents income `<=50K`, while `-1` represents income `>50K`,  remove all the records containing '?', remove the attribute "native-country"). We place this file in output folder.

`adult_test_preprocessed.csv`:
The processed evaluation set(Same as above). We place this file in output folder.

`decision_tree.txt`:
The decision tree.

`test_acc_results`:
The file contains the prediction results of each row of data.


## Usage

### Requirement
```
jdk >= 1.6
Operation System: Mac, Window, Linux
```

### Run the executable program
File Structure:<br>

```
├── DecisionTree.jar
└── src
    ├── input
    │   ├── adult.data
    │   └── adult.test
    └── output
```


1. Please ensure that these files or directory exist: <br>
 `src/input/adult.data` <br>
 `src/input/adult.test` <br>
 `src/output` 
2. Do not modify the directory structure or file names, otherwise an exception will occur during execution. 
3. Open the terminal in the directory where `DecisionTree.jar` is located, and run the command `java -jar DecisionTree.jar`. The program will start running and output the results to `src/output`.

### Run the code

1. Ensure you have [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) installed on your machine.
   ```bash
   java -version
2. Clone the repository or download the source code:
   ```bash
   git clone https://github.com/xzc-lyf/DecisionTree.git
3. Navigate to the project directory:
	```bash
	cd decisiontree
4. Compile the Java files:
	```bash
	javac *.java
5. Run the decision tree program:
	```bash
	java Main <input_dataset> <output_predictions>
 
### Outcome
The program will return the following logs in the console:

```
Building decision tree will take several minutes...
Decision tree built, please check the output directory.
Classifying test data...
CorrectClassifiedNum: 12575, Total: 15060
Precision: 83.49934%
Total Time: 36s
```

## Program structure
```
- Main.java(Main program)
- Classifier.java(Recursively traverse the decision tree according to the input data rows to find the final classification result. In addition, the code will also calculate the accuracy of classification.)
- DecisionTreeSaver.java(Saving the decision tree to a file so that the structure of the decision tree can be viewed later)
- FilePreprocessor.java(Preprocessing the input file)
- TreeBuilder.java(Used to build decision trees. It implements the core decision tree generation algorithm, using Gini impurity as the splitting criterion)
- TreeNode.java(Defining the nodes of the desicion tree)
- LeafNode.java(This class is used to represent the final result node of decision tree classification and contains the classification statistics of all data reaching this leaf node)
```
