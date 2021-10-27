x, y = map(int, input().split())

if x == y or x ==0 :
    print("-1")
else :
    z = (y * 100) // x
    if z == 99 :
        print("-1")
    else :
        n = ((z * x) + x - (100 * y)) // (99 - z)
        nz = ((y + n) * 100) // (x + n)
        if nz > z :
            print(n)
        else :
            print(n + 1)
