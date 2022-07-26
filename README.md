# KaiYan

> 一款看视频的app，接口全部来自开眼app，主要是观看视频功能，不局限于短视频。

整体界面采用tab+vp+fragment，app主题颜色是黑白。大概12天吧，不知不觉就写了个这么个东西，发现相比期中，还是提升了很多，很有收获，UI也提升了很多，但也有很多不足。往下看。

## 功能展示

**分别是主界面、发现界面、热门界面、分类详情界面、播放视频界面、搜索界面。**

<img src="https://github.com/RQ527/KaiYan/blob/master/images/%E4%B8%BB%E7%95%8C%E9%9D%A2.gif" width="200" height="400" /><img src="https://github.com/RQ527/KaiYan/blob/master/images/%E5%8F%91%E7%8E%B0%E7%95%8C%E9%9D%A2.gif" width="200" height="400" />
<img src="https://github.com/RQ527/KaiYan/blob/master/images/%E7%83%AD%E9%97%A8%E7%95%8C%E9%9D%A2.gif" width="200" height="400" /><img src="https://github.com/RQ527/KaiYan/blob/master/images/%E5%88%86%E7%B1%BB%E8%AF%A6%E6%83%85%E7%95%8C%E9%9D%A2.gif" width="200" height="400" /><img src="https://github.com/RQ527/KaiYan/blob/master/images/%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E7%95%8C%E9%9D%A2.gif" width="200" height="400" /><img src="https://github.com/RQ527/KaiYan/blob/master/images/%E6%90%9C%E7%B4%A0%E7%95%8C%E9%9D%A2.gif" width="200" height="400" />

## 技术简介

整体框架是retrofit+okhttp+flow+coroutines+livedata+viewmodel+paging3，查看图片用的第三方库Photoview，视频播放用的DKPlayVideo。

仓库层用flow发射数据，viewmodel用livedata将数据通知给ui层。

刷新控件是继承自LinearLayout的自定义控件，目前还有小问题，不能响应recyclerview的fling，不能与协调者布局结合使用，但能响应RV滑动一半去刷新，或者刷新一半去滑动RV，这也是我感觉不足的地方。

自己封装的banner采用vp+imagview+handler的形式，handle每隔一段时间将vp翻页，vp的页数无限多，初始从中间开始翻滚，达到左右两边无限滑动的效果。

### 亮点

简单包装了banner图

使用flow将不同状态的数据获取情况发送出去，以通知UI数据获取情况（加载中，加载失败，加载成功）

项目里用了vp转场动画，共享元素动画，增强视觉效果。

使用pging3实现上拉加载，自定义的刷新控件实现下拉刷新

## 不足

嵌套滑动不太熟练，有些细节处理地不是很好。

## 感想

相比寒假考核和期中，自己的技术栈从java到kotlin，从httpUrlConnection到okheep再到retrofit，从runOnUiThread到Rxjava再到flow+协程，可以说提升了很大一节。当然这都得感谢网校给我这个机会，让我从啥都不会的小白，成长为一名coder。未来希望能进一步掌握嵌套滑动的知识，了解协程，flow，paging底层原理。

