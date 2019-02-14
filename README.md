# SegmentIntersectionPoint

Algorithm for finding the intersection point of two line segments.

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
