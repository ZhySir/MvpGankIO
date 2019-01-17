package com.zhy.mvpgankio.category.fragment.main;

import com.zhy.mvpgankio.common.base.mvp.BaseModel;
import com.zhy.mvpgankio.common.base.mvp.BasePresenter;
import com.zhy.mvpgankio.common.base.mvp.BaseView;

/**
 * Created by zhy on 2019/1/17.
 */

public interface CategoryContract {

    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<Model, View> {

    }

}
