package com.hua.parambuilder_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 13:54
 */

class ObjectTargetType implements ITargetType {

    @Override
    public MethodSpec createMethod(TargetBean targetBean) {
        ClassName annClassName = targetBean.getAnnClassName();
        MethodSpec.Builder createBuilder = MethodSpec.methodBuilder(targetBean.getCreateName())
                .addModifiers(Modifier.PUBLIC)
                .returns(annClassName);

        createBuilder.addStatement("$T target = new $T()", annClassName, annClassName);
        createBuilder.addStatement("$T param = build()", targetBean.getClassName());
        for (FieldBean paramField : targetBean.getFields()) {
            createBuilder.addStatement("target.$N = param.$N", paramField.name, paramField.name);
        }
        createBuilder.addStatement("return target");
        return createBuilder.build();
    }

    @Override
    public MethodSpec injectMethod(TargetBean targetBean) {
        return null;
    }
}
