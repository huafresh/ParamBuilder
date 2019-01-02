package com.hua.parambuilder_compile;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * createMethod "XXX_ParamBuilder_Injection.java"
 *
 * @author hua
 * @version V1.0
 * @date 2019/1/2 17:14
 */

class TargetInjectionFile implements IJavaFile {
    private TargetBean targetBean;

    TargetInjectionFile(TargetBean targetBean) {
        this.targetBean = targetBean;
    }

    @Override
    public JavaFile create() {
        String className = targetBean.getAnnClassName().simpleName() + Constants.INJECTION_FILE_SUFFIX;
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL);

        typeBuilder.addMethod(targetBean.getCreateMethod().injectMethod(targetBean));

        return JavaFile.builder(targetBean.getPackageName(), typeBuilder.build()).build();
    }
}
