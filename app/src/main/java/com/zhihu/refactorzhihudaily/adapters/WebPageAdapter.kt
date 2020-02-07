import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.viewpager.widget.PagerAdapter

class WebPageAdapter(var webViewList: List<WebView>?) : PagerAdapter() {

    override fun getCount(): Int {
        return webViewList!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(webViewList!![position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(webViewList!![position])
        return webViewList!![position]
    }
}
