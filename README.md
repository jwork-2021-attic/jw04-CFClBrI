[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=5880009&assignment_repo_type=AssignmentRepo)
# W04


请阅读`gourds`分支上示例项目代码。该项目运行后会打开一个图形化窗口，并在其中绘制内容。

## 技术背景

`gourds`分支的图形化绘制部分采用了github上开源的[AsciiPanel](https://github.com/trystan/AsciiPanel)。因对其进行了一点点修改，故直接将其源代码放在`asciiPanel`目录下（此部分代码可不阅读）。AsciiPanel模拟了早期计算机终端的现实方式，因其所具有的复古风，还有基于它所实现的Roguelike类型RPG游戏，如`Roguelike`分支上项目所示。

## 任务一

请仔细阅读`gourds`分支项目代码，理解其运作原理，重点理解泛型相关代码，包括：
- `Tile`类型
- `Thing`类型中的`Tile<? extends Thing> tile`属性
- `Sorter`类型、其子类`BubbleSorter`以及`Comparable`接口

请新建`matrix`分支，并在该分支上仿照示例代码，实现妖精方阵排序。


> 注意：请将方阵定义为一个二维数据结构，而不是定义为一位数组后分行输出（W02中很多人这样偷懒）。请谨记用代码**模拟**现实。

代码运行结果请截屏发QQ群内。

## 任务二

请新建`maze`分支，并在该分支上实现迷宫生成算法，然后实现葫芦娃走迷宫（可以用算法指挥葫芦娃走出迷宫，或手工操作键盘指挥葫芦娃走迷宫）

迷宫生成算法可直接用https://github.com/oppenheimj/maze-generator。

>切记，本课程作业的重点不是算法实现，而是正确写出面向对象风格的代码。

代码运行结果请截屏发QQ群内。