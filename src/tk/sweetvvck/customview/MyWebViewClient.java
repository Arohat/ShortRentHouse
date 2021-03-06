package tk.sweetvvck.customview;

import tk.sweetvvck.shortrendhouse.activity.HouseDetailActivity;
import tk.sweetvvck.shortrendhouse.activity.MainActivity;
import tk.sweetvvck.shortrendhouse.activity.VVHouseDetailActivity;
import tk.sweetvvck.utils.HttpUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
	Context context;

	public MyWebViewClient(Context context) {
		this.context = context;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		System.out.println("url---->" + url);
		if(context instanceof MainActivity){
			if(((MainActivity)context).houseListFragment.getProgressDialog() != null && ((MainActivity)context).houseListFragment.getProgressDialog().isShowing())
				((MainActivity)context).houseListFragment.getProgressDialog().dismiss();
		} else if(context instanceof HouseDetailActivity){
			if(((HouseDetailActivity)context).getProgressDialog() != null && ((HouseDetailActivity)context).getProgressDialog().isShowing())
				((HouseDetailActivity)context).getProgressDialog().dismiss();
		} else if(context instanceof VVHouseDetailActivity){
			if(((VVHouseDetailActivity)context).getProgressDialog() != null && ((VVHouseDetailActivity)context).getProgressDialog().isShowing())
				((VVHouseDetailActivity)context).getProgressDialog().dismiss();
		}
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		if(context instanceof MainActivity){
			if(((MainActivity)context).houseListFragment.getProgressDialog() != null && !((MainActivity)context).houseListFragment.getProgressDialog().isShowing())
				((MainActivity)context).houseListFragment.getProgressDialog().show();
		}else if(context instanceof HouseDetailActivity){
			if(((HouseDetailActivity)context).getProgressDialog() != null && !((HouseDetailActivity)context).getProgressDialog().isShowing())
				((HouseDetailActivity)context).getProgressDialog().show();
		}else if(context instanceof VVHouseDetailActivity){
			if(((VVHouseDetailActivity)context).getProgressDialog() != null && !((VVHouseDetailActivity)context).getProgressDialog().isShowing())
				((VVHouseDetailActivity)context).getProgressDialog().show();
		}
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(url.contains(HttpUtils.BASE_URL + "bj_fang10/")){
			url = url.replace("http://vvrent.duapp.com/bj_fang10/", HttpUtils.GET_GANJI_HOUSE_LIST);
		}
		
		if(url.contains("http://www.3g.ganji.com/bj_fang10/")){
			url = url.replace("http://www.3g.ganji.com/bj_fang10/", HttpUtils.GET_GANJI_HOUSE_LIST);
		}
		if(url.contains(HttpUtils.GET_GANJI_HOUSE_LIST) && url.substring(url.indexOf(HttpUtils.GET_GANJI_HOUSE_LIST)).length() > 1){
			url = url.replace(HttpUtils.GET_GANJI_HOUSE_LIST, HttpUtils.GET_GANJI_HOUSE_LIST + "?projection=");
		}
		if(url.contains(HttpUtils.GET_GANJI_HOUSE_LIST) && url.substring(url.indexOf(HttpUtils.GET_GANJI_HOUSE_LIST)).length() > 1 && !url.contains("?page=")){
			Intent intent = new Intent(context, HouseDetailActivity.class);
			intent.putExtra("url", url);
			intent.putExtra("channel", "ganji");
			context.startActivity(intent);
			return true;
		}
		if (url.startsWith("http://i.webapp."))
			url.replace("webapp", "m");
		if (url.startsWith("mailto:") || url.startsWith("geo:")
				|| url.startsWith("tel:")) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(intent);
			return true;
		}
		view.loadUrl(url);
		System.out.println("url---->" + url);
		return true;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}
}