# BookArtifact （追书神器开发）
     
     
### 组长 : 宋水林 ###
 
`组员 : 宋水林 莫鹏飞 陈师表  方玲平` 

## 提交事项 ##
  
  * 布局文件书写 : 每个控件要写好注释，比如 :
 
 ```
           <!--登录输入框-->
        <EditText
        android:id="@+id/ba_login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="请输入用户名"
        />
```

  * 类名的书写 : 

 ```
 /**
 * 作者  ：xxx
 * 时间 ：xxxx-xx-xx
 * 描述 ： 说明你的该类的主要是干嘛的。能写详细就写详细点；
 * 
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
} 
```
  * 方法的书写 :
  
 ```
    /**
     * 作者  ：xxx
     * 时间 ：xxxx-xx-xx
     * 描述 ： 说明你的方法主要是干嘛的。能写详细就写详细点；
     * @param name  说明你的参数含义；
     * @param psd
     * 如有，返回值也要说明下；
     */
    public void login(String name ,String psd) {
        
    }
```
 ## 命名规范 ##
 
* 包的命名 
 
　　Java包的名字都是由小写单词组成。但是由于Java面向对象编程的特性，每一名Java程序员都可以编写属于自己的Java包，为了保障每个Java包命名的唯一性，在最新的Java编程规范中，要求程序员在自己定义的包的名称之前加上唯一的前缀。由于互联网上的域名称是不会重复的，所以程序员一般采用自己在互联网上的域名称作为自己程序包的唯一前缀。
  
　　例如： net.frontfree.javagroup 
* 类的命名 

　　 类的名字必须由大写字母开头而单词中的其他字母均为小写；如果类名称由多个单词组成，则每个单词的首字母均应为大写例如TestPage；如果类名称中包含单词缩写，则这个所写词的每个字母均应大写，如：XMLExample,还有一点命名技巧就是由于类是设计用来代表对象的，所以在命名类时应尽量选择名词。 　　
   
     例如： Circle 
* 方法的命名 

　　方法的名字的第一个单词应以小写字母作为开头，后面的单词则用大写字母开头。
  
    例如： sendMessge 
* 常量的命名 

　　常量的名字应该都使用大写字母，并且指出该常量完整含义。如果一个常量名称由多个单词组成，则应该用下划线来分割这些单词。
  
    例如： MAX_VALUE 
* 参数的命名 

　　参数的命名规范和方法的命名规范相同，而且为了避免阅读程序时造成迷惑，请在尽量保证参数名称为一个单词的情况下使参数的命名尽可能明确。

  
