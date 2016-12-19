package nomeiasdg.com.test;

import android.app.Activity;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * @name: com.nanyibang.nomi.adapter.viewHolder
 * @description:
 * @author：Administrator
 * @date: 2016-11-23 16:03
 * @company: 上海若美科技有限公司
 */

public class TestViewHolder extends BaseViewHolder<String> {

    private final SimpleDraweeView mImageView;
    private Activity mActivity;

    public TestViewHolder(Activity activity, ViewGroup parent) {
        super(parent, R.layout.view_simpledraweeview);
        this.mActivity = activity;
        mImageView = $(R.id.simpleDraweeView);
    }

    public void setData(final String str, int position) {
        ImageManager.instance().disPlayImage(mActivity, mImageView, str);
    }

}
