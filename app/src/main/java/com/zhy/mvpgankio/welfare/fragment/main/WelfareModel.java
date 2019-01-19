package com.zhy.mvpgankio.welfare.fragment.main;

import com.zhy.mvpgankio.common.base.rx.BaseObserver;
import com.zhy.mvpgankio.common.http.HttpUtils;
import com.zhy.mvpgankio.common.http.utils.RetrofitHelper;
import com.zhy.mvpgankio.welfare.bean.AllWelfareBean;

/**
 * Created by zhy on 2019/1/19.
 */

public class WelfareModel implements WelfareContract.Model {

    @Override
    public void getWelfareData(String category, int pageNum, int pageSize, BaseObserver<AllWelfareBean> baseObserver) {
        HttpUtils.loadData(RetrofitHelper.getHttpService().getWelfareData(category, pageNum, pageSize), baseObserver);
    }
}
