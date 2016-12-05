#coding:utf-8
'''
Created on 2016年2月1日

@author: Frank
'''
from locale import str
import re
import sys
import threading
import urllib2
import json
import chardet




kv_chexingxinxi={} 
kv_jibencanshu={} 
kv_cheshen={} 
kv_fadongji={} 
kv_biansuxiang={} 
kv_dipanzhuanxiang={} 
kv_anquanzhuangbei={} 
kv_caozuopeizhi={} 
kv_waibupeizhi={} 
kv_neibupeizhi={} 
kv_zuoyipeizhi={} 
kv_duomeitipeizhi={} 
kv_dengguangpeizhi={}
kv_bolihoushijing={}
kv_kongtiaobingxiang={} 
kv_gaokeji={} 
# kv_xuanzhuangbao={} 
canshu=[
        kv_chexingxinxi,kv_jibencanshu,kv_cheshen,kv_fadongji,
        kv_biansuxiang,kv_dipanzhuanxiang,kv_anquanzhuangbei,kv_caozuopeizhi,
        kv_waibupeizhi,kv_neibupeizhi,kv_zuoyipeizhi,kv_duomeitipeizhi,
        kv_dengguangpeizhi,kv_bolihoushijing,kv_kongtiaobingxiang,kv_gaokeji
        ]
names=[]
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
        for i in range(0,itemNum,2):
                canshu[canshuid][items[0][i]]=items[0][i+1]




def threadsQueue(patterns,itemNum,names,count,selectedstring):
    thread_arr=[]
    for i in range(count):
        t=MyThread(patterns[i],itemNum[i],names[i],i,selectedstring)
        thread_arr.append(t)
        
    for i in range(count):
        thread_arr[i].start()
        
    for i in range(count):
        thread_arr[i].join()    




def getCanshuById(carid) :
#     user_agent = r"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36"
#     headers = { 'User-Agent' : user_agent }
    patterns=[]
    itemNum=[]
    basicUrl = 'http://car.autohome.com.cn/duibi/chexing/carids=' 
    url=basicUrl+str(carid)+',0,0,0'
    try:
        content = urllib2.urlopen(url).read()
        incodec=chardet.detect(content)['encoding']
        data=content.decode(incodec)
        data=data.encode('utf8')
        
        pattern_firstmenu = re.compile(r'<table id="tbSpecInfo" class="js-hitems tableinfo">(.*?)<table class="tableinfo js-bitems">'
                        ,re.S)
        selectedpage = re.findall(pattern_firstmenu,data)
        selectedstring=selectedpage[0]
#         selectedstring=data
#         print selectedstring

#1.获取    车型信息、厂商指导价
##################################################################
        p_chexingxinxi=re.compile(r'<tr><th class="title redtitle">(.*?)</th>.*?target="_blank">(.*?)</a>.*?<tr><th>(.*?)</th><td>(.*?)<a.*?>', re.S)
        patterns.append(p_chexingxinxi)
        itemNum.append(4)
        names.append('车型信息、厂商指导价')
#         chexingxinxi = re.findall(p_chexingxinxi,selectedstring)
#         #键：车型信息
#         #值
#         #键：厂商指导价
#         #值
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 车型信息、厂商指导价>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(4):
#             print chexingxinxi[0][i]
#2.获取    基本参数
##################################################################

        p_jibencanshu=re.compile(r'<div class="js-title genre-title" data-title="基本参数"></i>.*?<table class="js-titems tableinfo">'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#厂商
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#级别
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#发动机
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#变速箱
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#长*宽*高(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#车身结构
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最高车速(km/h)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#官方0-100km/h加速(s)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#实测0-100km/h加速(s)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#实测100-0km/h制动(m)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#实测油耗(L/100km)
        '<tr><th class="title"><span>(.*?)</span></th><td class="text"><div.*?>(.*?)</div>.*?'+ #认证车主平均油耗(L/100km)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#工信部综合油耗(L/100km)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#实测离地间隙(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text"><a.*?target="_blank">(.*?)</a></td>.*?', re.S)#整车质保
#         jibencanshu = re.findall(p_jibencanshu,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 基本参数>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(30):
#                 print jibencanshu[0][i]
        patterns.append(p_jibencanshu)
        itemNum.append(30)
        names.append('基本参数')

#3.获取  车身
##################################################################

        p_cheshen=re.compile(r'<div class="js-title genre-title" data-title="车身"></i>.*?<table class="js-titems tableinfo">'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#长度(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#宽度(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#高度(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#轴距(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#前轮距(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#后轮距(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最小离地间隙(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#整备质量(kg)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#车身结构
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#车门数(个)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#座位数(个)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#油箱容积(L)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?', re.S)#行李厢容积(L)
        cheshen = re.findall(p_cheshen,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 车身>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(26):
#                 print i,cheshen[0][i]
        patterns.append(p_cheshen)
        itemNum.append(26)
        names.append('车身')


#4.获取  发动机
##################################################################

        p_fadongji=re.compile(r'<div class="js-title genre-title" data-title="发动机"></i>.*?<table class="js-titems tableinfo">'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#发动机型号
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#排量(mL)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#进气形式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#气缸排列形式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#气缸数(个)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#每缸气门数(个)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#压缩比
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#配气机构
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#缸径(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#行程(mm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最大马力(Ps)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最大功率(kW)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最大功率转速(rpm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最大扭矩(N·m)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#最大扭矩转速(rpm)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#发动机特有技术
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#燃料形式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#燃油标号
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#供油方式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#缸盖材料
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#缸体材料
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?', re.S)#环保标准
#         fadongji = re.findall(p_fadongji,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 发动机>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(44):
#                 print i,fadongji[0][i]
        patterns.append(p_fadongji)
        itemNum.append(44)
        names.append('发动机')
#5.获取 变速箱
##################################################################

        p_biansuxiang=re.compile(r'<div class="js-title genre-title" data-title="变速箱"></i>.*?<table class="js-titems tableinfo">'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#简称
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#挡位个数
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?', re.S)#变速箱类型
        biansuxiang = re.findall(p_biansuxiang,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  变速箱>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(6):
#                 print i,biansuxiang[0][i]
        patterns.append(p_biansuxiang)
        itemNum.append(6)
        names.append('变速箱')


#6.获取 底盘转向
##################################################################

        p_dipanzhuanxiang=re.compile(r'<div class="js-title genre-title" data-title="底盘转向"></i>.*?<table class="js-titems tableinfo">'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#驱动方式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#前悬架类型
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#后悬架类型
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?'+#助力类型
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a>.*?<td class="text">(.*?)</td>.*?', re.S)#车体结构
#         dipanzhuanxiang = re.findall(p_dipanzhuanxiang,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  底盘转向>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(10):
#                 print i,dipanzhuanxiang[0][i]
        patterns.append(p_dipanzhuanxiang)
        itemNum.append(10)
        names.append('底盘转向')
#7.获取 安全装备
##################################################################

        p_anquanzhuangbei=re.compile(r'<div class="js-title genre-title" data-title="安全装备" ></i><i class="icon10 icon10-pack"></i><h3>安全装备</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#主/副驾驶座安全气囊
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后排侧气囊
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后排头部气囊(气帘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#膝部气囊
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#胎压监测装置
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#零胎压继续行驶
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#安全带未系提示
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#ISOFIX儿童座椅接口
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#发动机电子防盗
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车内中控锁
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#遥控钥匙
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#无钥匙启动系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#无钥匙进入系统
#         anquanzhuangbei = re.findall(p_anquanzhuangbei,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   安全装备>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(26):
#             print i,anquanzhuangbei[0][i]
        patterns.append(p_anquanzhuangbei)
        itemNum.append(26)
        names.append('安全装备')
#8.获取 操控配置
##################################################################

        p_caozuopeizhi=re.compile(r'<div class="js-title genre-title" data-title="操控配置" ></i><i class="icon10 icon10-pack"></i><h3>操控配置</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#ABS防抱死
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#制动力分配(EBD/CBC等)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#刹车辅助(EBA/BAS/BA等)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#牵引力控制(ASR/TCS/TRC等)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车身稳定控制(ESC/ESP/DSC等)
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#上坡辅助
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#自动驻车
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#陡坡缓降
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#可变悬架
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#空气悬架
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#可变转向比
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前桥限滑差速器/差速锁
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#中央差速器锁止功能
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#后桥限滑差速器/差速锁
#         caozuopeizhi = re.findall(p_caozuopeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   操控配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(28):
#             print i,caozuopeizhi[0][i]
        patterns.append(p_caozuopeizhi)
        itemNum.append(28)
        names.append('操控配置')        
                
                
#9.获取 外部配置
##################################################################

        p_waibupeizhi=re.compile(r'<div class="js-title genre-title" data-title="外部配置" ></i><i class="icon10 icon10-pack"></i><h3>外部配置</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#电动天窗
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#全景天窗
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#运动外观套件
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#铝合金轮圈
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#电动吸合门
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#侧滑门
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#电动后备厢
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#感应后备厢
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?', re.S)#车顶行李架
#         waibupeizhi = re.findall(p_waibupeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   外部配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(18):
#             print i,waibupeizhi[0][i]
        patterns.append(p_waibupeizhi)
        itemNum.append(18)
        names.append('外部配置')

#10.获取 内部配置（坑）
##################################################################

        p_neibupeizhi=re.compile(r'<div class="js-title genre-title" data-title="内部配置" ></i><i class="icon10 icon10-pack"></i><h3>内部配置</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#真皮方向盘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#方向盘调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#方向盘电动调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#多功能方向盘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#方向盘换挡
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#方向盘加热
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#方向盘记忆
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#定速巡航
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后驻车雷达
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#倒车视频影像
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#行车电脑显示屏
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#全液晶仪表盘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#HUD抬头数字显示
#         neibupeizhi = re.findall(p_neibupeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   内部配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(26):
#             print i,neibupeizhi[0][i]
        patterns.append(p_neibupeizhi)
        itemNum.append(26)      
        names.append('内部配置')        
                
#11.获取 座椅配置
##################################################################

        p_zuoyipeizhi=re.compile(r'<div class="js-title genre-title" data-title="座椅配置" ></i><i class="icon10 icon10-pack"></i><h3>座椅配置</h3>.*?'+
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#座椅材质
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#运动风格座椅
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#座椅高低调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#腰部支撑调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#肩部支撑调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#主/副驾驶座电动调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#第二排靠背角度调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#第二排座椅移动
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后排座椅电动调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#电动座椅记忆
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后排座椅加热
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后排座椅通风
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后排座椅按摩
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#第三排座椅
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后排座椅放倒方式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后中央扶手
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#后排杯架
#         zuoyipeizhi = re.findall(p_zuoyipeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   座椅配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(34):
#             print i,zuoyipeizhi[0][i]
        patterns.append(p_zuoyipeizhi)
        itemNum.append(34)      
        names.append('座椅配置')        
#12.获取 多媒体配置
##################################################################

        p_duomeitipeizhi=re.compile(r'<div class="js-title genre-title" data-title="多媒体配置" ></i><i class="icon10 icon10-pack"></i><h3>多媒体配置</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#GPS导航系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#定位互动服务
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#中控台彩色大屏
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#蓝牙/车载电话
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车载电视
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后排液晶屏
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#220V/230V电源
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#外接音源接口
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#CD支持MP3/WMA
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#多媒体系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#扬声器品牌
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#扬声器数量
#         duomeitipeizhi = re.findall(p_duomeitipeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   多媒体配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(24):
#             print i,duomeitipeizhi[0][i]
        patterns.append(p_duomeitipeizhi)
        itemNum.append(24)
        names.append('多媒体配置')



#13.获取 灯光配置
##################################################################

        p_dengguangpeizhi=re.compile(r'<div class="js-title genre-title" data-title="灯光配置" ></i><i class="icon10 icon10-pack"></i><h3>灯光配置</h3>.*?'+
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#近光灯
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#远光灯
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#日间行车灯
        'tr><th class="title"><span>(.*?)</span></th><td  class="text">(.*?)</td>.*?'+#自适应远近光
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#自动头灯
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#转向辅助灯
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#转向头灯
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前雾灯
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#大灯高度可调
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#大灯清洗装置
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#车内氛围灯
#         dengguangpeizhi = re.findall(p_dengguangpeizhi,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   灯光配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(22):
#             print i,dengguangpeizhi[0][i]
        patterns.append(p_dengguangpeizhi)
        itemNum.append(22)
        names.append('灯光配置')


#14.获取 玻璃/后视镜
##################################################################

        p_bolihoushijing=re.compile(r'<div class="js-title genre-title" data-title="玻璃/后视镜" ></i><i class="icon10 icon10-pack"></i><h3>玻璃/后视镜</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#前/后电动车窗
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车窗防夹手功能
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#防紫外线/隔热玻璃
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后视镜电动调节
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后视镜加热
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#内/外后视镜自动防眩目
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后视镜电动折叠
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后视镜记忆
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后风挡遮阳帘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后排侧遮阳帘
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#遮阳板化妆镜
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后雨刷
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#感应雨刷
#         bolihoushijing = re.findall(p_bolihoushijing,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>    玻璃/后视镜>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(26):
#             print i,bolihoushijing[0][i]
        patterns.append(p_bolihoushijing)
        itemNum.append(26)          
        names.append('玻璃/后视镜')        
                
#15.获取 空调/冰箱
##################################################################

        p_kongtiaobingxiang=re.compile(r'<div class="js-title genre-title" data-title="空调/冰箱" ></i><i class="icon10 icon10-pack"></i><h3>空调/冰箱</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#空调控制方式
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后排独立空调
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#后座出风口
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#温度分区控制
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车内空气调节/花粉过滤
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#车载冰箱
#         kongtiaobingxiang = re.findall(p_kongtiaobingxiang,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   空调/冰箱>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(12):
#             print i,kongtiaobingxiang[0][i]
        patterns.append(p_kongtiaobingxiang)
        itemNum.append(12)
        names.append('空调/冰箱')


#16.获取 高科技配置
##################################################################

        p_gaokeji=re.compile(r'<div class="js-title genre-title" data-title="高科技配置" ></i><i class="icon10 icon10-pack"></i><h3>高科技配置</h3>.*?'+
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#自动泊车入位
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#发动机启停技术
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#并线辅助
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#车道偏离预警系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#主动刹车/主动安全系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#整体主动转向系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#夜视系统
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#中控液晶屏分屏显示
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?'+#自适应巡航
        '<tr><th class="title"><a.*?target="_blank">(.*?)</a></th><td  class="text">(.*?)</td>.*?', re.S)#全景摄像头
#         gaokeji = re.findall(p_gaokeji,selectedstring)
#         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   高科技配置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
#         for i in range(20):
#             print i,gaokeji[0][i]
        patterns.append(p_gaokeji)
        itemNum.append(20)
        names.append('高科技配置')        
# #17.获取 选装包
# ##################################################################
# 
#         p_xuanzhuangbao=re.compile(r'<div class="js-title genre-title" data-title="选装包" ></i><i class="icon10 icon10-pack"></i><h3>选装包</h3>.*?'+
#         '<tr><th rowspan="2" class="title"><span>(.*?)</span></th><td class="text"><div class="optional" data-toggle="popup".*?><p>(.*?)</p>', re.S)#后视镜选装包
# #         xuanzhuangbao = re.findall(p_xuanzhuangbao,selectedstring)
# #         print ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   选装包>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# #         for i in range(2):
# #             print i,xuanzhuangbao[0][i]
#         patterns.append(p_xuanzhuangbao)
#         itemNum.append(2)
#         names.append('选装包')                     
#                 
        threadsQueue(patterns, itemNum, names,16,selectedstring)        
    except Exception,msg:
        print msg


if __name__ == '__main__':
    a = sys.argv[1]
if a:
    getCanshuById(int(a))
    encoded_json = json.dumps(canshu, ensure_ascii=False) # 将列表，进行json格式化编码
    print encoded_json #输出结果 