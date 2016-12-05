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

def getPicsById(carid):
    basicUrl = 'http://car.autohome.com.cn/duibi/chexing/carids=' 
    url=basicUrl+str(carid)+',0,0,0'
    try:
        content = urllib2.urlopen(url).read()
        incodec=chardet.detect(content)['encoding']
        data=content.decode(incodec)
        data=data.encode('utf8')
  
        p_chexingxinxi=re.compile(r'<tr><th class="title redtitle">.*?</th>.*?target="_blank">.*?</a>.*?<tr><th>.*?</th><td>(.*?)<a.*?>', re.S)
         
        chexingxinxi = re.findall(p_chexingxinxi,data)
        #键：车型信息
        #值
        #键：厂商指导价
        #值
        #print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 厂商指导价>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(1):
        print chexingxinxi[0]
        
    
    except Exception,msg:
        print(msg)
    
if __name__ == '__main__':
    a = sys.argv[1]
if a:
    getPicsById(int(a))
#     getPicsById(22989)