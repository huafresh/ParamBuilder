package com.hua.parambuilder_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 14:37
 */

class Constants {
    static final ClassName CONTEXT_CLASS_NAME = ClassName.get("android.content", "Context");
    static final ClassName ACTIVITY_CLASS_NAME = ClassName.get("android.app", "Activity");
    static final ClassName ACTIVITY_COMPAT_CLASS_NAME = ClassName.get("android.support.v7.app", "AppCompatActivity");
    static final ClassName FRAGMENT_V4_CLASS_NAME = ClassName.get("android.support.v4.app", "Fragment");
    static final ClassName BUNDLE_CLASS_NAME = ClassName.get("android.os", "Bundle");
    static final ClassName INTENT_CLASS_NAME = ClassName.get("android.content", "Intent");

    static final String INJECTION_FILE_SUFFIX = "_ParamBuilder_Injection";
    static final String TARGET_PARAM_SUFFIX = "_ParamBuilder_Param";
    static final String DEFAULT_ENCLOSE_BUILDER_NAME = "Builder";
}
