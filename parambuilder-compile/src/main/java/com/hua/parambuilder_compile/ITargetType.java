package com.hua.parambuilder_compile;

import com.squareup.javapoet.MethodSpec;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 13:50
 */

interface ITargetType {
    MethodSpec createMethod(TargetBean targetBean);

    MethodSpec injectMethod(TargetBean targetBean);
}
