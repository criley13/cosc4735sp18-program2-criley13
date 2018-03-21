# Program # 2
Name:  Colin Riley

Cosc 4010

Description:  Runs on Pixel 2, api 27

Anything that doesn't work:  seems to work fine

# Graded: 47/50 #

* Location updates are one point behind where the picture was taken **(-3 points)** *see test case below*

Open app at Union, walked to Student Health and took a picture, walked to Classroom building and took a picture, walked to Engineering building and took a picture. -> Picture locations show that pictures were taken at Union, Student Health, and Classroom building.

To avoid this problem in the future it might be easier to simply update location on a fixed time interval. In this manner, taking a picture at any time should always give a reasonable estimate of where the user was located at the time of taking a picture.
