# grabhouse
Part 1: Interpolate missing values in spacedata.

Motivation: 
The value at a point might be influenced by its local context. Since this is an image data, the value at a point might be determined by considering the collective influence of its immediate neighbors.

Solution:
I posed this as a classification problem. Each point in space is treated as a data point. 
