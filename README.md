# grabhouse
Part 1: Interpolate missing values in spacedata.

Motivation: 
The value at a point might be influenced by its local context. Since this is an image data, the value at a point might be determined by considering the collective influence of its immediate neighbors.

Solution:
I posed this as a classification problem. Each point in space is treated as a data point. Values of its negihboring points are treated as features. Following features are used - 
(1) west: value of the point to the left
(2) east: value of the point to the right
(3) north: value of the point above
(4) south: value of the point below
(5) nw: value of the point at top-left
(6) ne: value of the point at top-right
(7) sw: value of the point at below-left
(8) se: value of the point at below-right

Each feature takes a nominal value in the range -1 to 99. A feature takes the value of '-1' if it does not apply for a data point. For instance, points in the first row do not have any points above and therefore for them, north=-1. The label of the data points takes values in the range 0-99, '0' when the value is unobserved. This then becomes a multiclass classification problem and can be solved using decision tree, multinomial LR etc.

Dataset generation:
Iterate over each point in spacedata.txt and convert it into a feature vector (using features described above) and label (Refer class DataGen.java).
Split the dataset into two - training set of those data points that have an observed label and test set of those data points for which the label is unobserved.

Classifier (Refer to ElementClassifier.java):
I used a J48 decision tree model and trained it using the training data (as created above). The trained model was then applied to the test data to get the label predictions.

Space data output (Refer to DataOut.java):
The label predictions are then added back to the missing elements in spacedata.txt and the output written to spaceout.txt.

The 'files' folder contains the training/test datasets and the output spaceout.txt.

----------------------------------------------------------------------------------
Part 2: Inferring state of the element at each point
We now have element value at each point and the problem is to infer its state 1-4. The state of an element might be influenced by the state of other elements in its neighborhood. The problem presents itself as a case for applying HMM (Hidden Markov Model), where, the point values are the observed random variables and their states are the unobserved random variables. The HMM model has three parameters -
(1) Initial probability: We can use the data in variousStatesDataset.txt to estimate the initial probabilities for each state as P(state=1) = P(state=2) = P(state=3) = P(state=4) = 0.25
(2) Emission probability: This is the probability of a state emitting a certain point value. Again, we can use the data in variousStatesDataset.txt to estimate the emission probabilities. For instance, P(element=35|state=1) = 0.002. The complete emission probabilties are computed in emission.csv (refer files folder).
(3) Transition probability: This is the probability of transitioning from one state to other. For instance, P(state_i = 2|state_i-1 = 1) = ? Unfortunately, there is no training data available to estimate these probabilities. The absense of any evidence on how the states of elements influence each other, makes it impossible to apply HMM here.

Alternate solution:
Next, I looked at the number of times an element is observed in a particular state. This is available in freq.csv (in the files folder). Interestingly, an element is observed to be in a particular state most number of times as compared to in any of the other states. For instance, element 35 is observed in state=2 67 times, 2 times in state=1 or 3 and 1 time in state=4. Thus, a simple model could be to tag an element value with the state that it is most commonly observed in. In the example above, element=35 is tagged to state=2.

