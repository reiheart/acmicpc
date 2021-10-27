
N = int(input())
v_array = [ None ]
v_array[0] = [ None, None ]
index = 0

def push_n_sort(v_cost):
    global N
    global index
    global v_array

    index += 1
    id = index
    v_array.append(v_cost)
    while id // 2 >= 1:
        up = id // 2
        if v_array[up][0] < v_array[id][0]:
            temp = v_array[up]
            v_array[up] = v_array[id]
            v_array[id] = temp
        else:
            break;
        id = up
    # print(v_array)

def pop_n_sort():
    global N
    global index
    global v_array

    out = 1
    v_cost = v_array[out]
    v_array[out] = v_array[index]
    index -= 1
    while out * 2 <= index:
        down = out * 2
        if v_array[down][0] < v_array[down-1][0]:
            down -= 1
        if v_array[out][0] < v_array[down][0]:
            temp = v_array[out]
            v_array[out] = v_array[down]
            v_array[down] = temp
        else:
            break;
        out = down

    # print(v_array)
    # print(v_cost)
    return v_cost

for n in range(N):
    v, c = map(int, input().split())
    temp_arr = []
    temp_arr.append(v)
    temp_arr.append(c)

    push_n_sort(temp_arr)

price = 0
cnt = 0
max_rt = 0
temp_arr = pop_n_sort()
accm_arr = [ temp_arr ]
while index >= 0:
    cnt += 1

    v_cnt = 0
    accm_deli = 0
    for i in range(cnt):
        if temp_arr[0] - accm_arr[i][1] > 0:
            v_cnt += 1
            accm_deli += accm_arr[i][1]

    rt = (temp_arr[0] * v_cnt) - accm_deli
    if rt > 0:
        if rt >= max_rt:
            max_rt = rt
            price = temp_arr[0]

    temp_arr = pop_n_sort()
    accm_arr.append(temp_arr)

if max_rt <= 0 :
    print("0")
else :
    print(price)
