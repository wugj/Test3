package nomeiasdg.com.test;

import android.app.Activity;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author wujian
 * @date 2016-11-23 11:50
 * @company 上海若美科技有限公司
 */
public class TestAdapter extends RecyclerArrayAdapter<String> {

	private Activity mActivity;

	public TestAdapter(Activity activity) {
		super(activity);
		this.mActivity = activity;
	}

	@Override
	public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
		return new TestViewHolder(mActivity, parent);
	}

}

