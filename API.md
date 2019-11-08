# 1. WX-V1.0-API

<!-- TOC -->

- [1. WX-V1.0-API](#1-wx-v10-api)
  - [1.1. 东林时间](#东林时间)
    - [1.1.1. 查询状态](#111-查询状态)
    - [1.1.2. 查询tip](#112-查询tip)
    - [1.1.3. 查询通知详情](#113-查询通知详情)
    - [1.1.4. 更新通知](#114-更新通知)
  - [1.2. 损坏保修](#12-损坏报修)
  - [1.3. 意见反馈](#13-意见反馈)
  - [1.4. 公选课榜](#14-公选课榜)
    - [1.4.1. 限制刷票](#141-限制刷票)
    - [1.4.2. 添加课程票数](#142-添加课程票数)
    - [1.4.3. 查询票数](#143-查询票数)
  - [1.5. 运动王者](#15-运动王者)
    - [1.5.1. 解密微信用户openID和sessionkey](#151-解密微信用户openID和sessionkey)
    - [1.5.2. 添加运动数据](#152-添加运动数据)
    - [1.5.3. 查询运动数据](#153-查询运动数据)
  - [1.6. 跑跑记录](#16-跑跑记录)
    - [1.6.1. 添加跑步数据](#161-添加跑步数据)
    - [1.6.2. 查询跑步数据](#162-查询跑步数据)
  - [1.7. 论坛](#17-论坛)
    - [1.7.1. 查询帖子内容和评论](#171-查询帖子内容和评论)
    - [1.7.2. 查询所有帖子](#172-查询所有帖子)
    - [1.7.3. 关键字查询帖子](#173-关键字查询帖子)
    - [1.7.4. 添加帖子内容](#174-添加帖子)
    - [1.7.5. 添加帖子图片](#175-添加帖子图片)
    - [1.7.6. 添加评论](#176-添加评论)
    - [1.7.7. 查询当前用户的所有帖子](#177-查询当前用户的所有帖子)
    - [1.7.8. 删除当前用户的帖子](#178-删除当前用户的帖子)
  - [1.8. 版本控制](#18-版本控制)
  
<!-- /TOC -->


## 1.1. 东林时间


### 1.1.1 查询状态
- GET /colleage/time
- return :
  - data : library,electric,hall,hospital,bath,swim区域的状态
  
```json
{
    "code": 0,
    "message": "",
    "data": {
        "library": "http://pojtdp0ru.bkt.clouddn.com/book.png",
        "electric": "http://pojtdp0ru.bkt.clouddn.com/gongdian.png",
        "hall": "http://pojtdp0ru.bkt.clouddn.com/eat.png",
        "hospital": "http://pojtdp0ru.bkt.clouddn.com/hospital1.png",
        "bath": "http://pojtdp0ru.bkt.clouddn.com/water.png",
        "swim": "http://pojtdp0ru.bkt.clouddn.com/swim.png"
    }
}
```

---

### 1.1.2 查询tip
- GET /colleage/tip
- return :
  - data : 首页tip小建议
 
```json
{
    "code": 0,
    "message": "",
    "data": {
        "kind": "gongdian",
        "closable": "true",
        "tip": "停电有新通知"
    }
}
```

---

### 1.1.3 查询通知详情
- GET /colleage/content/{bumen}
- return :
  - bumen: 要查询部门的名称
  - data : 通知的详细信息
  
```json
{
    "code": 0,
    "message": "",
    "data": {
        "detail": "因供电局供电线路变更，需停电作业，具体安排如下：\r\n\r\n停电时间：4月10日早5:00—6:00时。\r\n\r\n停电范围：理学楼、动资楼、图书馆B区、文法楼、理学实验楼、丹青楼、锦绣楼、工程楼、学生公寓0#—6#楼、9#—11#楼、饮食中心、美食城、专家公寓高层、体育部、森林植物生态学教育部重点实验室等。\r\n\r\n届时因停电可能造成短时间停水、停暖，请各单位提前做好准备，给您工作、生活带来的不便敬请谅解！停电前10分钟禁止乘用电梯！",
        "time": "资产与后勤管理处\r\n资产与后勤管理处,二0一九年四月九日",
        "title": "停电通知"
    }
}
```

---

### 1.1.4 更新通知
- POST /mangeNefuContent
- payload :

```json
{
    "bumen" : "gongdian",
    "title" : "停电通知",
    "detail": "因供电局供电线路变更，需停电作业，具体安排如下：\r\n\r\n停电时间：4月10日早5:00—6:00时。\r\n\r\n停电范围：理学楼、动资楼、图书馆B区、文法楼、理学实验楼、丹青楼、锦绣楼、工程楼、学生公寓0#—6#楼、9#—11#楼、饮食中心、美食城、专家公寓高层、体育部、森林植物生态学教育部重点实验室等。\r\n\r\n届时因停电可能造成短时间停水、停暖，请各单位提前做好准备，给您工作、生活带来的不便敬请谅解！停电前10分钟禁止乘用电梯！",
    "time": "资产与后勤管理处\r\n资产与后勤管理处,二0一九年四月九日",
    "kind": "停电通知",
    "closable" : "true",
    "tip": "停电有新通知"
}
```

- return :
  - data : 更新状态,true=success;false=fail
  
```json
{
    "code": 0,
    "message": "",
    "data": true
}
```

---


## 1.2. 损坏保修
- POST /addUser
- payload :

```json
{
    "areaLocation" : "丹青",
    "areaContext" : "丹青楼120教室桌子不平", 
    "userName": "李希文",
    "userNum" : "2019年4月13日",
    "userPhone" : "13091896371"
}
```
- return 

```json
{
    "code": 0,
    "message": ""
}
```
  
---

## 1.3. 意见反馈
- POST /addFan
- payload :

```json
{
     "name" : "李希文",
     "number" : "2017214370",
     "phoneNumber" : "13091896371",
     "collageMajor" : "计算机科学与技术",
     "detail" : "加强老师上课"
}
```

- return 

```json
{
    "code": 0,
    "message": ""
}
```

---


## 1.4. 公选课榜


### 1.4.1 限制刷票
- GET /time
- payload :
 
```json
{
     "userName": "CLYZ"
 }
```

- return :
    直接返回一个boolean类型的值,true表示可以再次投票，false表示不可以再次投票
    boolean flag

---

###1.4.2. 添加课程票数
- POST /zan
- payload :
  like(i):要投票的每个类别的课程名称
  
```json
{
    "like1" : "",
    "like2" : "",
    "like3" : "",
    "like4" : "",
    "like5" : "",
    "like6" : ""
}
```

- return :
    无返回内容


---
###1.4.3. 查询票数

- GET /sort
  
- return :
  返回一个列表
  
```json
[
    {
        "course": "预测与决策方法",
        "zan": "0",
        "rank": "1"
    }
]
```

---
## 1.5. 运动王者



### 1.5.1. 解密微信用户openID和sessionkey

- GET /onlogin
- payload :

```json
{
    "code" : "061KDJO703uryE14NSO70PQKO70KDJO8"
}
```

- return :

```json
{
    "session_key":"gi67ozWUQAiMY0O98uYuqQ",
    "openid":"oqBcs5Fwg-Fkj86lxNgwrQXX3juI"
 }
```
  
---


### 1.5.2. 添加运动数据

- POST /decrypt
- payload :
  encryptedData : 微信步数的加密数据
  iv : 微信用户的iv
  sessionKey : onlogin接口返回的sessionKey
```json
{
    "encryptedData" : "",
    "iv" : "",
    "sessionKey" : ""
}
```
- return :

```json
{   "code":0,
    "message":"",
    "data":
      {
        "解密情况":"success"
      }
}
```

---


### 1.5.3. 查询运动数据

- GET /selectPace
- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "img": "wang",
            "paiwei": "冠军王者",
            "pace": 333333,
            "icon": "http://pojtdp0ru.bkt.clouddn.com/1.jpg",
            "name": "lalala",
            "rank": 1,
            "style": "#fff143"
        }
    ],
    "mydata": {
        "Ipaiwei": "健步铂金",
        "Istyle": "#0094ff",
        "Ipace": 23214,
        "Iicon": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL4Yf2awot0ofC2x4WOQUXseDA6w0IdqGNgAZknf6yY3AnFhymzJ10zwwmwdIGP9yggdJX0H0BEEg/132",
        "Iimg": "bo",
        "Irank": 11,
        "Iname": "Hay."
    }
}
```


---



## 1.6. 跑跑记录


### 1.6.1. 添加跑步数据

- POST /rundata
- payload :

```json
{
    "kilometer" : "0.00", 
    "runTime" : "0:00:04 ", 
    "nickName" : "Hayashi Saki"
}
```

- return :

```json
{   "code":0,
    "message":"",
    "data":"insert user rundata success"
}
```

---


### 1.6.2. 查询跑步数据

- GET /selectRundata
- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "systemId": null,
            "userOpenId": "oqBcs5Fwg-Fkj86lxNgwrQXX3juI",
            "kilometer": "0.00",
            "nickName": "Hayashi  Saki",
            "runTime": "0:00:04 ",
            "runRank": null
        }
    ]
}
```


## 1.7. 论坛


### 1.7.1. 查询帖子内容和评论

- GET /detail/{systemId}
- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "commentsNumber": 0,
            "systemId": 119,
            "likesNumber": 0,
            "userPaiweiImg": "bo",
            "icon": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL4Yf2awot0ofC2x4WOQUXseDA6w0IdqGNgAZknf6yY3AnFhymzJ10zwwmwdIGP9yggdJX0H0BEEg/132",
            "viewsNumber": 0,
            "userPaiwei": "健步铂金",
            "writer": "Hayashi  Saki",
            "time": "2019-04-13 19:28:00",
            "title": "凄凄切切",
            "category": "日常交流",
            "content": "风风光光"
        }
    ],
    "mydata": []
}
```

---


### 1.7.2. 查询所有帖子
- GET /community
- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "userPaiweiImg": "bo",
            "viewsNumber": 0,
            "icon": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL4Yf2awot0ofC2x4WOQUXseDA6w0IdqGNgAZknf6yY3AnFhymzJ10zwwmwdIGP9yggdJX0H0BEEg/132",
            "userPaiwei": "健步铂金",
            "id": 126,
            "writer": "Hayashi  Saki",
            "pic": "http://pojtdp0ru.bkt.clouddn.com/2019/04/13/19224a25-57ca-4c80-9bee-3ff24e8e222a.jpg",
            "time": "2019-04-13 19:53:27",
            "title": "有电视剧《都挺好》的粉丝吗",
            "category": "日常交流"
        }
    ]
}
```


---


### 1.7.3. 关键字查询帖子
- GET /community/search
- payload :

```json
{
   "keyword" : "互联网"
}
```

- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "userPaiweiImg": "da",
            "viewsNumber": 0,
            "icon": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIaicXBbiazZwIicI6WSicFlcuCXEgz0lfBP64ciaRgGrMLSjjYad9AFPaOXF2NBOXxiaWlVuCDcvW3dSWg/132",
            "userPaiwei": "力行大师",
            "id": 123,
            "time": "2019-04-13 19:45:54",
            "writer": "CLYZ",
            "pic": "http://pojtdp0ru.bkt.clouddn.com/2019/04/13/b73448fd-85ec-460d-aec4-098a8755b1ea.jpg",
            "title": "互联网＋比赛求队友",
            "category": "竞赛组队"
        }
    ]
}
```

---


### 1.7.4. 添加帖子内容
- POST /post
- payload :
```json
{
    "content" : "今年国赛建模缺一个会matlab的大佬", 
    "title" : "国赛建模比赛组队",
    "category" : "竞赛组队", 
    "writer" : "Hayashi Saki"
}
```

- return :

```json
{
    "code":0,
    "message":"",
    "data":true
}
```

---


### 1.7.5. 添加帖子图片
- POST /postpics
- payload :
  file是一个文件
```json
{
    "file" : "xx.jpg"
}
```
- return 

```json
{
    "code": 0,
    "message": "",
    "data": "true"
}
```

---


### 1.7.6. 添加评论
- POST /detail/comment
- payload :

```json
{
    "content" : "我觉得我可以",
    "systemId" : "128", 
    "writer" : "CLYZ"
}
```
- return :
  - data :true表示评论成功,false表示评论失败
```json
{
    "code":0,
    "message":"",
    "data":true
}
```

---


### 1.7.7. 查询当前用户的所有帖子
- GET /my/{oppid}
- return :

```json
{
    "code": 0,
    "message": "",
    "data": [
        {
            "commentsNumber": 0,
            "userPaiweiImg": "bo",
            "icon": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL4Yf2awot0ofC2x4WOQUXseDA6w0IdqGNgAZknf6yY3AnFhymzJ10zwwmwdIGP9yggdJX0H0BEEg/132",
            "userPaiwei": "健步铂金",
            "id": 120,
            "time": "2019-04-13 19:29:14",
            "pic": "http://pojtdp0ru.bkt.clouddn.com/2019/04/13/3901565b-25fc-496d-a8ef-cbabb408668a.jpg",
            "writer": "Hayashi  Saki",
            "title": "换手机大家",
            "category": "创意分享"
        }
    ]
}
```


---


### 1.7.8. 删除当前用户的帖子
- POST /my/delete/{systemId}
- return :
  - data :true表示删除成功,false表示删除失败
  
```json
{
    "code": 0,
    "message": "",
    "data": true
}
```

---

## 1.8. 版本控制
- GET /version
- return :

```json
{
    "code": 0,
    "message": "",
    "data": {
        "data": "东北林业大学官方体验版发布，五大功能全部上线，可以正常使用",
        "version": "V1.0.0"
    }
}
```