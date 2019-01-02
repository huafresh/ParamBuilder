package com.hua.parambuilder_compile;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * createMethod "XXX_ParamBuilder_Param.java"
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/2 14:00
 */

class TargetParamFile implements IJavaFile {


    private TargetBean targetBean;

    TargetParamFile(TargetBean targetBean) {
        this.targetBean = targetBean;
    }

    private TypeSpec createEncloseBuilderTypeSpec() {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(Constants.DEFAULT_ENCLOSE_BUILDER_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addModifiers(Modifier.FINAL);

        //add builder field
        for (FieldBean param : targetBean.getFields()) {
            typeSpecBuilder.addField(param.createFieldSpec());
        }

        //add builder constructor
        MethodSpec construct = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
        typeSpecBuilder.addMethod(construct);

        //add setter method
        for (FieldBean param : targetBean.getFields()) {
            typeSpecBuilder.addMethod(param.createSetterMethodSpec(targetBean.getBuilderClassName()));
        }

        //add static method for createMethod builder class
        MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PRIVATE)
                .returns(targetBean.getClassName())
                .addStatement("return new $T(this)", targetBean.getClassName())
                .build();
        typeSpecBuilder.addMethod(buildMethod);

        //add "createXXX" method
        typeSpecBuilder.addMethod(targetBean.getCreateMethod().createMethod(targetBean));

        return typeSpecBuilder.build();
    }

    @Override
    public JavaFile create() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(targetBean.getClassName())
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .addType(createEncloseBuilderTypeSpec());

        //add field
        for (FieldBean param : targetBean.getFields()) {
            builder.addField(param.createFieldSpec());
        }

        //constructor
        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addParameter(targetBean.getBuilderClassName(), "builder")
                .build();
        builder.addMethod(constructor);

        //new builder method
        MethodSpec newBuilder = MethodSpec.methodBuilder("newBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(targetBean.getBuilderClassName())
                .addStatement("return new $T()", targetBean.getBuilderClassName())
                .build();
        builder.addMethod(newBuilder);

        return JavaFile.builder(targetBean.getPackageName(), builder.build()).build();
    }
}
