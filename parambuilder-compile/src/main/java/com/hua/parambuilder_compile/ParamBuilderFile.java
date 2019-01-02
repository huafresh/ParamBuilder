package com.hua.parambuilder_compile;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;

import javax.lang.model.element.Modifier;

/**
 * createMethod "ParamBuilder.java" file
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/2 14:05
 */

class ParamBuilderFile implements IJavaFile {
    private static final String PACKAGE_NAME = "com.hua.parambuilder_core";
    private static final String CLASS_NAME = "ParamBuilder";
    private HashMap<String, TargetBean> targetBeanMap;

    void addMethod(String methodName, TargetBean pageParam) {
        if (targetBeanMap == null) {
            targetBeanMap = new HashMap<>();
        }
        targetBeanMap.put(methodName, pageParam);
    }

    HashMap<String, TargetBean> getTargetBeanMap() {
        return targetBeanMap;
    }

    @Override
    public JavaFile create() {
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL);

        //add static "createXXXX" method
        for (String methodName : targetBeanMap.keySet()) {
            TargetBean targetBean = targetBeanMap.get(methodName);
            ClassName returnType = targetBean.getBuilderClassName();
            MethodSpec method = MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(returnType)
                    .addStatement("return $T.newBuilder()", targetBean.getClassName())
                    .build();
            typeBuilder.addMethod(method);
        }

        //add inject() method
        for (TargetBean targetBean : targetBeanMap.values()) {
            if (targetBean.targetType != TargetType.OBJECT) {
                String injectionName = targetBean.getAnnClassName().simpleName() + Constants.INJECTION_FILE_SUFFIX;
                ClassName className = ClassName.get(targetBean.getPackageName(), injectionName);
                MethodSpec method = MethodSpec.methodBuilder("inject")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(TypeName.VOID)
                        .addParameter(targetBean.getAnnClassName(), "target")
                        .addStatement("$T.inject(target)", className)
                        .build();
                typeBuilder.addMethod(method);
            }
        }

        return JavaFile.builder(PACKAGE_NAME, typeBuilder.build()).build();
    }
}
