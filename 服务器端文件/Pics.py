#coding:utf-8
'''
Created on 2016年2月4日

@author: Frank
'''
import re
import urllib2
import chardet
from locale import str
import sys
import threading
import json
names=[]

list_toutu=[]
list_zhengqian=[]
list_zhenghou=[]
list_zhengce=[]
list_xiehou=[]

list_chetoutexie=[]
list_cheweitexie=[]
list_qiandeng=[]
list_houdeng=[]
list_waihoushijing=[]

list_zhongkongquantu=[]
list_jiashiwei=[]
list_zhongkongtai=[]
list_yibiaopan=[]
list_dangba=[]

list_qianpaikongjian=[]
list_houpaikongjian=[]
list_houbeixiang=[]
list_qianmenban=[]
list_menchuangkongzhi=[]

list_qianlun=[]
list_fadongjicang=[]
list_houxuanjia=[]
list_yaoshi=[]

tupian=[
        list_toutu,list_zhengqian,list_zhenghou,list_zhengce,list_xiehou,
        list_chetoutexie,list_cheweitexie,list_qiandeng,list_houdeng,list_waihoushijing,
        list_zhongkongquantu,list_jiashiwei,list_zhongkongtai,list_yibiaopan,list_dangba,
        list_qianpaikongjian,list_houpaikongjian,list_houbeixiang,list_qianmenban,list_menchuangkongzhi,
        list_qianlun,list_fadongjicang,list_houxuanjia,list_yaoshi
        ]

class MyThread(threading.Thread):

    def __init__(self, pattern, itemNum, name,canshuid,selectedstring) :
        super(MyThread, self).__init__()  #调用父类的构造函数 
        self.pattern = pattern
        self.itemNum = itemNum
        self.name = name
        self.canshuid = canshuid
        self.selectedstring = selectedstring

    def run(self) :
#         print "Starting ", self.name
        show(self.pattern,self.itemNum,self.name,self.canshuid,self.selectedstring)
#         print "Exiting " , self.name

def show(pattern,itemNum,name,canshuid,selectedstring):
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+name+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
        items = re.findall(pattern,selectedstring)
        for i in range(itemNum):
                tupian[canshuid].append(items[0][i])




def threadsQueue(patterns,itemNum,names,count,selectedstring):
    thread_arr=[]
    for i in range(count):
        t=MyThread(patterns[i],itemNum[i],names[i],i,selectedstring)
        thread_arr.append(t)
        
    for i in range(count):
        thread_arr[i].start()
        
    for i in range(count):
        thread_arr[i].join()  


 
def getPicsById(carid):
    patterns=[]
    itemNum=[]
    basicUrl = 'http://car.autohome.com.cn/duibi/tupian/carids=' 
    url=basicUrl+str(carid)+',0,0,0'
    try:
        content = urllib2.urlopen(url).read()
        incodec=chardet.detect(content)['encoding']
        data=content.decode(incodec)
        data=data.encode('utf8')
#         print data


#1.外观
##################################################################
        p_waiguan=re.compile(r'<div class="content-title js-title"><i class="icon10 icon10-pack"></i><h3>外观</h3></div><div class="content-list  js-titems">.*?'+
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?', re.S)
        patterns.append(p_waiguan)
        itemNum.append(5)
        names.append('外观')
#         waiguan = re.findall(p_waiguan,data)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 外观>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(5):
#             print waiguan[0][i]




#2.外观细节类
##################################################################
        p_waiguanxijie=re.compile(r'<div class="content-title js-title"><i class="icon10 icon10-pack"></i><h3>外观细节类</h3></div><div class="content-list  js-titems">.*?'+
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?', re.S)
        patterns.append(p_waiguanxijie)
        itemNum.append(5)
        names.append('外观细节类')
#         waiguanxijie = re.findall(p_waiguanxijie,data)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 外观细节类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(5):
#             print waiguanxijie[0][i]




#3.中控类
##################################################################
        p_zhongkonglei=re.compile(r'<div class="content-title js-title"><i class="icon10 icon10-pack"></i><h3>中控类</h3></div><div class="content-list  js-titems">.*?'+
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?', re.S)
        patterns.append(p_zhongkonglei)
        itemNum.append(5)
        names.append('中控类')
#         zhongkonglei = re.findall(p_zhongkonglei,data)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 中控类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(5):
#             print zhongkonglei[0][i]




#4.车厢座椅
##################################################################
        p_chexiangzuoyi=re.compile(r'<div class="content-title js-title"><i class="icon10 icon10-pack"></i><h3>车厢座椅</h3></div><div class="content-list  js-titems">.*?'+
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?', re.S)                       
        patterns.append(p_chexiangzuoyi)
        itemNum.append(5)
        names.append('车厢座椅')
#         chexiangzuoyi = re.findall(p_chexiangzuoyi,data)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 车厢座椅>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(5):
#             print chexiangzuoyi[0][i]
#             
            
#5.其他细节类
##################################################################
        p_qitaxijie=re.compile(r'<div class="content-title js-title"><i class="icon10 icon10-pack"></i><h3>其他细节类</h3></div><div class="content-list  js-titems">.*?'+
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?'+ 
        '<ul class="list-ul">.*?<img src="(.*?)".*?">.*?', re.S)
        patterns.append(p_qitaxijie)
        itemNum.append(5)
        names.append('其他细节类')
#         qitaxijie = re.findall(p_qitaxijie,data)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 其他细节类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(4):
#             print qitaxijie[0][i]


        threadsQueue(patterns, itemNum, names,5,data)
    
    except Exception,msg:
        print msg

if __name__ == '__main__':
    a = sys.argv[1]
if a:
    getPicsById(int(a))
    encoded_json = json.dumps(tupian, ensure_ascii=False) # 将列表，进行json格式化编码
    print encoded_json #输出结果 
 
