# SegmentIntersectionPoint

Algorithm for finding the intersection point of two line segments.

Algorithm makes two lines from segments, finds the point of intersection of these lines using Cramer's rule and then checks if this point is located not only on lines, but on the initial segments also. 

If the constructed lines don't have a point of intersection then they're parallel. In this case algorithm checks if initial segments are just touching each other — end of one segment = beginning of another (so they're have exactly one point of intersection).

## Usage

To find a point of intersection of two line segments A—B and C—D create ```Point``` objects for the segments:

```java
Point a = new Point(x1, y1);
Point b = new Point(x2, y2);

Point c = new Point(x3, y3);
Point d = new Point(x4, y4);
```

Method ```pointOfIntersection(Point a, Point b, Point c, Point d)``` will return: 
* ```Point``` object with coordinates of intersection if exactly one point of intersection exists;
* ```null``` if no points of intersection exists or the line segments have many points of intersection.
