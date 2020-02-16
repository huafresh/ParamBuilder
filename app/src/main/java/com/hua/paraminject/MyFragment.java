package com.hua.paraminject;


import androidx.fragment.app.Fragment;

import com.hua.parambuilder_annotation.Builder;
import com.hua.parambuilder_annotation.Param;


/**
 * @author hua
 * @version V1.0
 * @date 2018/12/29 10:16
 */
@Builder(
        staticMethodName = "beginBuildMyFragment",
        createMethodName = "open"
)
public class MyFragment extends Fragment {

    @Param
    String name;

    @Param
    int progress;

}
