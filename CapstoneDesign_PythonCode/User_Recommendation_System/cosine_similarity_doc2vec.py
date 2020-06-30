from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
import pandas as pd

# 복지 데이터를 받는다
data = pd.read_csv('C:/Users/JUNGWOON/PycharmProjects/Assignment/Capstone_Design/User_Recommendation_System/new_welfare_service_1.csv',
                   encoding="euc_kr", low_memory=False)

# '서비스 요약' 속성에 따라 tf-idf를 수행한다
tfidf = TfidfVectorizer(stop_words='english')
tfidf_matrix = tfidf.fit_transform(data['서비스요약'].values.astype('U'))
print(tfidf_matrix.shape) # 360개의 복지 서비스, 2430개의 단어

# 코사인 유사도를 구한다.
cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)

# 복지 서비스명과 인덱스를 가진 테이블을 만든다
indices = pd.Series(data.index, index=data['서비스명']).drop_duplicates()

# 선택한 복지 서비스명에 대해서 코사인 유사도를 이용하여 가장 '서비스 요약'이 유사한 10개의 복지 서비스를 찾는 함수를 만든다.
def get_recommendations(title, cosine_sim=cosine_sim):
    # 선택한 서비스의 이름으로부터 해당되는 인덱스를 받아온다. 선택한 서비스를 가지고 연산을 진행한다.
    idx = indices[title]
    # 모든 서비스에 대해서 해당 서비스와의 유사도를 구한다.
    sim_scores = list(enumerate(cosine_sim[idx]))
    score_arr = []
    for i in sim_scores:
        if i[1] >= 0.09:
            score_arr.append(i)
    # 유사도에 따라 서비스들을 정렬한다.
    sim_scores = sorted(score_arr, key=lambda x: x[1], reverse=True)
    # 가장 유사한 10개의 서비스를 받아온다.
    sim_scores = sim_scores[1:6]
    # 가장 유사한 10개의 서비스의 인덱스를 받아온다.
    service_indices = [i[0] for i in sim_scores]
    arr = []
    for i in service_indices:
        arr.append(str(data['서비스아이디'].iloc[i]))
    # 가장 유사한 10개의 서비스의 이름을 리턴한다.
    return "/".join(arr)


# 코사인 유사도를 통해 구한 비슷한 복지 데이터들을 csv파일 형식으로 저장
f = open("doc2vec_result.csv", "w")

f.write("서비스아이디,유사서비스아이디\n")

serviceID = []
similarServiceID = []

for i in range(len(data)):
    serviceID.append(data.iloc[i]["서비스아이디"])
    similarServiceID.append(get_recommendations(data.iloc[i]["서비스명"]))

for i in range(len(serviceID)):
    f.write(f'{serviceID[i]},{similarServiceID[i]}\n')

f.close()
