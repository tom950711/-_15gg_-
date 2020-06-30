import contents_based_filtering as cbf
import pandas as pd
import socket

host = '172.30.1.16'   # 내 서버의 ip 적기
port = 9999            # 서버에서 사용할 포트번호 설정

# 서버를 오픈(클라이언트가 요철할 때까지 대기)
server_sock = socket.socket(socket.AF_INET)
server_sock.bind((host, port))
server_sock.listen(1)

print("Waiting...")
client_sock, addr = server_sock.accept()

# 클라이언트가 서버에 접속하여 자신의 정보 데이터를 넘김
# 서버는 사용자의 정보 데이터를 csv파일에 맞게 수정
print('Connected by', addr)
data = client_sock.recv(1024)
data = data.decode("utf-8")

index = []
for i in range(len(data)):
    if data[i] == "<":
        index.append(i)
    elif data[i] == ">":
        index.append(i)

inputValue = data[index[0]+1:index[1]].split(",")
inputValue[0] = int(inputValue[0])
print(inputValue)

# 미리 작성해 둔 recommend 외부 모듈을 사용해 사용자의 정보로 contents_based_filtering 수행
outputValue = cbf.recommend(inputValue)
print(outputValue)

# 사용자에게 추천할 만한 복지를 선택하여 index 값을 다시 클라이언트에 넘겨줌
file = pd.read_csv('C:/Users/JUNGWOON/PycharmProjects/Assignment/Capstone_Design/User_Recommendation_System/welfare_service_class1.csv',
                       encoding="euc_kr", low_memory=False)

num_arr = []
for i in outputValue:
    num = file[file["서비스명"] == i]["index"].values[0]
    if num <= 255:
        num_arr.append(num)
print(num_arr)

for i in num_arr:
    data2 = int(i)
    client_sock.send(data.encode())
    client_sock.send(data2.to_bytes(4, byteorder='little'))
    # 안드로이드에서 값 받으면 "하나받았습니다 : 숫자" 보낼 것 받음

# 클라이언트에 index 값을 모두 넘겨주면 서버와 클라이언트의 연결을 끊음
client_sock.close()
server_sock.close()
