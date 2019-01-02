package com.hua.parambuilder_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 17:20
 */
class TargetBean {
    private final String staticMethodName;
    private String packageName;
    private Element annElement;
    private ClassName annClassName;
    private ClassName className;
    private ClassName builderClassName;
    private List<FieldBean> fields;
    private String createName;
    private ITargetType createMethod;
     TargetType targetType;

    TargetBean(String packageName,
               Element annElement,
               String createName,
               List<FieldBean> fields,
               TargetType targetType,
               String staticMethodName) {
        this.packageName = packageName;
        this.annElement = annElement;
        this.annClassName = (ClassName) TypeName.get(annElement.asType());
        this.className = ClassName.get(packageName, annClassName.simpleName() + Constants.TARGET_PARAM_SUFFIX);
        this.builderClassName = ClassName.get(packageName, this.className.simpleName(),
                Constants.DEFAULT_ENCLOSE_BUILDER_NAME);
        this.createName = createName;
        this.targetType = targetType;
        this.createMethod = newCreateMethodByTargetType(targetType);
        this.fields = fields;
        this.staticMethodName = staticMethodName;
    }

    private static ITargetType newCreateMethodByTargetType(TargetType targetType) {
        switch (targetType) {
            case ACTIVITY:
                return new ActivityTargetType();
            case FRAGMENT:
                return new FragmentTargetType();
            case OBJECT:
                return new ObjectTargetType();
            default:
                break;
        }
        return new ObjectTargetType();
    }


    String getStaticMethodName() {
        return staticMethodName == null ? "" : staticMethodName;
    }

    String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    ITargetType getCreateMethod() {
        return createMethod;
    }

    ClassName getAnnClassName() {
        return annClassName;
    }

    Element getAnnElement() {
        return annElement;
    }

    ClassName getClassName() {
        return className;
    }

    ClassName getBuilderClassName() {
        return builderClassName;
    }

    List<FieldBean> getFields() {
        if (fields == null) {
            return new ArrayList<>();
        }
        return fields;
    }

    String getCreateName() {
        return createName == null ? "" : createName;
    }
}
