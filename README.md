# computer_vision
Introduction
The project is aimed at estimating the germination rate of plants/crops in farmlands and to ensure the intra-role spacing between plants are within the predefined allowance. Based on the result of the analysis, the application suggests to farmers to thin, replant or transplant on the farmland. 
All these analysis is done based on pictures taken on the farm at randomly generated points.

A feeder application to this is the field mapping app(FMA) which calculates the area of farmlands based on GPS points collected. 

As the area is being calculated, the FMA also saves the GPS points and generates a boundary around the field that has been mapped. The database of FMA is fed to the Computer Vision App(CVA). As such, users can select the field to examine and a plot/polygon of the field is displayed. Users can have a view of what the polygon of the field looks like, where they are on the field, the randomly generated point to take the picture and the number of steps to walk to the point where the picture should be taken.

Users will not be allowed to take pictures unless they are within a 5 metre radius of the randomly generated point. As the user takes the picture, the app seeks to detect plants and calculates the distance between the plants. This distances between plants is thus used to estimate the germination rate of the farmland.
