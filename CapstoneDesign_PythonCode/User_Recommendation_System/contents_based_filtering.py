import pandas as pd
from datetime import datetime

def recommend(inputValue):
    user = []
    # 나이대
    age = datetime.today().year - inputValue[0]
    if age >= 64:
        user.append('노년')
    elif age >= 31:
        user.append('중장년')
    elif age >= 19:
        user.append('청년')
    else:
        user.append('청소년')
    # 성별
    if inputValue[1] == 'male':
        user.append(1)
    else:
        user.append(2)
    # 자녀
    if inputValue[2] == 'Yes':
        user.append(1)
    else:
        user.append(0)
    # 장애
    if inputValue[3] == 'Yes':
        user.append(1)
    else:
        user.append(0)
    # 저소득층
    if inputValue[4] == 'Yes':
        user.append(1)
    else:
        user.append(0)


    # 분류된 csv 파일 받아와서 딕셔너리에 넣기
    data = pd.read_csv('C:/Users/JUNGWOON/PycharmProjects/Assignment/Capstone_Design/User_Recommendation_System/welfare_service_class1.csv',
                       encoding="euc_kr", low_memory=False)

    dic = {}
    for i in range(len(data)):
        arr = []
        arr.append(data.iloc[i]["나이"])
        arr.append(int(data.iloc[i]["성별"]))
        arr.append(int(data.iloc[i]["자녀여부"]))
        arr.append(int(data.iloc[i]["장애여부"]))
        arr.append(int(data.iloc[i]["저소득층"]))
        dic[data.iloc[i]["서비스명"]] = arr

    def comparision(arr1, arr2):
        cnt = 0
        for i in range(len(arr1)):
            if arr1[i] == 1 and arr2[i] == 1:
                cnt += 1
        return cnt

    # 사용자 정보와 딕셔너리를 비교해 알맞은 서비스 상위 10개를 추천
    serviceArray = []
    for i in dic:
        if user[0] == dic[i][0]: # 나이대가 같은 경우
            if user[1] == 2 and dic[i][1] == 2: # 성별이 여성일 경우
                if comparision(user[2:], dic[i][2:]) == 3: # 나머지 요소 중 3가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 2: # 나머지 요소 중 2가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 1: # 나머지 요소 중 1가지가 같으면
                    serviceArray.append(i)
                else:
                    serviceArray.append(i)

            elif user[1] == 2 and dic[i][1] == 1: # 성별이 남성일 경우
                if comparision(user[2:], dic[i][2:]) == 3: # 나머지 요소 중 3가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 2: # 나머지 요소 중 2가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 1: # 나머지 요소 중 1가지가 같으면
                    serviceArray.append(i)
                else:
                    serviceArray.append(i)

            else: # 성별이 상관 없는 경우
                if comparision(user[2:], dic[i][2:]) == 3: # 나머지 요소 중 3가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 2: # 나머지 요소 중 2가지가 같으면
                    serviceArray.append(i)
                elif comparision(user[2:], dic[i][2:]) == 1: # 나머지 요소 중 1가지가 같으면
                    serviceArray.append(i)
                else:
                    serviceArray.append(i)

        if len(serviceArray) == 10:
            break

    return serviceArray

# inputValue = [1932, 'female', 'Yes', 'Yes', 'Yes'] # [나이, 성별(male/female), 자녀(Yes/No), 장애(Yes/No), 저소득층(Yes/No)]
# for i in recommend(inputValue):
#     print(i)
#
#
# "1932,female,Yes,Yes,Yes"

# print(recommend([1988, "female", "Yes", "Yes", "No"]))