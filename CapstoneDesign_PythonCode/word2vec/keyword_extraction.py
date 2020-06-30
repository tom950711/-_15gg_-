from wordcloud import WordCloud, STOPWORDS
import matplotlib.pyplot as plt

def generate_wordcloud(text):  # 워드클라우드 만드는 부분
    wordcloud = WordCloud(font_path="C:/Users/JUNGWOON/PycharmProjects/Assignment/Capstone_Design/word2vec/08+서울+남산체+EB.ttf",
                          width=2400,
                          height=1800,
                          ranks_only=None,
                          relative_scaling = 0.8,
                          stopwords = set(STOPWORDS)).generate(text)
    plt.imshow(wordcloud)
    plt.axis("off")
    plt.show()

import pandas as pd

csv_data = pd.read_csv('new_welfare_service_1.csv', encoding='euc_kr')
long = len(csv_data)
for i in range(144,145): # range(long)
    serviceID = csv_data.loc[i, '서비스아이디']
    serviceName = csv_data.loc[i, '서비스명']
    serviceSummary = csv_data.loc[i, '서비스요약']
    serviceTarget = csv_data.loc[i, '지원대상']

    sentence = serviceSummary + serviceTarget
    replaceSentence = sentence.replace("\n", " ")
    print(replaceSentence)
    generate_wordcloud(replaceSentence)
