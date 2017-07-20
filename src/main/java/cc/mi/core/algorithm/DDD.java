package cc.mi.core.algorithm;

public class DDD {

}

/**
(-a)*x + (-b)*y + (-c)*z + (-d)*w <= -H
x <= g1
y <= g2
z <= g3
w <= g4

(-a)*x - b*y <= c*g3 + d*g4 - H 


| -a -b -c -d |   |x1|    |-H|
|  1  0  0  0 |   |x2|    |g1|
|  0  1  0  0 | . |x3| <= |g2|
|  0  0  1  0 |   |x4|    |g3|
|  0  0  0  1 |           |g4|

x + y <= x1 + y1
x + z <= x1 + z1
x + w <= x1 + w1
y + z <= y1 + z1
y + w <= y1 + w1
z + w <= z1 + w1
*/