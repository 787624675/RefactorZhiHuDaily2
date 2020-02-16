package com.zhihu.refactorzhihudaily.network

class HtmlUtil {
    //为了实现webview的夜间模式创建的类

    // css样式，隐藏header
    val HIDE_HEADER_STYLE : String = "<style>div.headline{display:none;}</style>"

    // css style tag, 需要格式化
    val NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>"

    // js script tag, 需要格式化
    val NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>"
    val MIME_TYPE = "text/html; charset=utf-8"
    val ENCODING = "utf-8"

      //根据css链接生成Link标签
    fun createCssTag(url:String):String {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    //根据多个css链接生成Link标签
    fun createCssTag(urls:List<String>):String {
        val sb  = StringBuilder();
        urls.forEach {
            sb.append(createCssTag(it));
        }
        return sb.toString()
    }

    //根据js链接生成Script标签
    fun createJsTag( url:String) = String.format(NEEDED_FORMAT_JS_TAG, url);

    //根据多个js链接生成Script标签
    fun createJsTag( urls:List<String>):String {

        val sb =  StringBuilder();
        urls.forEach {
            sb.append(createJsTag(it));
        }
        return sb.toString();
    }

    //根据样式标签,html字符串,js标签,生成完整的HTML文档
    fun createHtmlData( html:String,  cssList:List<String>,  jsList:List<String>):String {
        val css = createCssTag(cssList);
        val js = createJsTag(jsList);
        return css+HIDE_HEADER_STYLE+html+js
    }

    fun createNightHtmlData( html:String,  cssList:List<String>,  jsList:List<String>):String {
        val css = createCssTag(cssList)+ "</head><body class=\"night\">"
        val js = createJsTag(jsList);
        return css+HIDE_HEADER_STYLE+html+js
    }






}