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
