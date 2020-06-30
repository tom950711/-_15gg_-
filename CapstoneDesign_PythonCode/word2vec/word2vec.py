import gensim
from gensim.models.word2vec import Word2Vec

ko_model = gensim.models.Word2Vec.load('C:/Users/JUNGWOON/PycharmProjects/Assignment/Capstone_Design/word2vec/ko/ko.bin')

f = open("asdf.csv", "w")
for i in ["국가", "유공자", "참전", "보훈", "군대", "군인", "전쟁", "복무"]:
    synonym = ko_model.wv.most_similar(i)
    s = []
    for j in synonym:
        s.append(f'"{j[0]}"')
    string = ",".join(s)
    print(string)
    ss = f'"{i}",{string}\n'
    f.write(ss)

f.close()
