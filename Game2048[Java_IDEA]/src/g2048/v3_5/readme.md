# 2048小游戏的说明

## CN(中文版)

#### 功能介绍

1. 管理员可以查看、修改用户个人信息[管理员账号:admin123,密码:111111]
2. 分为国内、国外2个版本
3. 用户可以注册、登录后玩

#### 特色

1. 国内注册的身份证号必须合法
2. 国内注册的性别与身份证号必须对应
3. 国内如果年龄小于18岁则只能玩指定时间且可以自定义，且年龄大于85岁将不能注册
4. 加入首页背景图片以及各个页面的图标
5. 登录进入游戏后会自动播放背景音乐
6. 拥有排行榜功能，从此游戏不再孤单
7. 含有关于界面，方便联系作者
8. 无需本地安装MySQL数据库，快捷游玩从我做起
9. 丰富玩法[3*3、4*4、5*5]，从此爱上2048
10. 支持超级终端的流转功能[Beta功能][仅支持国内版]

### 一.首次使用注意事项

1. 请导入`./v3_5/qudong/`目录下的2个依赖到你的开发环境中
2. 并且自行修改`./v3_5/lib/`目录下的`GMessage.java`文件中的`GMusicMessagePath`值为你的背景音乐的绝对路径
3. 可以修改`./v3_5/lib/`目录下的`GMessage.java`文件中的`GPlayTime`值为你想要未成年玩家玩的时间
4. 可以修改`./v3_5/lib/`目录下的`GMessage.java`文件中的`GBackupPicture`值为你想要的背景图片
5. 可以修改`./v3_5/lib/`目录下的`GMessage.java`文件中的`GPictureIcon`值为你想要的图标
6. 打开`./v3_5/`目录下的`Main2048.java`文件，并运行即可
7. 务必使用支持相对路径的编译器/环境，否则请自行修改图片的相对路径为绝对路径
8. 如遇一切Bug/问题，欢迎 [联系我](mailto:xcl@xuegao-tzx.top)

### 二.项目目录文件说明

1. `./v3_5/itf`为接口文件存放位置
2. `./v3_5/lib`为资源文件存放位置
3. `./v3_5/qudong`为所需依赖文件存放位置
4. `./v3_5/CNGame`为中文版程序主要的Java文件存放位置
5. `./v3_5/ENGame`为英文版程序主要的Java文件存放位置
6. `./v3_5/sql`为数据库相关文件存放位置
7. `./v3_5/tool`为程序集成功能相关的Java文件存放位置
8. `./v3_5/readme.md`为项目的说明文件

### 三.关于

[![Mail Badge](https://img.shields.io/badge/-xcl@xuegao--tzx.top-c14438?style=flat&logo=Gmail&logoColor=white&link=mailto:2013040111@st.nuc.edu.cn)](mailto:xcl@xuegao-tzx.top)
[![Github Badge](https://img.shields.io/badge/-xuegao--tzx-grey?style=flat&logo=github&logoColor=white&link=mailto:2013040111@st.nuc.edu.cn)](https://www.github.com/xuegao-tzx/)

- 🔭 I’m currently studying on NUC College
- 🌱 I’m currently learning Software Engineering
- 📫 How to reach
  me: [![Mail Badge](https://img.shields.io/badge/-xcl@xuegao--tzx.top-c14438?style=flat&logo=Gmail&logoColor=white&link=mailto:2013040111@st.nuc.edu.cn)](mailto:xcl@xuegao-tzx.top)

[2048系列项目地址](https://github.com/xuegao-tzx/2048Game)

### 总分工表

1. 数据库代码(t_user(CN).sql、t_user1.sql)
2. 数据库连接、执行数据库查询语句、执行数据库插入语句、执行数据库删除语句、执行数据库更新语句
3. 选择背景音乐、选择背景图片、设置背景音乐、设置程序图标
4. 中期报告、说明书实验报告、自拟题目任务书
5. 游戏布局界面设计、游戏布局界面实现、游戏注册功能实现、登录功能实现、游戏排行榜功能实现、3*3的界面、4*4的界面、5*
   5的界面、关于界面实现、退出功能实现、游戏数据保存功能、游戏数据读取功能、游戏中判空、游戏中生成随机数、游戏中键盘动作监听、游戏流转功能
6. 管理员改密功能、展示用户数据功能、管理员删除用户