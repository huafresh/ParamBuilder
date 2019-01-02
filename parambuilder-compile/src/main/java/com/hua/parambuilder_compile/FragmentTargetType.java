package com.hua.parambuilder_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 13:54
 */

class FragmentTargetType implements ITargetType {

    @Override
    public MethodSpec createMethod(TargetBean targetBean) {
        ClassName annClassName = targetBean.getAnnClassName();
        MethodSpec.Builder createBuilder = MethodSpec.methodBuilder(targetBean.getCreateName())
                .addModifiers(Modifier.PUBLIC)
                .returns(annClassName);

        createBuilder.addStatement("$T target = new $T()", annClassName, annClassName)
                .addStatement("$T targetParam = build()", targetBean.getClassName())
                .addStatement("$T arguments = new $T()", Constants.BUNDLE_CLASS_NAME, Constants.BUNDLE_CLASS_NAME);
        for (FieldBean paramField : targetBean.getFields()) {
            if (paramField.typeName.equals(TypeName.BOOLEAN)) {
                createBuilder.addStatement("arguments.putBoolean($S, targetParam.$N)", paramField.name, paramField.name);
            } else if (paramField.typeName.equals(TypeName.INT)) {
                createBuilder.addStatement("arguments.putInt($S, targetParam.$N)", paramField.name, paramField.name);
            } else if (paramField.typeName.equals(TypeName.get(String.class))) {
                createBuilder.addStatement("arguments.putString($S, targetParam.$N)", paramField.name, paramField.name);
            } else {
                createBuilder.addStatement("arguments.putParcelable($S, targetParam.$N)", paramField.name, paramField.name);
            }
        }
        createBuilder.addStatement("target.setArguments(arguments)");
        createBuilder.addStatement("return target");
        return createBuilder.build();
    }

    @Override
    public MethodSpec injectMethod(TargetBean targetBean) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(targetBean.getAnnClassName(), "target")
                .addStatement("$T arguments = target.getArguments()", Constants.BUNDLE_CLASS_NAME);
        methodBuilder.beginControlFlow("if (arguments != null)");
        for (FieldBean paramField : targetBean.getFields()) {
            if (paramField.typeName.equals(TypeName.BOOLEAN)) {
                methodBuilder.addStatement("target.$N = arguments.getBoolean($S)", paramField.name, paramField.name);
            } else if (paramField.typeName.equals(TypeName.INT)) {
                methodBuilder.addStatement("target.$N = arguments.getInt($S)", paramField.name, paramField.name);
            } else if (paramField.typeName.equals(TypeName.get(String.class))) {
                methodBuilder.addStatement("target.$N = arguments.getString($S)", paramField.name, paramField.name);
            } else {
                methodBuilder.addStatement("target.$N = arguments.getParcelable($S)", paramField.name, paramField.name);
            }
        }
        methodBuilder.endControlFlow();
        return methodBuilder.build();
    }
}
