from bs4 import BeautifulSoup as BS
import requests
from selenium import webdriver
import time

def crawling(Service_URL):
    driver = webdriver.Chrome('chromedriver.exe')
    search_url = Service_URL #웹 드라이버로 띄울 URL
    driver.get(search_url)
    time.sleep(3)

    html = driver.page_source  # 페이지의 elements 모두 가져오기
    soup = BS(html, 'html.parser')  # BeautifulSoup사용하기

    # '지원 대상' 크롤링 (변수 : target_for_support)
    target_for_support = ""
    for x in soup.select("#backup > div:nth-child(1) > div > ul > li > ul"):
        target_for_support += x.text
    # print(f"target_for_support : {target_for_support}")

    # '지원 내용' 크롤링 (변수 : contents_for_support)
    contents_for_support = ""
    for x in soup.select("#backup > div:nth-child(2) > div > ul > li > ul"):
        contents_for_support += x.text
    # print(f"contents_for_support : {contents_for_support}")

    # '신청 방법' 크롤링 (변수 : how_to_apply)
    how_to_apply = ""
    for x in soup.select("#backup > div:nth-child(3) > div > ul > li > ul"):
        how_to_apply += x.text
    # print(f"how_to_apply : {how_to_apply}")

    return target_for_support, contents_for_support, how_to_apply


import pandas as pd

csv_data = pd.read_csv('welfare_service.csv', encoding='CP949')
long = len(csv_data)
new_target_for_support = []
new_contents_for_support = []
new_how_to_apply = []
for i in range(long): # range(long)
    Service_ID = csv_data.loc[i, '서비스아이디']
    Service_Name = csv_data.loc[i, '서비스명']
    Service_URL = csv_data.loc[i, '서비스URL']
    Service_Summary = csv_data.loc[i, '서비스요약']

    Site = csv_data.loc[i, '사이트']
    Representative = csv_data.loc[i, '대표문의']
    Department_Name = csv_data.loc[i, '소관부처명']
    Organization_Name = csv_data.loc[i, '소관조직명']
    Base_Year = csv_data.loc[i, '기준년도']
    Final_Modification_Date = csv_data.loc[i, '최종수정일']

    target_for_support, contents_for_support, how_to_apply = crawling(Service_URL)
    new_target_for_support.append(target_for_support)
    new_contents_for_support.append(contents_for_support)
    new_how_to_apply.append(how_to_apply)


wel_file = pd.read_csv("./welfare_service.csv", encoding='CP949')
new_data = pd.DataFrame(index=range(long))

for col in wel_file:
    data = wel_file[col].values.reshape(-1,1)
    data.reshape(1,-1)
    new_data[col] = pd.DataFrame(data)

new_data['지원대상'] = new_target_for_support #지원대상 열에 크롤링한 데이터 삽입
new_data['지원내용'] = new_contents_for_support
new_data['신청방법'] = new_how_to_apply
new_data['지원대상'] = new_data['지원대상'].str.strip() #앞뒤 공백 제거(\n제거)
new_data['지원내용'] = new_data['지원내용'].str.strip()
new_data['신청방법'] = new_data['신청방법'].str.strip()
new_data.to_csv('new_welfare_service.csv' ,header=True, index=True, encoding='utf-8-sig')
