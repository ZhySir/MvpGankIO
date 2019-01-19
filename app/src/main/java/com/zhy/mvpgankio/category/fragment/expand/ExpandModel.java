package com.zhy.mvpgankio.category.fragment.expand;

import com.zhy.mvpgankio.category.bean.AllCategoryBean;
import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.common.http.HttpUtils;
import com.zhy.mvpgankio.common.http.utils.RetrofitHelper;

/**
 * Created by zhy on 2019/1/17.
 */

public class ExpandModel implements ExpandContract.Model {

    @Override
    public void getCategoryData(String category, int pageNum, int pageSize, BaseObserver<AllCategoryBean> baseObserver) {
        HttpUtils.loadData(RetrofitHelper.getHttpService().getCategoryData(category, pageNum, pageSize), baseObserver);
    }

}
