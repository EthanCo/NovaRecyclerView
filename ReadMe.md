## NovaRecyclerview ##
[![](https://jitpack.io/v/EthanCo/NovaRecyclerView.svg)](https://jitpack.io/#EthanCo/NovaRecyclerView)  

对LRecyclerview第三方库的二次封装  

**[LRecyclerView](https://github.com/jdsjlzx/LRecyclerView)**
  
1. 下拉刷新、滑动到底部自动加载下页数据；  
1. 可以方便添加Header和Footer；  
1. 头部下拉样式可以自定义；  
1. 具备item点击和长按事件；  
1. 网络错误加载失败点击Footer重新请求数据；    
1. 可以动态为FooterView赋予不同状态（加载中、加载失败、滑到最底等）；    

**通过封装，使其代码更优雅，编写代码更小，接口更小，耦合更低，更适合于公司项目开发**  

## 添加依赖 ##

### Step 1.

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
###Step 2.

	dependencies {
	        compile 'com.github.EthanCo:NovaRecyclerView:v1.1.5'
	}  

## 使用 ##

请看Sample实例代码  

	MainActivity为最原始版本  

	MainActivity2 精简了代码  

	MainActivity3 继承自封装后的BaseListActivity  